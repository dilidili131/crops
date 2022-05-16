package com.lldd.crops;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * @description:
 * @author: lch
 * @time: 2021/11/21 22:08
 */
@SpringBootApplication
@EnableDiscoveryClient
public class UserServiceMain8001 {
    public static void main(String[] args) {
        SpringApplication.run(UserServiceMain8001.class,args);
    }
}
