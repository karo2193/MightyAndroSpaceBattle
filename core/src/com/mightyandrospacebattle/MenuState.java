package com.mightyandrospacebattle;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

/**
 * Created by Karo2 on 2017-06-25.
 */

public class MenuState extends GameState {

    private final String PLAY = "Play";
    private final String HIGHSCORE = "High scores";
    private final String QUIT = "Quit";

    private SpriteBatch sb;
    private BitmapFont titleFont;
    private BitmapFont font;
    private final String title = "Mighty Andro Space Battle";

    Stage stage;
    TextButton buttonPlay;
    TextButton buttonHighScore;
    TextButton buttonQuit;
    TextButton.TextButtonStyle textButtonStyle;

    public MenuState(GameStateManager gsm) {
        super(gsm);
    }

    public void init() {
        sb = new SpriteBatch();
        stage = new Stage();
        Gdx.input.setInputProcessor(stage);
        initFont();
        initTextButtonStyle();
        initButtons();
    }

    private void initButtons() {
        createButtonPlay();
        createButtonHighscore();
        createButtonQuit();

    }

    private void createButtonPlay() {
        buttonPlay = new TextButton(PLAY, textButtonStyle);
        float width = calculateWidth(font, PLAY);
        buttonPlay.setPosition((MainGame.WIDTH - width) / 2, 450);
        buttonPlay.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                gsm.setState(GameStateManager.PLAY);
            }
        });
        stage.addActor(buttonPlay);
    }

    private void createButtonHighscore() {
        buttonHighScore = new TextButton(HIGHSCORE, textButtonStyle);
        float width = calculateWidth(font, HIGHSCORE);
        buttonHighScore.setPosition((MainGame.WIDTH - width) / 2, 370);
        buttonHighScore.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                gsm.setState(GameStateManager.HIGHSCORE);
            }
        });
        stage.addActor(buttonHighScore);
    }

    private void createButtonQuit() {
        buttonQuit = new TextButton(QUIT, textButtonStyle);
        float width = calculateWidth(font, QUIT);
        buttonQuit.setPosition((MainGame.WIDTH - width) / 2, 290);
        buttonQuit.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Gdx.app.exit();
            }
        });
        stage.addActor(buttonQuit);
    }

    private void initTextButtonStyle() {
        textButtonStyle = new TextButton.TextButtonStyle();
        textButtonStyle.font = font;
    }

    private void initFont() {
        FreeTypeFontGenerator generator =
                new FreeTypeFontGenerator(Gdx.files.internal("fonts/hyperspacebold.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter parameter =
                new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size = 60;
        titleFont = generator.generateFont(parameter);
        titleFont.setColor(com.badlogic.gdx.graphics.Color.WHITE);
        parameter.size = 50;
        font = generator.generateFont(parameter);
    }

    public void update(float dt) {
        handleInput();
    }

    public void draw() {
        stage.draw();
        sb.setProjectionMatrix(MainGame.cam.combined);
        sb.begin();
        float width = calculateWidth(titleFont, title);
        titleFont.draw(sb, title, (MainGame.WIDTH - width) / 2, 600);
        sb.end();
    }

    public void handleInput() {
    }

    public void dispose() {
    }

    private float calculateWidth(BitmapFont font, String text) {
        GlyphLayout glyphLayout = new GlyphLayout(font, text);
        float width = glyphLayout.width;
        return width;
    }
}

