package com.lldd.crops.dao;

import com.lldd.crops.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface LoginDao {
    User loginCheck(@Param("username") String username, @Param("password") String password);
    String checkUserByName(@Param("username") String username);
    int insertUser(@Param("id") String id,
                   @Param("username") String username,
                   @Param("password") String password,
                   @Param("address") String address,
                   @Param("type") String type);
}
