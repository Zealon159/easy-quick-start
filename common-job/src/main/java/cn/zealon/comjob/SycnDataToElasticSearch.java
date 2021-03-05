package cn.zealon.comjob;

import cn.zealon.comjob.domain.TagLibBookBo;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import io.searchbox.client.JestClient;
import io.searchbox.client.JestClientFactory;
import io.searchbox.client.JestResult;
import io.searchbox.client.config.HttpClientConfig;
import io.searchbox.core.Bulk;
import io.searchbox.core.Cat;
import io.searchbox.core.CatResult;
import io.searchbox.core.Index;
import io.searchbox.indices.CreateIndex;
import io.searchbox.indices.DeleteIndex;
import io.searchbox.indices.aliases.AddAliasMapping;
import io.searchbox.indices.aliases.ModifyAliases;
import io.searchbox.indices.aliases.RemoveAliasMapping;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.io.IOException;
import java.util.*;

/**
 * 同步数据到ES
 * <p>
 *     基于Jest客户端
 * </p>
 * @author: zealon
 * @since: 2021/3/5
 */
public class SycnDataToElasticSearch {
    private static final Logger LOGGER = LoggerFactory.getLogger(SycnDataToElasticSearch.class);
    /** 别名 */
    private static String aliasName = "bookLib";
    /** 索引库 */
    private static String indexNames = "book_lib_1,book_lib_2";
    /** 类型 */
    private static String indexType = "book";
    /** 服务器地址 */
    private static String servers = "http://elasticsearch.miandian:9200";
    private static Gson gosn = new Gson();
    private static JestClient jestClient;

    /** 初始化jest客户端 */
    static {
        String[] sers = servers.split(",");
        HttpClientConfig httpClientConfig =
                new HttpClientConfig.Builder(Arrays.asList(sers)).multiThreaded(true).connTimeout(5000).readTimeout(10000)
                        .maxTotalConnection(100).defaultMaxTotalConnectionPerRoute(50).build();
        JestClientFactory factory = new JestClientFactory();
        factory.setHttpClientConfig(httpClientConfig);
        jestClient= factory.getObject();
    }

    public static void main(String[] args){
        // ----------------- 获取工作索引与非工作索引 -----------------
        Map<Integer, String> workingIndexName = getWorkingIndexName();

        // ----------------- 重建非工作索引 -----------------
        reIndex(workingIndexName.get(0));

        // ----------------- 上传文档 -----------------
        savePlatformDocument(workingIndexName.get(0));

        // ----------------- 切换工作索引 -----------------
        switchIndexAlias(workingIndexName);
    }

    /**
     * 获取当前工作索引，与非工作索引
     * @return 0 为非工作，1 为工作
     */
    private static Map<Integer,String> getWorkingIndexName() {
        String[] indexArr = indexNames.split(",");
        Map<Integer,String> indexResult = new HashMap<>();
        // 获取当前正在提供搜索的index
        Cat.AliasesBuilder aliasesBuilder = new Cat.AliasesBuilder();
        try {
            CatResult result = jestClient.execute(aliasesBuilder.build());
            LOGGER.info("获取当前索引返回={}",gosn.toJson(result));

            //获取索引的别名映射
            ObjectMapper objectMapper = new ObjectMapper();
            List<Map> aliasMaps = objectMapper.readValue(result.getJsonString(), List.class);
            LOGGER.info("aliasMaps={}",gosn.toJson(aliasMaps));
            for (Map aliasMap : aliasMaps) {
                // 如果当前需要创建的alias等于查找出来的alias的话,则获取到当前正在工作的index,然后取下一个index做为当前需要创建的索引index
                if (aliasName.equals(String.valueOf(aliasMap.get("alias")))) {
                    //获取当前工作的别名
                    String workingIndex = String.valueOf(aliasMap.get("index"));
                    for (int i = 0; i < indexArr.length; i++) {
                        if (indexArr[i].equals(workingIndex)) {
                            indexResult.put(1, indexArr[i]);
                        } else {
                            indexResult.put(0, indexArr[i]);
                        }
                    }
                    return indexResult;
                }
            }
        } catch (IOException e) {
            LOGGER.error("获取alias出错,程序应该终止", e);
        }
        indexResult.put(1, indexArr[0]);
        indexResult.put(0, indexArr[1]);
        return indexResult;
    }

