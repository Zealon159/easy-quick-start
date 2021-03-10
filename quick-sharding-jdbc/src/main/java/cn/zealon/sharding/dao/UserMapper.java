package cn.zealon.sharding.dao;

import cn.zealon.sharding.domain.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;

/**
 * 用户信息
 * @author: zealon
 * @since: 2021/3/10
 */
public interface UserMapper {

    @Select("select id,name from user where id=#{id};")
    User selectById(Integer id);

    @Insert("insert into user(name) values(#{name});")
    int insert(User user);
}
