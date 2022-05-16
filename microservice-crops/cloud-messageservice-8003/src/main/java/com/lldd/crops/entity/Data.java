package com.lldd.crops.entity;

import org.springframework.stereotype.Component;

/**
 * @description:
 * @author: lch
 * @time: 2021/11/3 2:42
 */
@Component
public class Data {
    private int x;
    private int y;
    private int w;
    private int h;
    private String name;

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public void setW(int w) {
        this.w = w;
    }

    public void setH(int h) {
        this.h = h;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getW() {
        return w;
    }

    public int getH() {
        return h;
    }
}
