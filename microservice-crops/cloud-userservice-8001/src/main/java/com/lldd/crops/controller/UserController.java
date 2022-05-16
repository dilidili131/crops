package com.lldd.crops.controller;

import com.lldd.crops.entities.CommonResult;
import com.lldd.crops.entity.User;
import com.lldd.crops.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @description:
 * @author: lch
 * @time: 2021/11/22 16:17
 */
@RestController
@RequestMapping("/api")
public class UserController {
    @Autowired
    private UserService userService;

    @PostMapping("addAdmin")
    @ResponseBody
    public CommonResult<Integer> addAdmin(@RequestParam("id")String id,
                                 @RequestParam("username")String username,
                                 @RequestParam("password")String password,
                                 @RequestParam("address")String address,
                                 @RequestParam("type")String type){
        int tmp = this.userService.addAdmin(id,username,password,address,type);
        if(tmp == 1){
            return new CommonResult<>(200,"添加成功",tmp);
        }
        return new CommonResult<>(500,"添加失败",null);
    }

    @PostMapping("deleteUser")
    @ResponseBody
    public CommonResult<Integer> deleteUser(@RequestParam("id")String id){
        int tmp = userService.deleteUser(id);
        if(tmp == 1){
            return new CommonResult<>(200,"删除成功",tmp);
        }
        return new CommonResult<>(500,"删除失败",null);
    }

    @PostMapping("getAllUser")
    public CommonResult<Map> getAllUser(@RequestParam("type")String type,
                             @RequestParam("address")String address,
                             @RequestParam("pagenum")int pagenum,
                             @RequestParam("pagesize")int pagesize){
        List<User> userList = userService.getAllUser(type,address,(pagenum-1)*pagesize,pagesize);
        int total = userService.getAllUserNum(type,address);

        Map<String,Object> map = new HashMap<>();
        map.put("list",userList);
        map.put("num",total);

        return new CommonResult<>(200,"获取成功",map);
    }

    @PostMapping("updateUser")
    @ResponseBody
    public CommonResult<Integer> updateUser(@RequestParam("id")String id,
                             @RequestParam("username")String username,
                             @RequestParam("address")String address){
        int tmp = userService.updateUser(id,username,address);

        if(tmp == 1){
            return new CommonResult<>(200,"更新成功",tmp);
        }
        return new CommonResult<>(500,"更新失败",null);
    }
}
