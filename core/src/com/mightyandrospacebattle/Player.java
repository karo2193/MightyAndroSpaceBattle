package com.mightyandrospacebattle;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.MathUtils;

/**
 * Created by Michal on 21.06.2017.
 */

public class Player extends SpaceObject {


    private float maxSpeed;
    private float acceleration;
    private float deceleration;

    public Player() {
        x = MainGame.WIDTH / 2;
        y = MainGame.HEIGHT / 2;
        maxSpeed = 300;
        acceleration = 200;
        deceleration = 10;
        shapex = new float[4];
        shapey = new float[4];
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

    public void update(float dt) {
        if (AccelerometerReadings.isLeft())
            radians += rotationSpeed * dt;
        else if (AccelerometerReadings.isRight())
            radians -= rotationSpeed * dt;
        if (AccelerometerReadings.isUp()) {
            dx += MathUtils.cos(radians) * acceleration * dt;
            dy += MathUtils.sin(radians) * acceleration * dt;

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
        wrap();


    }

    public void draw(ShapeRenderer sr) {
        System.out.println("dzieje sie");
        sr.setColor(1, 1, 1, 1);
        sr.begin(ShapeRenderer.ShapeType.Line);
        for (int i = 0, j = shapex.length - 1; i < shapex.length; j = i++) {
            sr.line(shapex[i], shapey[i], shapex[j], shapey[j]);
        }
        sr.end();

    }

}

