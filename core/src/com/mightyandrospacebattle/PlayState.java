package com.mightyandrospacebattle;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

import java.util.ArrayList;

/**
 * Created by Michal on 16.06.2017.
 */

public class PlayState extends GameState {

    private ShapeRenderer sr;
    private Player player;
    private ArrayList<Bullet> bullets;

    protected PlayState(GameStateManager gsm) {
        super(gsm);

    }

    @Override
    public void init() {
        System.out.println("kom2");
        sr = new ShapeRenderer();
        bullets = new ArrayList<Bullet>();
        player = new Player(bullets);

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
