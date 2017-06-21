package com.mightyandrospacebattle;


import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

/**
 * Created by Michal on 16.06.2017.
 */

public class PlayState extends GameState {

    private ShapeRenderer sr;
    private Player player;

    protected PlayState(GameStateManager gsm) {
        super(gsm);

    }

    @Override
    public void init() {
        System.out.println("kom2");
        sr = new ShapeRenderer();
        player = new Player();
    }

    @Override
    public void update(float dt) {
        //System.out.println("play state update");
        player.update(dt);
    }

    @Override
    public void draw() {

        //System.out.println("play state draw");
        player.draw(sr);

    }

    @Override
    public void handleInput() {

    }

    @Override
    public void dispose() {

    }
}
