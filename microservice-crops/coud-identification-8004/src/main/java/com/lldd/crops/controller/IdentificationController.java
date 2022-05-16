package com.lldd.crops.controller;

import com.lldd.crops.entities.CommonResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

/**
 * @description:
 * @author: lch
 * @time: 2021/11/25 13:34
 */
@RestController
public class IdentificationController {
    @Autowired
    private RestTemplate restTemplate;

    @GetMapping("/api/getInfoByCate")
    public Object getInfoByName(@RequestParam("category")String category){
        return restTemplate.getForObject("http://nacos-wormservice-8002/api/getInfoByCate?category="+category,CommonResult.class);
    }
    @PostMapping("/api/identification")
    public CommonResult Identification(@RequestParam("photo") MultipartFile upfile) throws IOException {
        ByteArrayResource fileAsResource = new ByteArrayResource(upfile.getBytes()){
            public String getFilename(){
                return upfile.getOriginalFilename();
            }
            public long contentLength(){
                return upfile.getSize();
            }
        };
        MultiValueMap<String ,Object> multipartRequest = new LinkedMultiValueMap<>();
        multipartRequest.add("photo",fileAsResource);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.MULTIPART_FORM_DATA);
        HttpEntity<MultiValueMap<String ,Object>> files = new HttpEntity<>(multipartRequest,headers);

        RestTemplate restTemplate = new RestTemplate();
        return restTemplate.postForObject("http://8.142.15.16:5001",files,CommonResult.class);
    }

}
