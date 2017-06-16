package com.mightyandrospacebattle;


/**
 * Created by Michal on 16.06.2017.
 */

public class PlayState extends GameState {

    protected PlayState(GameStateManager gsm) {
        super(gsm);
        init();
    }

    @Override
    public void init() {

    }

    @Override
    public void update(float dt) {
        System.out.println("play state update");
    }

    @Override
    public void draw() {
        System.out.println("play state draw");
    }

    @Override
    public void handleInput() {

    }

    @Override
    public void dispose() {

    }
}
