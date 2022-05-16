package com.lldd.crops.service;

import com.lldd.crops.dao.LoginDao;
import com.lldd.crops.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

/**
 * @description:
 * @author: lch
 * @time: 2021/11/22 14:01
 */
@Service
public class LoginService{
    @Autowired
    private LoginDao loginDao;

    public User loginCheck(String username, String password){
        return loginDao.loginCheck(username,password);
    }
    public String checkUserByName(String username){
        return loginDao.checkUserByName(username);
    }
    public int insertUser(String id,String username,String password,String address,String type){
        return loginDao.insertUser(id, username, password, address,type);
    }
}
