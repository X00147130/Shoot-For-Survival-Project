package com.mygdx.sfs.Scenes.Screens;

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
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mygdx.sfs.shootForSurvival;

public class Upgrade implements Screen {
    private Viewport viewport;
    private Stage stage;
    private shootForSurvival GAME;
    private Table table;
    public boolean reset = false;

    private int area = 1;
    private int map = 1;
    private int price = 0;
    private int cash = 0;

    private int pistol;
    private int rifle;

    private Texture background;

    //buttons
    private Button upgradeButton;
    private Button continueButton;

    public Upgrade(final shootForSurvival game, int location, int level){
        this.GAME = game;
        viewport = new FitViewport(shootForSurvival.V_WIDTH, shootForSurvival.V_HEIGHT, new OrthographicCamera());
        stage = new Stage(viewport, GAME.batch);
        this.area = location;
        this.map = level;
        cash = GAME.getMoney();
        pistol = GAME.getPistolLvl();
        rifle = GAME.getPowerLVL();


        switch(pistol){
            case 1:
                price  = 500;
                break;
            case 2:
                price = 1000;
                break;
            case 3:
                price  = 1500;
                break;
            case 4:
                price = 2000;
                break;
            case 5:
                price  = 2500;
                break;
            case 6:
                price = 3000;
                break;
            case 7:
                price  = 3500;
                break;
            case 8:
                price = 4000;
                break;
            case 9:
                price  = 4500;
                break;
            case 10:
                price = 5000;
                break;
        }



        background = GAME.manager.get("backgrounds/deadbg.png", Texture.class);

        Label.LabelStyle font = new Label.LabelStyle(new BitmapFont(Gdx.files.internal("skins/CyberpunkCraftpixFont.fnt")), Color.valueOf("ff0a7f"));

        table = new Table();
        table.center();
        table.setFillParent(true);

        Skin skin = new Skin(Gdx.files.internal("skins/quantum-horizon/skin/quantum-horizon-ui.json"));
        skin.setScale(0.2f);
        upgradeButton = new TextButton(String.format("Upgrade: %4d", price), skin);
        continueButton = new TextButton(" SKIP ", skin);
        Label.LabelStyle style2 = new Label.LabelStyle(new BitmapFont(Gdx.files.internal("skins/CyberpunkCraftpixFont.fnt")), Color.GREEN);

        Label upgradeLabel = new Label("NEED SOME", font);
        upgradeLabel.setFontScale(0.8f, 0.7f);
        Label gameOverLabel2 = new Label("HELP PAL???", font);
        gameOverLabel2.setFontScale(0.8f, 0.7f);
        Label Coins = new Label(String.format("Score: %4d" ,cash),style2);
        Coins.setFontScale(0.5f, 0.5f);

        table.add(upgradeLabel).expandX().center();
        table.row();
        table.add(gameOverLabel2).expandX().center();
        table.row();
        table.add(Coins).expandX().center().padTop(10);
        table.row();
        table.add(upgradeButton).expandX().padTop(10).center();
        table.row();
        table.add(continueButton).expandX().padTop(10).center();
        stage.addActor(table);
        Gdx.input.setInputProcessor(stage);



        // Upgrades pistol
        upgradeButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if(cash >= price) {
                    if (Gdx.app.getType() == Application.ApplicationType.Android) {
                        GAME.manager.get("audio/sounds/421837__prex2202__blipbutton.mp3", Sound.class).play(GAME.getSoundVolume());
                    }

                    if (Gdx.app.getType() == Application.ApplicationType.Desktop) {
                        GAME.loadSound("audio/sounds/421837__prex2202__blipbutton.mp3");
                        long id = GAME.sound.play();
                        if (GAME.getSoundVolume() != 0) {
                            GAME.sound.setVolume(id, GAME.getSoundVolume());
                        }
                        else {
                            GAME.sound.setVolume(id, 0);
                        }
                    }

                    GAME.music.stop();
                    GAME.setMoney(cash - price);
                    GAME.setStartMoney(cash - price);
                    pistol++;
                    rifle++;
                    GAME.setPistolLvl(pistol);
                    GAME.setPowerLVL(rifle);
                    GAME.setScreen(new PlayScreen(GAME, area, map));
                }

                else if(cash < price){
                    if (Gdx.app.getType() == Application.ApplicationType.Android) {
                        GAME.manager.get("audio/sounds/stomp.wav", Sound.class).play(GAME.getSoundVolume());
                    }

                    if (Gdx.app.getType() == Application.ApplicationType.Desktop) {
                        GAME.loadSound("audio/sounds/stomp.wav");
                        long id = GAME.sound.play();
                        if (GAME.getSoundVolume() != 0) {
                            GAME.sound.setVolume(id, GAME.getSoundVolume());
                        }
                        else {
                            GAME.sound.setVolume(id, 0);
                        }
                    }
                }
            }
        });

        //Button for the continuing
        continueButton.addListener(new ClickListener(){
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
                GAME.setScreen(new PlayScreen(GAME, area, map));
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
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        GAME.batch.begin();
        GAME.batch.draw(background, 0, 0, 400, 300);
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
