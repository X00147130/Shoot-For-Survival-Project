package com.mygdx.sfs.Scenes.Screens;

import static com.badlogic.gdx.graphics.Color.RED;
import static com.badlogic.gdx.graphics.Color.WHITE;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mygdx.sfs.shootForSurvival;


public class GameOverScreen implements Screen {
    private Viewport viewport;
    private Stage stage;
    private shootForSurvival GAME;
    private Table table;
    public boolean reset = false;

    private int map = 1;

    private Texture background;

    //buttons
    private Button playAgainButton;
    private Button mainMenuButton;

    public GameOverScreen(shootForSurvival game, int level){
        this.GAME = game;
        viewport = new FitViewport(shootForSurvival.V_WIDTH, shootForSurvival.V_HEIGHT, new OrthographicCamera());
        stage = new Stage(viewport, GAME.batch);
        this.map = level;

        background = GAME.manager.get("backgrounds/deadbg.png", Texture.class);

        Label.LabelStyle font = new Label.LabelStyle(new BitmapFont(Gdx.files.internal("skins/quantum-horizon/raw/font-title-export.fnt")), Color.valueOf("ff0a7f"));

        table = new Table();
        table.center();
        table.setFillParent(true);

        BitmapFont buttonFont;
        TextButton.TextButtonStyle buttonStyle;
        buttonStyle = new TextButton.TextButtonStyle();
        buttonFont = new BitmapFont(Gdx.files.internal("skins/quantum-horizon/raw/font-export.fnt"));
        buttonStyle.font= buttonFont;
        buttonStyle.fontColor = WHITE;
        playAgainButton = new TextButton(" Play Again? ", buttonStyle);
        mainMenuButton = new TextButton(" Main Menu ", buttonStyle);


        Label gameOverLabel = new Label("YOU GOT", font);
        Label gameOverLabel2 = new Label("SCRAPPED!!!", font);
        table.add(gameOverLabel).expandX().center();
        table.row();
        table.add(gameOverLabel2).expandX().center();
        table.row();
        table.add(playAgainButton).expandX().padTop(10).center();
        table.row();
        table.add(mainMenuButton).expandX().padTop(10).center();
        stage.addActor(table);
        Gdx.input.setInputProcessor(stage);


       // makes button take us to new playscreen
        playAgainButton.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y){
                if(Gdx.app.getType() == Application.ApplicationType.Desktop) {
                    GAME.loadSound("audio/sounds/mixkit-gear-metallic-lock-sound-2858.wav");
                    long id = GAME.sound.play();
                    if (GAME.getSoundVolume() != 0)
                        GAME.sound.setVolume(id, GAME.getSoundVolume());
                    else {
                        GAME.sound.setVolume(id, 0);
                    }
                }
                if(Gdx.app.getType() == Application.ApplicationType.Android) {
                    GAME.manager.get("audio/sounds/mixkit-gear-metallic-lock-sound-2858.wav", Sound.class).play(GAME.getSoundVolume());
                }

                GAME.music.stop();
                GAME.setScreen(new PlayScreen(GAME,map));
            }
        });

        //Makes Button take us to main menu
        mainMenuButton.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y){
                if(Gdx.app.getType() == Application.ApplicationType.Desktop) {
                    GAME.loadSound("audio/sounds/mixkit-gear-metallic-lock-sound-2858.wav");
                    long id = GAME.sound.play();
                    if (GAME.getSoundVolume() != 0)
                        GAME.sound.setVolume(id, GAME.getSoundVolume());
                    else {
                        GAME.sound.setVolume(id, 0);
                    }
                }
                if(Gdx.app.getType() == Application.ApplicationType.Android) {
                    GAME.manager.get("audio/sounds/mixkit-gear-metallic-lock-sound-2858.wav", Sound.class).play(GAME.getSoundVolume());
                }


                GAME.music.stop();
                GAME.setScreen(new MenuScreen(GAME));
            }
        });
        GAME.loadMusic("audio/music/mixkit-piano-horror-671.mp3");
        if(GAME.getVolume() != 0) {
            GAME.music.setVolume(GAME.getVolume());
            GAME.music.play();
        }

    }

    public boolean isReset() {
        return reset;
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0,0,0,1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        GAME.batch.begin();
        GAME.batch.draw(background,0,0,400,300);
        GAME.batch.end();
        stage.draw();
    }




    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width,height,true);
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {
        stage.dispose();
        GAME.dispose();
    }
}
