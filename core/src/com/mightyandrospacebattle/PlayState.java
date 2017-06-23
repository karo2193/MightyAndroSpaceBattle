package com.mightyandrospacebattle;


import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.MathUtils;

import java.util.ArrayList;
import java.util.jar.Manifest;

/**
 * Created by Michal on 16.06.2017.
 */

public class PlayState extends GameState {

    private ShapeRenderer sr;
    private Player player;
    private ArrayList<Bullet> bullets;
    private ArrayList<Asteroid> asteroids;

    private int level;
    private int totalAsteroids;
    private int numAsteroidsLeft;

    protected PlayState(GameStateManager gsm) {
        super(gsm);

    }

    @Override
    public void init() {

        System.out.println("kom2");
        sr = new ShapeRenderer();
        bullets = new ArrayList<Bullet>();
        player = new Player(bullets);
        asteroids = new ArrayList<Asteroid>();
        asteroids.add(new Asteroid(100, 100, Asteroid.LARGE));
        asteroids.add(new Asteroid(200, 200, Asteroid.MEDIUM));
        asteroids.add(new Asteroid(300, 300, Asteroid.SMALL));

        level = 1;
        spawnAsteroids();

    }

    private void  spawnAsteroids() {

        asteroids.clear();

        int numToSpawn = 4 + level * level - 1;
        totalAsteroids = numToSpawn * 7;
        numAsteroidsLeft = totalAsteroids;

        for (int i = 0; i < numToSpawn; i++) {

            float x = MathUtils.random(Gdx.graphics.getWidth());
            float y = MathUtils.random(Gdx.graphics.getHeight());

            float dx = x - player.getx();
            float dy = y - player.gety();
            float dist = (float) Math.sqrt(dx * dx + dy * dy);

            while (dist < 100) {
                x = MathUtils.random(Gdx.graphics.getWidth());
                y = MathUtils.random(Gdx.graphics.getHeight());

                dx = x - player.getx();
                dy = y - player.gety();
                dist = (float) Math.sqrt(dx * dx + dy * dy);
            }

            asteroids.add(new Asteroid(x, y, Asteroid.LARGE));
        }

    }

    @Override
    public void update(float dt) {

        //System.out.println("play state update");
        player.update(dt);

        //update player bullets
        for (int i = 0; i < bullets.size(); i++) {
            bullets.get(i).update(dt);
            if (bullets.get(i).shouldRemove()) {
                bullets.remove(i);
                i--;
            }
        }

        //update asteroids
        for (int i = 0; i < asteroids.size(); i++) {
            asteroids.get(i).update(dt);
            if (asteroids.get(i).shouldRemove()) {
                asteroids.remove(i);
                i--;
            }
        }

        //check collision
        checkCollisions();

    }

    private void checkCollisions() {

        // player - asteroid collision
        for (int i = 0; i < asteroids.size(); i++) {
            Asteroid asteroid = asteroids.get(i);
            if (asteroid.intersects(player)) {
                player.hit();
                asteroids.remove(i);
                i--;
                splitAsteroids(asteroid);
                break;
            }
        }

        // bullet - asteroid collision
        for (int i = 0; i < bullets.size(); i++) {
            Bullet bullet = bullets.get(i);
            for (int j = 0; j < asteroids.size(); j++) {
                Asteroid asteroid = asteroids.get(j);
                if (asteroid.contains(bullet.getx(), bullet.gety())) {
                    bullets.remove(i);
                    i--;
                    asteroids.remove(j);
                    j--;
                    splitAsteroids(asteroid);
                    break;
                }
            }
        }

    }

    private void splitAsteroids(Asteroid a) {

        numAsteroidsLeft--;
        if (a.getType() == Asteroid.LARGE) {
            asteroids.add(new Asteroid(a.getx(), a.gety(), Asteroid.MEDIUM));
            asteroids.add(new Asteroid(a.getx(), a.gety(), Asteroid.MEDIUM));
        }
        if (a.getType() == Asteroid.MEDIUM) {
            asteroids.add(new Asteroid(a.getx(), a.gety(), Asteroid.SMALL));
            asteroids.add(new Asteroid(a.getx(), a.gety(), Asteroid.SMALL));
        }

    }

    @Override
    public void draw() {

        handleInput();
        //System.out.println("play state draw");
        player.draw(sr);

        //draw bullets
        for(int i = 0; i < bullets.size(); i++) {
            bullets.get(i).draw(sr);
        }

        //draw asteroids
        for(int i = 0; i < asteroids.size(); i++) {
            asteroids.get(i).draw(sr);
        }

    }

    @Override
    public void handleInput() {
        if (Gdx.app.getInput().justTouched()) {
            player.shoot();
        }
    }

    @Override
    public void dispose() {

    }
}
