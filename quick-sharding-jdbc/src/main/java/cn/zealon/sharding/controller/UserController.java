package cn.zealon.sharding.controller;

import cn.zealon.sharding.domain.User;
import cn.zealon.sharding.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 用户接口
 * @author: zealon
 * @since: 2021/3/10
 */
@RestController
@RequestMapping("user")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("add")
    public Object add(User user){
        this.userService.add(user);
        return "ok";
    }

    @GetMapping("details/{id}")
    public User getById(@PathVariable("id") Integer id){
        return this.userService.getById(id);
    }
}
