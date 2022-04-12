package com.lagou.rcache.controller;

import com.lagou.rcache.dao.UserDao;
import com.lagou.rcache.entity.TUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@EnableAutoConfiguration
@RequestMapping(value = "/user")
public class UserController {
    @Autowired
    private UserDao userDao;

    @RequestMapping(value = "getall")
    public List<TUser> getUsers()
    {
        List<TUser> userList= userDao.selectUser();
        return userList;
    }

}
