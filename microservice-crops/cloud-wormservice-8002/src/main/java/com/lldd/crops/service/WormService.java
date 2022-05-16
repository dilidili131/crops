package com.lldd.crops.service;

import com.lldd.crops.dao.WormDao;
import com.lldd.crops.entity.Worm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @description:
 * @author: lch
 * @time: 2021/11/22 22:08
 */
@Service
public class WormService {
    @Autowired
    private WormDao wormDao;

    public List<Worm> getAllWormData(String worm_name, String category, int pagenum, int pagesize){
        return wormDao.getAllWormData(worm_name,category,pagenum,pagesize);
    }
    public int getAllWormDataNum(String worm_name, String category){
        return wormDao.getAllWormDataNum(worm_name,category);
    }

    public List<String> getAllCate(){
        return wormDao.getAllCate();
    }

    public int updateWormInfo(String worm_name,String worm_sname,String category,String location,String character,String discipline,String prevent){
        return wormDao.updateWorminfo(worm_name, worm_sname, category, location, character, discipline, prevent);
    }
    public int deleteWorm(String worm_name){
        return wormDao.deleteWorm(worm_name);
    }

    public int insertWorm(String worm_name, String worm_sname, String category, String img_link, String location, String character, String discipline, String prevent){
        return wormDao.insertWorm(worm_name, worm_sname, category, img_link, location, character, discipline, prevent);
    }

    public List<Worm> getInfoByCate(String category){
        return wormDao.getInfoByCate(category);
    }
}
