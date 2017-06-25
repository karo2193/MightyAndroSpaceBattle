package com.mightyandrospacebattle;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.MathUtils;

import java.util.ArrayList;

/**
 * Created by Michal on 16.06.2017.
 */

public class PlayState extends GameState {

    private SpriteBatch sb;
    private ShapeRenderer sr;
    private BitmapFont font;
    private Player player;
    private ArrayList<Bullet> bullets;
    private ArrayList<Asteroid> asteroids;

    private ArrayList<Particle> particles;

    private int level;
    private int totalAsteroids;
    private int numAsteroidsLeft;

    protected PlayState(GameStateManager gsm) {
        super(gsm);

    }

    @Override
    public void init() {
        sb = new SpriteBatch();
        System.out.println("kom2");
        sr = new ShapeRenderer();

        initFont();

        bullets = new ArrayList<Bullet>();
        player = new Player(bullets);
        asteroids = new ArrayList<Asteroid>();

        particles = new ArrayList<Particle>();
        level = 1;
        spawnAsteroids();
    }

    private void initFont() {
        FreeTypeFontGenerator generator =
                new FreeTypeFontGenerator(Gdx.files.internal("fonts/hyperspacebold.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter parameter =
                new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size = 50;
        font = generator.generateFont(parameter);
        generator.dispose();
    }

    private void createParticles(float x, float y) {

        for (int i = 0; i < 6; i++)
            particles.add(new Particle(x, y));
    }

    private void spawnAsteroids() {

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
        if (asteroids.size() == 0) {
            level++;
            spawnAsteroids();

        }
        player.update(dt);
        if (player.isDead()) {
            player.reset();
            player.loseLife();
            //why without return; ?
        }
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
        //update particles
        for (int i = 0; i < particles.size(); i++) {
            particles.get(i).update(dt);
            if (particles.get(i).shouldRemove()) {
                particles.remove(i);
                i--;
            }
        }
        //check collision
        checkCollisions();
    }

    private void checkCollisions() {

        // player - asteroid collision
        if (!player.isHit()) {
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
                    player.incrementScore(asteroid.getScore());
                    break;
                }
            }
        }

    }

    private void splitAsteroids(Asteroid a) {

        createParticles(a.getx(), a.gety());
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
        for (int i = 0; i < bullets.size(); i++) {
            bullets.get(i).draw(sr);
        }

        //draw asteroids
        for (int i = 0; i < asteroids.size(); i++) {
            asteroids.get(i).draw(sr);
        }

        //draw particles
        for (int i = 0; i < particles.size(); i++) {

            particles.get(i).draw(sr);
        }

        //draw score
        sb.setColor(1, 1, 1, 1);
        sb.begin();
        font.draw(sb, Long.toString(player.getScore()), 40, 700);
        sb.end();
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
