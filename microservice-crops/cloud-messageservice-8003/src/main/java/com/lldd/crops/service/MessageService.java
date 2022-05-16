package com.lldd.crops.service;

import com.lldd.crops.dao.MessageDao;
import com.lldd.crops.entity.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @description:
 * @author: lch
 * @time: 2021/11/22 23:00
 */
@Service
public class MessageService {
    @Autowired
    private MessageDao messageDao;

    public List<Message> getAllMessage(String worm_name, String address, String done){
        return messageDao.getAllMessage(worm_name,address, done);
    }
    public int getAllMessageNum(String worm_name,String address,String done){
        return messageDao.getAllMessageNum(worm_name,address, done);
    }

    public int updateDone(String username,String address,String worm_name){
        return messageDao.updateDone(username, address, worm_name);
    }
    public int insertMsg(String username,String worm_name,String number,String address,String done){
        return messageDao.insertMsg(username, worm_name, number, address, done);
    }
}
