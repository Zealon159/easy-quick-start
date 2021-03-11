package cn.zealon.sharding.dao;

import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @author: zealon
 * @since: 2021/3/11
 */
public interface TablesMapper {
    @Select("show tables;")
    List<String> getAllTables();
}
