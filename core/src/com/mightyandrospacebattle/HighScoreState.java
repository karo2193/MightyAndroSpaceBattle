package com.mightyandrospacebattle;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeType;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;

import java.lang.String;

/**
 * Created by Michal on 26.06.2017.
 */

public class HighScoreState extends GameState {

    private SpriteBatch sb;

    private BitmapFont font;

    private long[] highScores;
    private String[] names;
    private GlyphLayout layout;

    public HighScoreState(GameStateManager gsm) {
        super(gsm);
    }

    @Override
    public void init() {
        sb = new SpriteBatch();
        layout = new GlyphLayout();
        FreeTypeFontGenerator generator =
                new FreeTypeFontGenerator(Gdx.files.internal("fonts/hyperspacebold.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter parameter =
                new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size = 25;
        font = generator.generateFont(parameter);
        generator.dispose();
        Save.load();
        highScores = Save.gd.getHighScores();
        names = Save.gd.getNames();
    }

    @Override
    public void update(float dt) {
        handleInput();
    }

    @Override
    public void draw() {
        sb.setProjectionMatrix(MainGame.cam.combined);
        sb.begin();
        String s = "High Scores";
        float w;


        layout.setText(font, s);
        w = layout.width;
        font.draw(sb, s, (MainGame.WIDTH - w) / 2, 300);
        for (int i = 0; i < highScores.length; i++) {
            s = String.format("%2d. %7s %s", i + 1, highScores[i], names[i]);
            layout.setText(font, s);
            w = layout.width;
            font.draw(sb, s, (MainGame.WIDTH - w) / 2, 280 - 25 * i);
        }
        sb.end();
    }

    @Override
    public void handleInput() {

    }

    @Override
    public void dispose() {

    }
}
