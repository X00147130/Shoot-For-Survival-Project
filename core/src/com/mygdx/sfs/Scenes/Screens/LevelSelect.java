package com.mygdx.sfs.Scenes.Screens;

import static com.badlogic.gdx.graphics.Color.GREEN;
import static com.badlogic.gdx.graphics.Color.MAGENTA;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Sound;
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

public class LevelSelect implements Screen {

    //Game
    private shootForSurvival GAME ;

    //buttons
    Button level1;
    Button level2;
    Button level3;
    Button level4;
    Button level5;
    Button level6;
    Button level7;
    Button level8;
    Button level9;
    Button level10;
    Button backButton;

    TextButton.TextButtonStyle textStyle;
    BitmapFont buttonFont;

    //Background
    private Texture background;

    //admin
    private Viewport viewport;
    private Stage screen;
    private int level = 1;


    public LevelSelect(final shootForSurvival game) {
        super();

        //Admin
        GAME = game;
        viewport = new FitViewport(shootForSurvival.V_WIDTH, shootForSurvival.V_HEIGHT, new OrthographicCamera());
        screen = new Stage(viewport, GAME.batch);

        //Texture
        background = GAME.manager.get("backgrounds/lvlselectbg.png", Texture.class);


        //Button initialisation
        textStyle = new TextButton.TextButtonStyle();
        buttonFont = new BitmapFont(Gdx.files.internal("skins/quantum-horizon/raw/font-export.fnt"));
        textStyle.font = buttonFont;
        textStyle.fontColor = MAGENTA;

        level1 = new TextButton("Level 1", textStyle);
        level2 = new TextButton("Level 2", textStyle);
        level3 = new TextButton("Level 3", textStyle);
        level4 = new TextButton("Level 4", textStyle);
        level5 = new TextButton("Level 5", textStyle);
        level6 = new TextButton("Level 6", textStyle);
        level7 = new TextButton("Level 7", textStyle);
        level8 = new TextButton("Level 8", textStyle);
        level9 = new TextButton("Level 9", textStyle);
        level10 = new TextButton("Level 10", textStyle);
        backButton = new TextButton("Back", textStyle);

        //Label
        Label.LabelStyle font = new Label.LabelStyle(new BitmapFont(Gdx.files.internal("skins/quantum-horizon/raw/font-export.fnt")), GREEN);
        Label pageLabel = new Label("Level Select", font);
        pageLabel.setFontScale(2);

        //Table
        Table grid = new Table();
        grid.top();
        grid.setFillParent(true);

        Table grid2 = new Table();
        grid2.center();
        grid2.setFillParent(true);


        //filling table
        grid.add(pageLabel).center().padLeft(20).padBottom(10).padTop(20);
        grid.row();
        grid2.add(level1).padLeft(120).padTop(50);
        grid2.add(level6).padRight(200).padTop(50);
        grid2.row();
        grid2.add(level2).padLeft(120);
        grid2.add(level7).padRight(200);
        grid2.row();
        grid2.add(level3).padLeft(120);
        grid2.add(level8).padRight(200);
        grid2.row();
        grid2.add(level4).padLeft(120);
        grid2.add(level9).padRight(200);
        grid2.row();
        grid2.add(level5).padLeft(120);
        grid2.add(level10).padRight(200);
        grid2.row();
        grid2.add(backButton).padTop(10).padLeft(320);
        screen.addActor(grid);
        screen.addActor(grid2);
        Gdx.input.setInputProcessor(screen);

        backButton.addListener(new ClickListener() {
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


                GAME.music.stop();
                GAME.setScreen(new MenuScreen(GAME));
            }
        });

        level1.addListener(new ClickListener(){
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


                GAME.music.stop();
                GAME.setScreen(new CharacterSelect(GAME , 1));
            }
        });

        level2.addListener(new ClickListener(){
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


                GAME.music.stop();
                GAME.setScreen(new CharacterSelect(GAME , 2));
            }
        });

        level3.addListener(new ClickListener(){
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


                GAME.music.stop();
                GAME.setScreen(new CharacterSelect(GAME , 3));
            }
        });

        level4.addListener(new ClickListener(){
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
                    GAME.manager.get("audio/sounds421837__prex2202__blipbutton.mp3", Sound.class).play(GAME.getSoundVolume());
                }


                GAME.music.stop();
                GAME.setScreen(new CharacterSelect(GAME , 4));
            }
        });

        level5.addListener(new ClickListener(){
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

                GAME.music.stop();
                GAME.setScreen(new CharacterSelect(GAME , 5));
            }
        });

        level6.addListener(new ClickListener(){
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


                GAME.music.stop();
                GAME.setScreen(new CharacterSelect(GAME , 6));
            }
        });

        level7.addListener(new ClickListener(){
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


                GAME.music.stop();
                GAME.setScreen(new CharacterSelect(GAME , 7));
            }
        });

        level8.addListener(new ClickListener(){
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


                GAME.music.stop();
                GAME.setScreen(new CharacterSelect(GAME , 8));
            }
        });

        level9.addListener(new ClickListener(){
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

                GAME.music.stop();
                GAME.setScreen(new CharacterSelect(GAME , 9));
            }
        });

        level10.addListener(new ClickListener(){
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


                GAME.music.stop();
                GAME.setScreen(new CharacterSelect(GAME , 10));
            }
        });
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        if(Gdx.app.getType() == Application.ApplicationType.Desktop) {
            GAME.batch.begin();
            GAME.batch.draw(background, 0, 0, 400, 275);
            GAME.batch.end();
        }
        if(Gdx.app.getType() == Application.ApplicationType.Android) {
            GAME.batch.begin();
            GAME.batch.draw(background, 0, -0, 400, 300);
            GAME.batch.end();
        }


        screen.draw();
    }

    @Override
    public void resize(int width, int height) {
        screen.getViewport().update(width,height,true);

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
    public void show() {

    }

    @Override
    public void dispose() {
        screen.dispose();
        GAME.dispose();
        background.dispose();
    }


}
