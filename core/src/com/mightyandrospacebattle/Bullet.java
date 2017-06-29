package com.mightyandrospacebattle;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.MathUtils;

/**
 * Created by kryguu on 21.06.2017.
 */

class Bullet extends SpaceObject{

    private float lifeTime;
    private float lifeTimer;

    private boolean remove;

    public Bullet(float x, float y, float radians) {

        this.x = x;
        this.y = y;
        this.radians = radians;

        float speed = 350;
        // speed = 700; Medium Q2
        dx = MathUtils.cos(radians) * speed;
        dy = MathUtils.sin(radians) * speed;

        width = 2; // = 3 Medium Q2
        height = 2; // = 6 Medium Q2

        lifeTimer = 0;
        lifeTime = 1;

    }

    // Hard Q3
//    public Bullet(float x, float y, float endX, float endY) {
//        this.x = x;
//        this.y = y;
//        this.endX = endX;
//        this.endY = endY;
//
//        float vX = endX - x;
//        float vY = y - endY;
//        float normvX = vX / (float) Math.sqrt(Math.pow(vX, 2) + Math.pow(vY, 2));
//        float normvY = vY / (float) Math.sqrt(Math.pow(vX, 2) + Math.pow(vY, 2));
//
//        float speed = 350;
//        dx = normvX * speed;
//        dy = normvY * speed;
//
//        width = 2;
//        height = 2;
//        lifeTimer = 0;
//        lifeTime = 1;
//    }

    public boolean shouldRemove() {
        return remove;
    }

    public void update(float dt) {

        x += dx * dt;
        y += dy * dt;

        wrap();

        lifeTimer += dt;
        if (lifeTimer > lifeTime) {
            remove = true;
        }

    }

    public void draw(ShapeRenderer sr) {

        sr.setColor(1, 1, 1, 1);
        sr.begin(ShapeRenderer.ShapeType.Filled);
        sr.circle(x - width/2, y - height/2, width/2);
        sr.end();

    }

}
