package com.lldd.crops.dao;

import com.lldd.crops.entity.Worm;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface WormDao {
    List<Worm> getAllWormData(@Param("worm_name") String worm_name,
                              @Param("category") String category,
                              @Param("pagenum")int pagenum,
                              @Param("pagesize")int pagesize);

    int getAllWormDataNum(@Param("worm_name")String worm_name,
                          @Param("category")String category);

    List<String> getAllCate();

    int updateWorminfo(@Param("worm_name")String worm_name,
                       @Param("worm_sname")String worm_sname,
                       @Param("category")String category,
                       @Param("location")String location,
                       @Param("character")String character,
                       @Param("discipline")String discipline,
                       @Param("prevent")String prevent);

    int deleteWorm(@Param("worm_name")String worm_name);

    int insertWorm(@Param("worm_name")String worm_name,
                   @Param("worm_sname")String worm_sname,
                   @Param("category")String category,
                   @Param("img_link")String img_link,
                   @Param("location")String location,
                   @Param("character")String character,
                   @Param("discipline")String discipline,
                   @Param("prevent")String prevent);

    //    根据虫子名称找相关信息
    List<Worm> getInfoByCate(@Param("category")String category);
}
