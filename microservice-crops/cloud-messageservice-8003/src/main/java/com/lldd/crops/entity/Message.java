package com.lldd.crops.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @description:
 * @author: lch
 * @time: 2021/11/22 23:00
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Message {
    private String username;//上传用户；
    private String worm_name;
    private String number;
    private String address;
    private String done;
}
