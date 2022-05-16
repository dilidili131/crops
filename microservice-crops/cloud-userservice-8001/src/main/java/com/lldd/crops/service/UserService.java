package com.lldd.crops.service;

import com.lldd.crops.dao.UserDao;
import com.lldd.crops.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @description:
 * @author: lch
 * @time: 2021/11/22 16:15
 */
@Service
public class UserService{
    @Autowired
    private UserDao userDao;

    public int addAdmin(String id,String username,String password,String address,String type){
        return userDao.addAdmin(id, username, password, address, type);
    }
    public int deleteUser(String id){
        return userDao.deleteUser(id);
    }
    public List<User> getAllUser(String type , String address, int pagenum, int pagesize){
        return userDao.getAllUser(type,address,pagenum, pagesize);
    }
    public int getAllUserNum(String type,String address){
        return userDao.getAllUserNum(type,address);
    }
    public int updateUser(String id,String username,String address){
        return userDao.updateUser(id, username, address);
    }
}
