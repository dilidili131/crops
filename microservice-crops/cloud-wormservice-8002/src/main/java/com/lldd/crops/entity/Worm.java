package com.lldd.crops.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @description:
 * @author: lch
 * @time: 2021/11/22 22:03
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Worm {
    private String worm_name;
    private String worm_link;
    private String img_link;
    private String location;
    private String character;
    private String discipline;
    private String prevent;
    private String worm_sname;
    private String category;
}
