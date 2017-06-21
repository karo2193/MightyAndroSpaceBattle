package com.mightyandrospacebattle;

import com.badlogic.gdx.Gdx;

/**
 * Created by Michal on 21.06.2017.
 */

public class AccelerometerReadings {

    public static boolean isUp() {

        float x = Gdx.input.getAccelerometerX();
        float y = Gdx.input.getAccelerometerY();
        float z = Gdx.input.getAccelerometerZ();
        boolean result = x > 2 && Math.abs(x) > Math.abs(y);
        if (result) System.out.println("gora");
        return result;
    }

    public static boolean isRight() {

        float x = Gdx.input.getAccelerometerX();
        float y = Gdx.input.getAccelerometerY();
        float z = Gdx.input.getAccelerometerZ();

        boolean result = y > 2 && Math.abs(x) < Math.abs(y);
        if (result) System.out.println("praw");
        return result;
    }

    public static boolean isLeft() {

        float x = Gdx.input.getAccelerometerX();
        float y = Gdx.input.getAccelerometerY();
        float z = Gdx.input.getAccelerometerZ();

        boolean result = y < -2 && Math.abs(x) < Math.abs(y);
        if (result) System.out.println("gora");
        return result;
    }
}
