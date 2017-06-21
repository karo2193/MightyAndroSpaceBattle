package com.mightyandrospacebattle;

import com.badlogic.gdx.Game;


/**
 * Created by Michal on 21.06.2017.
 */

public class SpaceObject {
    protected float x;
    protected float y;
    protected float dx;
    protected float dy;
    protected float radians;
    protected float speed;
    protected float rotationSpeed;
    protected int width;
    protected int height;
    protected float shapex[];
    protected float shapey[];

    public float getx() {
        return x;
    }

    public float gety() {
        return y;
    }

    protected void wrap() {
        if (x < 0) x = MainGame.WIDTH;
        if (x > MainGame.WIDTH) x = 0;
        if (y < 0) y = MainGame.HEIGHT;
        if (y > MainGame.HEIGHT) y = 0;


    }

}