    /**
     * 上传基础平台有标签的图书（评分排序）
     * @param indexName
     */
    private static void savePlatformDocument(String indexName){
        // 分页上传
        int page = 1;
        int size = 500;
        Map<String, TagLibBookBo> platformTagBooks = getPlatformTagBooks(page, size);
        while (platformTagBooks != null && platformTagBooks.size() > 0) {
            Bulk.Builder bulk = new Bulk.Builder().refresh(true).defaultIndex(indexName).defaultType(indexType);
            Iterator<Map.Entry<String, TagLibBookBo>> iterator = platformTagBooks.entrySet().iterator();
            while (iterator.hasNext()) {
                Map.Entry<String, TagLibBookBo> next = iterator.next();
                bulk.addAction(new Index.Builder(next.getValue()).id(next.getKey()).build());
            }
            executeBulk(bulk, indexName);

            // 获取下页数据
            page++;
            platformTagBooks = getPlatformTagBooks(page, size);
        }
    }

    /**
     * 分页查询业务数据
     * @param page
     * @param size
     * @return
     */
    public static Map<String, TagLibBookBo> getPlatformTagBooks(int page, int size) {
        return null;
    }

    /**
     * 重建索引库
     * @param indexName
     */
    private static void reIndex(String indexName){
        try {
            // 删除构建的索引
            DeleteIndex.Builder delBuilder = new DeleteIndex.Builder(indexName);
            JestResult deleteResult = jestClient.execute(delBuilder.build());
            LOGGER.info("删除索引 indexName={},data={}", indexName, deleteResult.getJsonString());

            // 创建新的索引
            String settings = "{\"max_result_window\": \"2000\",\"analysis\":{\"analyzer\":{\"default\":{\"tokenizer\":\"ik_max_word\"}}}}}";
            CreateIndex.Builder createBuilder = new CreateIndex.Builder(indexName)
                    .payload(settings);
            JestResult createResult = jestClient.execute(createBuilder.build());
            LOGGER.info("创建索引 indexName={},data={}",indexName,createResult.getJsonString());
        } catch (IOException e) {
            LOGGER.error("创建标签索引失败！indexName={}", indexName, e);
        }
    }

    /**
     * 切换索引别名
     * @param workingIndexName
     */
    private static void switchIndexAlias(Map<Integer, String> workingIndexName){
        try {
            AddAliasMapping aliasBuilder = new AddAliasMapping.Builder(workingIndexName.get(0), aliasName).build();
            JestResult aliasResult = jestClient.execute(new ModifyAliases.Builder(aliasBuilder).build());
            LOGGER.info("设置别名 indexName={},data={}", workingIndexName.get(0), aliasResult.getJsonString());

            RemoveAliasMapping removeAlias = new RemoveAliasMapping.Builder(workingIndexName.get(1), aliasName).build();
            jestClient.execute(new ModifyAliases.Builder(removeAlias).build());
            LOGGER.info("移除别名 indexName={},data={}", workingIndexName.get(1), aliasResult.getJsonString());
        } catch (IOException e) {
            LOGGER.error("切换别名失败！", e);
        }
    }

    /**
     * 执行请求批量存储
     * @param bulk
     * @param indexName
     */
    private static void executeBulk(Bulk.Builder bulk, String indexName){
        try {
            //批量执行
            jestClient.execute(bulk.build());
            LOGGER.info("add tagLibBookIndex sucess!! indexName:" + indexName + ",indexType:" + indexType);
        } catch (IOException e) {
            LOGGER.error("插入索引数据错误!!!", e);
            retry(bulk.build());
        }
    }

    /**
     * 失败后重试3次
     * @param bulk
     */
    private static void retry(Bulk bulk) {
        int i = 0;
        while (i++ < 3) {
            try {
                jestClient.execute(bulk);
                //添加索引成功,退出循环
                break;
            } catch (IOException e) {
                LOGGER.error("插入索引数据错误!!! retry:" + i, e);
            }
        }
    }

}
