package com.lldd.crops;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * @description:
 * @author: lch
 * @time: 2021/11/22 23:13
 */
@SpringBootApplication
@EnableDiscoveryClient
public class MessageServiceMain8003 {
    public static void main(String[] args) {
        SpringApplication.run(MessageServiceMain8003.class,args);
    }
}
