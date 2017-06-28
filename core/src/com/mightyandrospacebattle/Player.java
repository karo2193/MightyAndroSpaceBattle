package com.mightyandrospacebattle;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;

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

    private boolean hit;
    private boolean dead;

    private float hitTimer;
    private float hitTime;

    private Line[] hitLines;
    private Vector2[] hitLinesVector;

    private long score;
    private int extraLives;
    private long requiredScore;

    public Player(ArrayList<Bullet> bullets) {

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

        hit = false;
        hitTimer = 0;
        hitTime = 2;

        radians = (float) Math.PI / 2;
        rotationSpeed = 3;

        score = 0;
        extraLives = 3;
        //extraLives = 5; Easy Q2
        requiredScore = 100;
        //requiredScore = 2000; Easy Q3
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

    public void reset(){
        x= MainGame.WIDTH/2;
        y=MainGame.HEIGHT/2;
        setShape();
        hit = false;
        dead=false;
    }

    public void setPosition(float x, float y) {
        super.setPosition(x, y);
        setShape();
    }

    public long getScore() {
        return score;
    }

    public int getExtraLives() {
        return extraLives;
    }

    public void loseLife() {
        extraLives--;
    }

    public void incrementScore(long l) {
        score += l;
    }

    public void shoot() {
        if (bullets.size() == MAX_BULLETS) return;
        bullets.add(new Bullet(x, y, radians));
        Jukebox.play("shoot");
    }

    public void hit() {

        if (hit) return;
        hit = true;
        dx = 0;
        dy = 0;
        hitLines = new Line[4];
        for (int i = 0, j = hitLines.length - 1; i < hitLines.length; j = i++) {
            hitLines[i] = new Line(shapex[i], shapey[i], shapex[j], shapey[j]);
        }
        hitLinesVector = new Vector2[4];
        hitLinesVector[0] = new Vector2(
                MathUtils.cos(radians + 1.5f),
                MathUtils.sin(radians + 1.5f)
        );
        hitLinesVector[1] = new Vector2(
                MathUtils.cos(radians - 1.5f),
                MathUtils.sin(radians - 1.5f)
        );
        hitLinesVector[2] = new Vector2(
                MathUtils.cos(radians - 2.8f),
                MathUtils.sin(radians - 2.8f)
        );
        hitLinesVector[3] = new Vector2(
                MathUtils.cos(radians + 2.8f),
                MathUtils.sin(radians + 2.8f)
        );

    }

    public void update(float dt) {

        //check if hit
        if(hit){
            hitTimer+=dt;
            if(hitTimer>hitTime)
            {
                dead = true;
                hitTimer = 0;
            }
            for(int i=0; i<hitLines.length;i++)
            {
                hitLines[i] = new Line(
                     hitLines[i].v1.x+hitLinesVector[i].x*10*dt,
                        hitLines[i].v1.y+hitLinesVector[i].x*10*dt,
                        hitLines[i].v2.x+hitLinesVector[i].x*10*dt,
                        hitLines[i].v2.y+hitLinesVector[i].x*10*dt

                );

            }
            return;
        }
        //check extra lives
        if (score >= requiredScore) {
            extraLives++;
            requiredScore += 100;
            //requiredScore += 2000; Easy Q3
            Jukebox.play("extralife");
        }

        if (AccelerometerReadings.isLeft())
            radians += rotationSpeed * dt;
        else if (AccelerometerReadings.isRight())
            radians -= rotationSpeed * dt;
        if (AccelerometerReadings.isUp()) {
            dx += MathUtils.cos(radians) * acceleration * dt;
            dy += MathUtils.sin(radians) * acceleration * dt;
            acceleratingTimer += dt;
            if (acceleratingTimer > 0.1f) {
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
            //Jukebox.loop("thruster"); + after hit stop sound
        } else {
            //Jukebox.stop("thruster");
        }
        wrap();


    }

    public void draw(ShapeRenderer sr) {
        sr.setColor(1, 1, 1, 1);
        sr.begin(ShapeRenderer.ShapeType.Line);
        if(hit){
            for(int i =0; i< hitLines.length; i++){
                sr.line(hitLines[i].v1.x,
                        hitLines[i].v1.y,
                        hitLines[i].v2.x,
                        hitLines[i].v2.y

                        );
            }
            sr.end();
            return;
        }



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

    public boolean isHit() {
        return hit;
    }

    public boolean isDead() {
        return dead;
    }
}

