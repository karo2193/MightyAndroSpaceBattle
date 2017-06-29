package com.mightyandrospacebattle;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.MathUtils;

/**
 * Created by kryguu on 21.06.2017.
 */

class Asteroid extends SpaceObject {

    private int type;
    public static final int SMALL = 0;
    public static final int MEDIUM = 1;
    public static final int LARGE = 2;
    //public static final int VERY_LARGE = 3; // Hard Q4

    private int numPoints;
    private float[] dists;

    private int score;

    private boolean remove;

    public Asteroid(float x, float y, int type) {

        this.x = x;
        this.y = y;
        this.type = type;

        if (type == SMALL) {
            numPoints = 8;
            //numPoints = 3; Medium Q1
            width = height = 12;
            //width = height = 20; Medium Q1
            speed = MathUtils.random(70, 100);
            score = 100;
        } else if (type == MEDIUM) {
            numPoints = 10;
            //numPoints = 4; Medium Q1
            width = height = 20;
            //width = height = 40; Medium Q1
            speed = MathUtils.random(40, 60);
            score = 50;
        } else if (type == LARGE) {
            numPoints = 12;
            //numPoints = 6; Medium Q1
            width = height = 40;
            //width = height = 60; Medium Q1
            speed = MathUtils.random(20, 30);
            score = 20;
        }

        // Hard Q4
//        switch(type) {
//            case SMALL:
//                numPoints = 8;
//                width = height = 12;
//                speed = MathUtils.random(70, 100);
//                score = 100;
//                break;
//            case MEDIUM:
//                numPoints = 10;
//                width = height = 20;
//                speed = MathUtils.random(40, 60);
//                score = 60;
//                break;
//            case LARGE:
//                numPoints = 12;
//                width = height = 40;
//                speed = MathUtils.random(20, 30);
//                score = 30;
//                break;
//            case VERY_LARGE:
//                numPoints = 14;
//                width = height = 60;
//                speed = MathUtils.random(10, 15);
//                score = 10;
//                break;
//        }

        rotationSpeed = MathUtils.random(-1, 1);
        radians = MathUtils.random(2 * MathUtils.PI);
        dx = MathUtils.cos(radians) * speed;
        dy = MathUtils.sin(radians) * speed;

        shapex = new float[numPoints];
        shapey = new float[numPoints];
        dists = new float[numPoints];

        int radius = width / 2;
        for(int i = 0; i < numPoints; i++) {
            dists[i] = MathUtils.random(radius/2, radius);
        }

        setShape();

    }

    private void setShape() {

        float angle = 0;

        for (int i = 0; i < numPoints; i++) {
            shapex[i] = x + MathUtils.cos(angle + radians) * dists[i];
            shapey[i] = y + MathUtils.sin(angle + radians) * dists[i];
            angle += 2 * MathUtils.PI / numPoints;
        }

    }

    public int getType() {
        return type;
    }

    public int getScore() {
        return score;
    }
    public boolean shouldRemove() {
        return remove;
    }

    public void update(float dt) {

        x += dx * dt;
        y += dy * dt;

        radians += rotationSpeed * dt;
        setShape();

        wrap();

    }

    public void draw(ShapeRenderer sr) {
        sr.setColor(1, 1, 1, 1);
        sr.begin(ShapeRenderer.ShapeType.Line);
        for (int i = 0, j = shapex.length - 1; i < shapex.length; j = i++) {
            sr.line(shapex[i], shapey[i], shapex[j], shapey[j]);
        }
        sr.end();
    }

}
