package com.lldd.crops;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * @description:
 * @author: lch
 * @time: 2021/11/22 21:57
 */
@SpringBootApplication
@EnableDiscoveryClient
public class WormServiceMain8002 {
    public static void main(String[] args) {
        SpringApplication.run(WormServiceMain8002.class,args);
    }
}
