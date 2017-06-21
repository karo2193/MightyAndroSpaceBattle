package com.mightyandrospacebattle;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.MathUtils;

import java.util.ArrayList;

/**
 * Created by Michal on 21.06.2017.
 */

public class Player extends SpaceObject {

    private final int MAX_BULLETS = 4;

    private ArrayList<Bullet> bullets;

    private float[] flamex;
    private float[] flamey;

    private float maxSpeed;
    private float acceleration;
    private float deceleration;
    private float acceleratingTimer;

    public Player(ArrayList<Bullet> bullets ) {

        this.bullets = bullets;

        x = MainGame.WIDTH / 2;
        y = MainGame.HEIGHT / 2;
        maxSpeed = 300;
        acceleration = 200;
        deceleration = 10;

        shapex = new float[4];
        shapey = new float[4];
        flamex = new float[3];
        flamey = new float[3];

        radians = (float) Math.PI / 2;
        rotationSpeed = 3;


    }

    private void setShape() {
        shapex[0] = x + MathUtils.cos(radians) * 8;
        shapey[0] = y + MathUtils.sin(radians) * 8;

        shapex[1] = x + MathUtils.cos(radians - 4 * MathUtils.PI / 5) * 8;
        shapey[1] = y + MathUtils.sin(radians - 4 * MathUtils.PI / 5) * 8;

        shapex[2] = x + MathUtils.cos(radians + MathUtils.PI) * 5;
        shapey[2] = y + MathUtils.sin(radians + MathUtils.PI) * 5;

        shapex[3] = x + MathUtils.cos(radians + 4 * MathUtils.PI / 5) * 8;
        shapey[3] = y + MathUtils.sin(radians + 4 * MathUtils.PI / 5) * 8;

    }

    private void setFlame() {

        flamex[0] = x + MathUtils.cos(radians - 5 * MathUtils.PI / 6) * 5;
        flamey[0] = y + MathUtils.sin(radians - 5 * MathUtils.PI / 6) * 5;

        flamex[1] = x + MathUtils.cos(radians - MathUtils.PI) * (6 + acceleratingTimer * 50);
        flamey[1] = y + MathUtils.sin(radians - MathUtils.PI) * (6 + acceleratingTimer * 50);

        flamex[2] = x + MathUtils.cos(radians + 5 * MathUtils.PI / 6) * 5;
        flamey[2] = y + MathUtils.cos(radians + 5 * MathUtils.PI / 6) * 5;

    }

    public void shoot() {

        if (bullets.size() == MAX_BULLETS) {
            return;
        } else {
            bullets.add(new Bullet(x, y, radians));
        }

    }

    public void update(float dt) {
        if (AccelerometerReadings.isLeft())
            radians += rotationSpeed * dt;
        else if (AccelerometerReadings.isRight())
            radians -= rotationSpeed * dt;
        if (AccelerometerReadings.isUp()) {
            dx += MathUtils.cos(radians) * acceleration * dt;
            dy += MathUtils.sin(radians) * acceleration * dt;
            acceleratingTimer += dt;
            if(acceleratingTimer > 0.1f) {
                acceleratingTimer = 0;
            }
        } else {
            acceleratingTimer = 0;
        }
        float vec = (float) Math.sqrt(dx * dx + dy * dy);
        if (vec > 0) {
            dx -= (dx / vec) * deceleration * dt;
            dy -= (dy / vec) * deceleration * dt;
        }
        if (vec > maxSpeed) {
            dx = (dx / vec) * maxSpeed;
            dy = (dy / vec) * maxSpeed;

        }
        x += dx * dt;
        y += dy * dt;
        setShape();
        if (AccelerometerReadings.isUp()) {
            setFlame();
        }
        wrap();


    }

    public void draw(ShapeRenderer sr) {
        System.out.println("dzieje sie");
        sr.setColor(1, 1, 1, 1);
        sr.begin(ShapeRenderer.ShapeType.Line);

        //draw ship
        for (int i = 0, j = shapex.length - 1; i < shapex.length; j = i++) {
            sr.line(shapex[i], shapey[i], shapex[j], shapey[j]);
        }

        //draw flames
        if (AccelerometerReadings.isUp()) {
            for (int i = 0, j = flamex.length - 1; i < flamex.length; j = i++) {
                sr.line(flamex[i], flamey[i], flamex[j], flamey[j]);
            }
        }
        sr.end();

    }

}

