package com.mightyandrospacebattle;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Cursor;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;

/**
 * Created by kryguu on 27.06.2017.
 */

public class GameOverState extends GameState {

    private final String NAME = "name";
    private final String GAME_OVER = "Game Over";
    private final String NEW_HIGH_SCORE = "New High Score";

    private SpriteBatch sb;
    private Stage stage;

    private boolean newHighscore;
    private String newHighscoreText;

    private BitmapFont gameOverFont;
    private BitmapFont font;
    private GlyphLayout glyphLayout;
    private float textWidth;
    private TextField nameField;
    private TextField.TextFieldStyle textFieldStyle;

    public GameOverState(GameStateManager gsm) {
        super(gsm);
    }

    @Override
    public void init() {

        sb = new SpriteBatch();
        stage = new Stage();
        Gdx.input.setInputProcessor(stage);
        glyphLayout = new GlyphLayout();

        initFonts();

        newHighscore = Save.gd.isHighScore(Save.gd.getTentativeScore());
        if (newHighscore) {
            initTextField();
        }
    }

    private void initTextField() {
        textFieldStyle = new TextField.TextFieldStyle();
        textFieldStyle.font = gameOverFont;
        textFieldStyle.fontColor = Color.WHITE;
        nameField = new TextField("name", textFieldStyle);
        nameField.setWidth(300);
        nameField.setPosition((MainGame.WIDTH - 300) / 2, 400);
        //nameField.setMaxLength(6); Medium Q5
        nameField.setTextFieldListener(new TextField.TextFieldListener() {
            @Override
            public void keyTyped(TextField textField, char c) {
                if (c == '\n') {
                    if (newHighscore) {
                        Save.gd.addHighScore(Save.gd.getTentativeScore(), textField.getText());
                        Save.save();
                    }
                    gsm.setState(GameStateManager.MENU);
                }
//                if(c == ' ') { Medium Q5
//                    textField.setText(textField.getText().replace(" ", "")); Medium Q5
//                    nameField.setCursorPosition(textField.getText().length()); Medium Q5
//                }
            }
        });
        stage.addActor(nameField);
    }

    @Override
    public void update(float dt) {
        handleInput();
    }

    @Override
    public void draw() {
        stage.draw();
        sb.setProjectionMatrix(MainGame.cam.combined);
        sb.begin();

        calculateTextWidth(gameOverFont, GAME_OVER);
        gameOverFont.draw(sb, GAME_OVER, (MainGame.WIDTH - textWidth) / 2, 650);

        if (!newHighscore) {
            sb.end();
            return;
        }

        newHighscoreText = NEW_HIGH_SCORE + Save.gd.getTentativeScore();
        calculateTextWidth(gameOverFont, newHighscoreText);
        gameOverFont.draw(sb, newHighscoreText, (MainGame.WIDTH - textWidth) / 2, 550);

        sb.end();
    }

    private void initFonts() {
        FreeTypeFontGenerator generator =
                new FreeTypeFontGenerator(Gdx.files.internal("fonts/hyperspacebold.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter parameter =
                new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size = 60;
        gameOverFont = generator.generateFont(parameter);
        gameOverFont.setColor(Color.WHITE);
        parameter.size = 50;
        font = generator.generateFont(parameter);
        font.setColor(Color.WHITE);
    }

    private void calculateTextWidth(BitmapFont font, String text) {
        glyphLayout.setText(font, text);
        textWidth = glyphLayout.width;
    }

    @Override
    public void handleInput() {
        if (!newHighscore) {
            if (Gdx.app.getInput().justTouched()) {
                gsm.setState(GameStateManager.MENU);
            }
        }
    }

    @Override
    public void dispose() {

    }
}
