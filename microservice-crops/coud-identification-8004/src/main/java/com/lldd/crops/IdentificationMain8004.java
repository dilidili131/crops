package com.lldd.crops;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * @description:
 * @author: lch
 * @time: 2021/11/25 13:29
 */
@SpringBootApplication
@EnableDiscoveryClient
public class IdentificationMain8004 {
    public static void main(String[] args) {
        SpringApplication.run(IdentificationMain8004.class,args);
    }
}
