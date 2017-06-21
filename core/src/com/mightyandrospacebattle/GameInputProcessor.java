package com.mightyandrospacebattle;

import com.badlogic.gdx.InputAdapter;

import java.io.Console;

/**
 * Created by Michal on 21.06.2017.
 */

public class GameInputProcessor extends InputAdapter {
    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        System.out.println("down" + screenX + " " + screenY + " " + pointer + " " + button);
        return super.touchDown(screenX, screenY, pointer, button);
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        return super.touchUp(screenX, screenY, pointer, button);
    }
}
