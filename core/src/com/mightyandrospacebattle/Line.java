package com.mightyandrospacebattle;

import com.badlogic.gdx.math.Vector2;

/**
 * Created by Michal on 24.06.2017.
 */

public class Line {
    public Vector2 v1;
    public Vector2 v2;

    public Line(Vector2 v1, Vector2 v2) {
        this.v1 = v1;
        this.v2 = v2;
    }

    public Line(float x1, float y1, float x2, float y2) {
        this.v1 = new Vector2(x1, y1);
        this.v2 = new Vector2(x2, y2);
    }
}
