package com.lldd.crops.dao;

import com.lldd.crops.entity.Message;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @description:
 * @author: lch
 * @time: 2021/11/22 23:00
 */
@Mapper
public interface MessageDao {
    List<Message> getAllMessage(@Param("worm_name") String worm_name,
                                @Param("address")String address,
                                @Param("done")String done);
    int getAllMessageNum(@Param("worm_name")String worm_name,
                         @Param("address")String address,
                         @Param("done")String done);

    int updateDone(@Param("username")String username,
                   @Param("address")String address,
                   @Param("worm_name")String worm_name);

    int insertMsg(@Param("username")String username,
                  @Param("worm_name")String worm_name,
                  @Param("number")String number,
                  @Param("address")String address,
                  @Param("done")String done);
}
