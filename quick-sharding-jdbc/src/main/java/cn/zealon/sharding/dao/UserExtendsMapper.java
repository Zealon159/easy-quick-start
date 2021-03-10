package cn.zealon.sharding.dao;

import cn.zealon.sharding.domain.UserExtends;
import org.apache.ibatis.annotations.Insert;

/**
 * 用户扩展信息
 * @author: zealon
 * @since: 2021/3/10
 */
public interface UserExtendsMapper {
    @Insert("insert into user_extends(uid,age) values(#{uid},#{age})")
    int insert(UserExtends userExtends);
}
