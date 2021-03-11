package cn.zealon.sharding.service;

import cn.zealon.sharding.dao.TablesMapper;
import com.baomidou.dynamic.datasource.annotation.DS;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author: zealon
 * @since: 2021/3/11
 */
@Service
public class TableService {

    @Autowired
    private TablesMapper tablesMapper;

    @DS("book-ms")
    public List<String> getBookMSTables(){
        return this.tablesMapper.getAllTables();
    }

    @DS("book-ms-account")
    public List<String> getBookMSAccountTables(){
        return this.tablesMapper.getAllTables();
    }
}
