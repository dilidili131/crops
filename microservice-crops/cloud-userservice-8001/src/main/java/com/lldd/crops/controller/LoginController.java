package com.lldd.crops.controller;

import com.lldd.crops.entities.CommonResult;
import com.lldd.crops.entity.User;
import com.lldd.crops.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @description:
 * @author: lch
 * @time: 2021/11/22 13:56
 */
@RestController
@RequestMapping("/api")
public class LoginController {
    @Autowired
    private LoginService loginService;

    @PostMapping("/login")
    public CommonResult<User> loginCheck(@RequestParam("username")String username,
                                         @RequestParam("password")String password){
        User user = loginService.loginCheck(username,password);
        if(user != null){
            return new CommonResult<>(200,"登录成功",user);
        }
        return new CommonResult<>(500,"登录失败",null);
    }
    //注册验证用户是否存在
    @PostMapping("/checkUser")
    @ResponseBody
    public CommonResult<String> checkUserByName(@RequestParam("username") String username){
        String pwd = loginService.checkUserByName(username);
        if(pwd != null){
            return new CommonResult<>(200,"有此用户",pwd);
        }
        return new CommonResult<>(500,"无此用户",null);
    }

    @PostMapping("/addUser")
    @ResponseBody
    public CommonResult<Integer> updateUser(@RequestParam("id")String id,
                             @RequestParam("username")String username,
                             @RequestParam("password")String password,
                             @RequestParam("address")String address,
                             @RequestParam("type")String type){

        int tmp = this.loginService.insertUser(id,username,password,address,type);
        if(tmp == 1){
            return new CommonResult<>(200,"添加成功",1);
        }
        return new CommonResult<>(500,"添加失败",null);
    }
}
