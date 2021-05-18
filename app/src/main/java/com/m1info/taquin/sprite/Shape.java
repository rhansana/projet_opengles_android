package com.m1info.taquin.sprite;

public interface Shape {

    void draw(float[] mvpMatrix);
    void set_position(float[] pos);
    float[] get_position();
}
