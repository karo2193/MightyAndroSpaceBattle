package com.mightyandrospacebattle;

/**
 * Created by Michal on 16.06.2017.
 */

public class GameStateManager {
    private GameState gameState;
    public static final int MENU = 0;
    public static final int PLAY = 354531;
    public static final int HIGHSCORE = 34332;

    public GameStateManager() {
        setState(MENU);
    }

    public void setState(int state) {
        if(gameState != null) gameState.dispose();
        if (state == MENU) {
            gameState = new MenuState(this);
        }
        if (state == PLAY) {
            gameState = new PlayState(this);
        }
        if (state == HIGHSCORE) {
            gameState = new HighScoreState(this);
        }


    }

    public void update(float dt) {
        gameState.update(dt);
    }

    public void draw() {
        gameState.draw();
    }

}
