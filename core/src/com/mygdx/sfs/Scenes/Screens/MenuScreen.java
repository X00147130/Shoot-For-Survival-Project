package com.mygdx.sfs.Scenes.Screens;

import static com.badlogic.gdx.graphics.Color.CYAN;
import static com.badlogic.gdx.graphics.Color.MAGENTA;
import static com.badlogic.gdx.graphics.Color.WHITE;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Sound;
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
    private Label play;
    private Label levelSelect;
    private Label controls;
    private Label settings;
    private Label quit;


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


        Label.LabelStyle font = new Label.LabelStyle(new BitmapFont(Gdx.files.internal("skins/CyberpunkCraftpixFont.fnt")), MAGENTA);
        Label.LabelStyle buttonFont = new Label.LabelStyle(new BitmapFont(Gdx.files.internal("skins/CyberpunkCraftpixFont.fnt")), CYAN);
        Label titleLabel = new Label(" SHOOT FOR ", font);
        titleLabel.setFontScale(1.2f, 0.8f);
        Label titleLabel2 = new Label("SURVIVAL", font);
        titleLabel2.setFontScale(1.2f, 0.8f);

        play = new Label("Play", buttonFont);
        play.setFontScale(0.5f, 0.5f);

        levelSelect = new Label("Level Select", buttonFont);
        levelSelect.setFontScale(0.5f, 0.5f);

        controls = new Label("Controls", buttonFont);
        controls.setFontScale(0.5f, 0.5f);

        settings = new Label("Settings", buttonFont);
        settings.setFontScale(0.5f, 0.5f);

        quit = new Label("Exit", buttonFont);
        quit.setFontScale(0.5f, 0.5f);


        table.add(titleLabel).expandX();
        table.row();
        table.add(titleLabel2).expandX();
        table.row();
        table.add(play).expandX().padTop(10);
        table.row();
        table.add(levelSelect).expandX().padTop(5);
        table.row();
        table.add(controls).expandX().padTop(5);
        table.row();
        table.add(settings).expandX().padTop(5);
        table.row();
        table.add(quit).expandX().padTop(5);
        table.row();


        stage.addActor(table);
        Gdx.input.setInputProcessor(stage);


        play.addListener(new ClickListener() {
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

        levelSelect.addListener(new ClickListener() {
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

        settings.addListener(new ClickListener() {
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

        quit.addListener(new ClickListener(){
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