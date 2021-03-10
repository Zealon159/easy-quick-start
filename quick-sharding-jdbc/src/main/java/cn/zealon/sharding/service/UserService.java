package cn.zealon.sharding.service;

import cn.zealon.sharding.dao.UserExtendsMapper;
import cn.zealon.sharding.dao.UserMapper;
import cn.zealon.sharding.domain.User;
import cn.zealon.sharding.domain.UserExtends;
import org.apache.shardingsphere.spring.boot.util.DataSourceUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 用户服务
 * @author: zealon
 * @since: 2021/3/10
 */
@Service
public class UserService {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private UserExtendsMapper userExtendsMapper;

    @Transactional
    public void add(User user){
        this.userMapper.insert(user);

        int a = 0 / 0;
        System.out.println(a);

        UserExtends userExtends = new UserExtends();
        userExtends.setAge(20);
        userExtends.setUid(1);
        this.userExtendsMapper.insert(userExtends);
    }

    public User getById(Integer id){
        //DataSourceUtil.getDataSource("");
        return this.userMapper.selectById(id);
    }
}
