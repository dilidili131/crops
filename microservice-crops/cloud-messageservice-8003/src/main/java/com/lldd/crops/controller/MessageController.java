package com.lldd.crops.controller;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.aliyun.oss.internal.OSSUtils;
import com.aliyun.oss.model.PutObjectResult;
import com.lldd.crops.entities.CommonResult;
import com.lldd.crops.entity.Data;
import com.lldd.crops.entity.Message;
import com.lldd.crops.service.MessageService;
import com.lldd.crops.utils.AliOssService;
import com.lldd.crops.utils.ConvertToMultipartFile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import javax.imageio.stream.ImageOutputStream;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

/**
 * @description:
 * @author: lch
 * @time: 2021/11/22 23:01
 */
@RestController
@RequestMapping("/api")
public class MessageController {
    @Autowired
    private MessageService messageService;

    @PostMapping("/getAllMessage")
    public Object getAllMessage(@RequestParam(value = "worm_name",required = false)String worm_name,
                                @RequestParam(value = "address",required = false)String address,
                                @RequestParam(value = "done",required = false)String done){
        List<Message> messageList = messageService.getAllMessage(worm_name,address, done);
        int total = messageService.getAllMessageNum(worm_name,address, done);

        Map<String,Object> map = new HashMap<>();
        map.put("list",messageList);
        map.put("total",total);

        return new CommonResult<>(200,"获取成功",map);
    }

    @PostMapping("/updateDone")
    public Object updateDone(@RequestParam("username")String username,
                             @RequestParam("address")String address,
                             @RequestParam("worm_name")String worm_name){
        int tmp = messageService.updateDone(username, address, worm_name);
        if(tmp == 1){
            return new CommonResult<>(200,"更新成功",tmp);
        }
        return new CommonResult<>(500,"更新失败",null);
    }

    @PostMapping("/imageUpload")
    public Object image(@RequestParam("photo") MultipartFile upfile,
                        @RequestParam("name")String name) throws IOException, InterruptedException {
        AliOssService.upload(upfile,name+".jpg");
        return new CommonResult<>(200,"图片上传成功",null);
    }
    @PostMapping("/imagePaint")
    public Object paint(@RequestBody Data[] datalist) throws IOException {
        AliOssService.getImg(datalist[0].getName());
//        BufferedImage image = ImageIO.read(new File("/home/worm_img/"+datalist[0].getName()+".jpg"));
        BufferedImage image = ImageIO.read(new File("D:\\"+datalist[0].getName()+".jpg"));
        Graphics g = image.getGraphics();
        g.setColor(Color.RED);

        for(Data data:datalist){
            g.drawRect(data.getX(), data.getY(), data.getW(), data.getH());
        }
        AliOssService.uploadBufferedImage(image,datalist[0].getName());

        return new CommonResult<>(200,"图片绘制成功",null);
    }

    @PostMapping("/alert")
    public Object alert(@RequestParam("username")String username,
                        @RequestParam("worm_name")String worm_name,
                        @RequestParam("number")String number,
                        @RequestParam("address")String address,
                        @RequestParam("done")String done){
        int tmp = messageService.insertMsg(username, worm_name, number, address, done);
        if(tmp == 1){
            return new CommonResult<>(200,"警报成功",tmp);
        }
        return new CommonResult<>(500,"警报失败",null);
    }

}
