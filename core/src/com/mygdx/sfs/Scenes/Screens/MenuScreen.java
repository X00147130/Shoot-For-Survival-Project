package com.mygdx.sfs.Scenes.Screens;

import static com.badlogic.gdx.graphics.Color.CYAN;
import static com.badlogic.gdx.graphics.Color.MAGENTA;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
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


public class MenuScreen implements Screen  {

    private AssetManager manager;
    private Viewport viewport;
    private Stage stage;
    private final shootForSurvival GAME ;
    private Texture background;
    private SpriteBatch batch;


    //Buttons
    Button playButton;
    Button levelButton;
    Button controls;
    Button settingsButton;
    Button quitButton;
    TextButton.TextButtonStyle buttonStyle;
    BitmapFont buttonFont;


    public MenuScreen(final shootForSurvival game) {
        this.GAME = game;
        this.manager = shootForSurvival.getManager();
        batch = new SpriteBatch();
        viewport = new FitViewport(shootForSurvival.V_WIDTH, shootForSurvival.V_HEIGHT, new OrthographicCamera());
        stage = new Stage(viewport, GAME.batch);


        //background
        background = manager.get("backgrounds/menubg.png", Texture.class);


        Table table = new Table();
        table.center();
        table.setFillParent(true);


        //Buttons
        buttonStyle = new TextButton.TextButtonStyle();
        buttonFont = new BitmapFont(Gdx.files.internal("skins/quantum-horizon/raw/font-export.fnt"));
        buttonStyle.font = buttonFont;
        buttonStyle.fontColor = CYAN;
        playButton  = new TextButton("Start",buttonStyle);
        playButton.setSize(12,10);
        levelButton  = new TextButton("Level Select",buttonStyle );
        levelButton.setSize(12,10);
        controls  = new TextButton("Controls",buttonStyle);
        controls.setSize(12,10);
        settingsButton  = new TextButton("Settings",buttonStyle );
        settingsButton.setSize(12,10);
        quitButton = new TextButton("Quit",buttonStyle);
        quitButton.setSize(12,10);

        Label.LabelStyle font = new Label.LabelStyle(new BitmapFont(Gdx.files.internal("skins/quantum-horizon/raw/font-title-export.fnt")), MAGENTA);
        Label titleLabel = new Label(" SHOOT FOR ", font);
        Label titleLabel2 = new Label("SURVIVAL", font);

        table.add(titleLabel).expandX().setActorHeight(110);
        table.row();
        table.add(titleLabel2).expandX().setActorHeight(110);
        table.row();
        table.add(playButton).expandX().padTop(10);
        table.row();
        table.add(levelButton).expandX().padTop(5);
        table.row();
        table.add(controls).expandX().padTop(5);
        table.row();
        table.add(settingsButton).expandX().padTop(5);
        table.row();
        table.add(quitButton).expandX().padTop(5);
        table.row();


        stage.addActor(table);
        Gdx.input.setInputProcessor(stage);


        playButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
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
                if(GAME.music.isPlaying())
                    GAME.music.stop();

                GAME.setScreen(new CharacterSelect(GAME , 1, 1));
                dispose();

            }
        });

        levelButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if(Gdx.app.getType() == Application.ApplicationType.Android) {
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

                GAME.setScreen(new LevelSelect(GAME));
            }
        });

        controls.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
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

                GAME.setScreen(new Controls(GAME));
            }
        });

        settingsButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
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

                GAME.setScreen(new Settings(GAME));
            }
        });

        quitButton.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event,float x,float y){
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

                System.gc();
                System.exit(0);
            }
        });


        GAME.loadMusic("audio/music/jantrax - ai.mp3");
        if(GAME.getVolume() != 0) {
            GAME.music.play();
            GAME.music.setVolume(GAME.getVolume());
            }
    }
    @Override
    public void show() {

    }
    @Override
    public void render(float delta){
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        if(Gdx.app.getType() == Application.ApplicationType.Desktop) {
            batch.begin();
            batch.draw(background,0,-20,1950,1250);
            batch.end();
        }
        else if(Gdx.app.getType() == Application.ApplicationType.Android){
            batch.begin();
            batch.draw(background, 0, 0, 2250,1075);
            batch.end();
        }
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
    System.gc();
    }
}