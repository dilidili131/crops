package com.lldd.crops.controller;

import com.lldd.crops.entities.CommonResult;
import com.lldd.crops.entity.Worm;
import com.lldd.crops.service.WormService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @description:
 * @author: lch
 * @time: 2021/11/22 22:12
 */
@RestController
@RequestMapping("/api")
public class WormController {
    @Autowired
    private WormService wormService;

    @PostMapping("/getAllWormData")
    @ResponseBody
    public Object getAllWormData(@RequestParam("worm_name")String worm_name,
                                 @RequestParam("category")String category,
                                 @RequestParam("pagenum")int pagenum,
                                 @RequestParam("pagesize")int pagesize){

        List<Worm> wormList = wormService.getAllWormData(worm_name,category,(pagenum-1)*pagesize,pagesize);
        int total = wormService.getAllWormDataNum(worm_name,category);

        Map<String,Object> map = new HashMap<>();
        map.put("list",wormList);
        map.put("num",total);

        return new CommonResult<>(200,"获取病虫数据成功",map);
    }

    @GetMapping("/getAllCate")
    @ResponseBody
    public Object getAllCate(){
        List<String> allCate = wormService.getAllCate();
        return new CommonResult<>(200,"获取病虫类别成功",allCate);
    }

    @PostMapping("/updateWormInfo")
    @ResponseBody
    public Object updateWormInfo(@RequestParam("worm_name")String worm_name,
                                 @RequestParam("worm_sname")String worm_sname,
                                 @RequestParam("category")String category,
                                 @RequestParam("location")String location,
                                 @RequestParam("character")String character,
                                 @RequestParam("discipline")String discipline,
                                 @RequestParam("prevent")String prevent){
        int num = wormService.updateWormInfo(worm_name, worm_sname, category, location, character, discipline, prevent);
        if(num != 0){
            return new CommonResult<>(200,"更新病虫信息成功",num);
        }
        return new CommonResult<>(500,"更新病虫信息失败",null);
    }

    @PostMapping("/deleteWorm")
    @ResponseBody
    public Object deleteWorm(@RequestParam("worm_name")String worm_name){
        int num = wormService.deleteWorm(worm_name);
        if(num != 0){
            return new CommonResult<>(200,"删除病虫信息成功",num);
        }
        return new CommonResult<>(500,"删除病虫信息失败",null);
    }
    @PostMapping("/insertWorm")
    @ResponseBody
    public Object addWorm(@RequestParam("worm_name")String worm_name,
                          @RequestParam("img_link")String img_link,
                          @RequestParam("location")String location,
                          @RequestParam("character")String character,
                          @RequestParam("discipline")String discipline,
                          @RequestParam("prevent")String prevent,
                          @RequestParam("worm_sname")String worm_sname,
                          @RequestParam("category")String category){
        int num = wormService.insertWorm(worm_name, worm_sname, category, img_link, location, character, discipline, prevent);
        if(num != 0){
            return new CommonResult<>(200,"添加病虫信息成功",num);
        }
        return new CommonResult<>(500,"添加病虫信息失败",null);
    }

    @GetMapping("/getInfoByCate")
    public Object getInfoByCate(@RequestParam("category")String category){
        List<Worm> dataList = wormService.getInfoByCate(category);
        Map<String,Object> maplist = new HashMap<>();
        maplist.put("list",dataList);

        return new CommonResult<>(200,"获取病虫信息成功",maplist);
    }
}
