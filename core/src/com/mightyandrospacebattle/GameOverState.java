package com.mightyandrospacebattle;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.sun.org.apache.xpath.internal.operations.String;

/**
 * Created by kryguu on 27.06.2017.
 */

public class GameOverState extends GameState {

    private SpriteBatch sb;
    private ShapeRenderer sr;

    private boolean newHighscore;
    private char[] newName;
    private int currentChar;

    private BitmapFont gameOverFont;
    private BitmapFont font;

    public GameOverState(GameStateManager gsm) {
        super(gsm);
    }

    @Override
    public void init() {

        sb = new SpriteBatch();
        sr = new ShapeRenderer();

        newHighscore = Save.gd.isHighScore(Save.gd.getTentativeScore());
        if (newHighscore) {
            newName = new char[] {'A','A','A'};
            currentChar = 0;
        }

        FreeTypeFontGenerator gen = new FreeTypeFontGenerator(
                Gdx.files.internal("fonts/Hyperspace Bold.ttf")
        );
        gameOverFont = gen.generateFont(32);
        font = gen.generateFont(32);


    }

    @Override
    public void update(float dt) {
        handleInput();
    }

    @Override
    public void draw() {
        sb.setProjectionMatrix(MainGame.cam.combined);
        sb.begin();

        java.lang.String s;
        float w;
        s = "Game Over";
        GlyphLayout layout = new GlyphLayout(); //dont do this every frame! Store it as member
        layout.setText(gameOverFont, (CharSequence) s);
        w = layout.width;
        gameOverFont.draw(sb, s, (MainGame.WIDTH - w) / 2, 200);

        if (!newHighscore) {
            sb.end();
            return;
        }

        s = "New High Score: " + Save.gd.getTentativeScore();
        layout.setText(gameOverFont, (CharSequence) s);
        w = layout.width;
        gameOverFont.draw(sb, s, (MainGame.WIDTH - w) / 2, 180);

        for (int i = 0; i < newName.length; i++) {
            font.draw(sb, Character.toString(newName[i]), 230 + 14 * i, 120);
        }

        sb.end();

        sr.begin(ShapeRenderer.ShapeType.Line);
        sr.line(230 + 14 * currentChar, 100,
                244 + 14 * currentChar, 100);
        sr.end();

    }

    @Override
    public void handleInput() {
    }

    @Override
    public void dispose() {

    }


}
