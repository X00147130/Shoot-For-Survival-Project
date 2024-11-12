package com.mygdx.sfs.Scenes.Screens;

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
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
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

    private int area = 1;
    private int map = 1;

    private Texture background;

    //buttons
    private Label playAgainButton;
    private Label mainMenuButton;

    public GameOverScreen(shootForSurvival game,int location, int level){
        this.GAME = game;
        viewport = new FitViewport(shootForSurvival.V_WIDTH, shootForSurvival.V_HEIGHT, new OrthographicCamera());
        stage = new Stage(viewport, GAME.batch);
        this.area = location;
        this.map = level;

        background = GAME.manager.get("backgrounds/deadbg.png", Texture.class);

        Label.LabelStyle font = new Label.LabelStyle(new BitmapFont(Gdx.files.internal("skins/CyberpunkCraftpixFont.fnt")), Color.RED);
        Label.LabelStyle buttonFont = new Label.LabelStyle(new BitmapFont(Gdx.files.internal("skins/CyberpunkCraftpixFont.fnt")), Color.WHITE);

        table = new Table();
        table.center();
        table.setFillParent(true);

        playAgainButton = new Label(" Play Again? ", buttonFont);
        mainMenuButton = new Label(" Main Menu ",buttonFont);
        playAgainButton.setFontScale(0.4f, 0.4f);
        mainMenuButton.setFontScale(0.4f, 0.4f);


        Label gameOverLabel = new Label("YOU GOT", font);
        gameOverLabel.setFontScale(0.8f, 0.5f);
        Label gameOverLabel2 = new Label("SCRAPPED!!!", font);
        gameOverLabel2.setFontScale(0.8f, 0.7f);
        table.add(gameOverLabel).expandX().center();
        table.row();
        table.add(gameOverLabel2).expandX().center();
        table.row();
        table.add(playAgainButton).expandX().padTop(30).center();
        table.row();
        table.add(mainMenuButton).expandX().padTop(10).center();
        stage.addActor(table);
        Gdx.input.setInputProcessor(stage);


       // makes button take us to new playscreen
        playAgainButton.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y){
                if(Gdx.app.getType() == Application.ApplicationType.Desktop) {
                    GAME.loadSound("audio/sounds/421837__prex2202__blipbutton.mp3");
                    long id = GAME.sound.play();
                    if (GAME.getSoundVolume() != 0)
                        GAME.sound.setVolume(id, GAME.getSoundVolume());
                    else {
                        GAME.sound.setVolume(id, 0);
                    }
                }
                if(Gdx.app.getType() == Application.ApplicationType.Android) {
                    GAME.manager.get("audio/sounds/421837__prex2202__blipbutton.mp3", Sound.class).play(GAME.getSoundVolume());
                }

                GAME.music.stop();
                GAME.setScreen(new PlayScreen(GAME,area,map));
            }
        });

        //Makes Button take us to main menu
        mainMenuButton.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y){
                if(Gdx.app.getType() == Application.ApplicationType.Desktop) {
                    GAME.loadSound("audio/sounds/421837__prex2202__blipbutton.mp3");
                    long id = GAME.sound.play();
                    if (GAME.getSoundVolume() != 0)
                        GAME.sound.setVolume(id, GAME.getSoundVolume());
                    else {
                        GAME.sound.setVolume(id, 0);
                    }
                }
                if(Gdx.app.getType() == Application.ApplicationType.Android) {
                    GAME.manager.get("audio/sounds/421837__prex2202__blipbutton.mp3", Sound.class).play(GAME.getSoundVolume());
                }

                GAME.setHealthAtlas(new TextureAtlas("sprites/Objects/HealthCrate.pack"));
                GAME.setKeycardAtlas(new TextureAtlas("sprites/Objects/keycard.pack"));
                GAME.setDoorAtlas(new TextureAtlas("sprites/Objects/industrialDoor.pack"));
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
