package com.lagou.rcache.dao;

import com.lagou.rcache.entity.TUser;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface UserDao {

    List<TUser> selectUser();
}
