package com.lldd.crops.dao;

import com.lldd.crops.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface UserDao {
    int addAdmin(@Param("id") String id,
                 @Param("username") String username,
                 @Param("password") String password,
                 @Param("address") String address,
                 @Param("type") String type);
    int deleteUser(@Param("id") String id);
    List<User> getAllUser(@Param("type") String type,
                          @Param("address") String address,
                          @Param("pagenum") int pagenum,
                          @Param("pagesize") int pagesize);
    int getAllUserNum(@Param("type") String type,@Param("address")String address);

    int updateUser(@Param("id")String id,@Param("username")String username,@Param("address") String address);
}
