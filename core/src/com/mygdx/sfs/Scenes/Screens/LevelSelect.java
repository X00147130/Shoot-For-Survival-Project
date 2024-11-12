package com.mygdx.sfs.Scenes.Screens;

import static com.badlogic.gdx.graphics.Color.CYAN;
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
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mygdx.sfs.shootForSurvival;

public class LevelSelect implements Screen {

    //Game
    private shootForSurvival GAME ;
    private int area;


    //buttons
    Label level1;
    Label level2;
    Label level3;
    Label level4;
    Label level5;
    Label level6;
    Label level7;
    Label level8;
    Label level9;
    Label level10;

    Label backButton;

    Texture prevImage;
    Drawable prevDraw;
    Button previousButton;

    Texture image;
    Drawable draw;
    Button nextButton;

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
        area = 1;

        //Texture
        background = GAME.manager.get("backgrounds/Background.png", Texture.class);


        prevImage = new Texture("controller/Backward.png");
        prevDraw = new TextureRegionDrawable(prevImage);
        previousButton = new ImageButton(prevDraw);
        previousButton.setSize(40,35);
        image = new Texture("controller/Forward.png");
        draw = new TextureRegionDrawable(image);
        nextButton = new ImageButton(draw);
        nextButton.setSize(40,35);

        //Label
        Label.LabelStyle font = new Label.LabelStyle(new BitmapFont(Gdx.files.internal("skins/quantum-horizon/raw/font-title-export.fnt")), GREEN);
        final Label.LabelStyle buttonFont = new Label.LabelStyle(new BitmapFont(Gdx.files.internal("skins/CyberpunkCraftpixFont.fnt")), MAGENTA);
        Label pageLabel = new Label("Level Select", font);
        pageLabel.setFontScale(0.7f, 0.6f);

        //Button Init
        level1 = new Label("Level 1", buttonFont);
        level2 = new Label("Level 2", buttonFont);
        level3 = new Label("Level 3", buttonFont);
        level4 = new Label("Level 4", buttonFont);
        level5 = new Label("Level 5", buttonFont);
        level6 = new Label("Level 6", buttonFont);
        level7 = new Label("Level 7", buttonFont);
        level8 = new Label("Level 8", buttonFont);
        level9 = new Label("Level 9", buttonFont);
        level10 = new Label("Level 10", buttonFont);

        level1.setFontScale(0.5f,0.5f);
        level2.setFontScale(0.5f,0.5f);
        level3.setFontScale(0.5f,0.5f);
        level4.setFontScale(0.5f,0.5f);
        level5.setFontScale(0.5f,0.5f);
        level6.setFontScale(0.5f,0.5f);
        level7.setFontScale(0.5f,0.5f);
        level8.setFontScale(0.5f,0.5f);
        level9.setFontScale(0.5f,0.5f);
        level10.setFontScale(0.5f,0.5f);


        backButton = new Label("Back", buttonFont);
        backButton.setFontScale(0.5f,0.5f);

        //Table
        final Table grid = new Table();
        grid.top();
        grid.setFillParent(true);

        //Tileset 1
        final Table grid2 = new Table();
        grid2.center();
        grid2.setFillParent(true);


        //filling table for Tileset 1
        grid.add(pageLabel).center().padLeft(20).padBottom(10).padTop(20);
        grid.row();
        grid2.add(level1).padTop(50);
        grid2.add(level6).padRight(90).padTop(50);
        grid2.row();
        grid2.add(level2);
        grid2.add(level7).padRight(90);
        grid2.row();
        grid2.add(level3);
        grid2.add(level8).padRight(90);
        grid2.row();
        grid2.add(previousButton).maxSize(40,35);
        grid2.add(nextButton).padRight(90).maxSize(40,35);
        grid2.row();
        grid2.add(level4);
        grid2.add(level9).padRight(90);
        grid2.row();
        grid2.add(level5);
        grid2.add(level10).padRight(90);
        grid2.row();
        grid2.add(backButton).padTop(10).padLeft(200);

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

        nextButton.addListener(new ClickListener(){
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
                background = GAME.manager.get("backgrounds/ResidentialBackround.png", Texture.class);
                buttonFont.fontColor = CYAN;
                area++;
                game.setHealthAtlas(new TextureAtlas("sprites/Objects/Residential/ResidentialHealthCrate.pack"));
                game.setKeycardAtlas(new TextureAtlas("sprites/Objects/Residential/ResidentialKeyCard.pack"));
                game.setDoorAtlas(new TextureAtlas("sprites/Objects/Residential/residentialDoor.pack"));


            }
        });

        previousButton.addListener(new ClickListener(){
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
                background = GAME.manager.get("backgrounds/Background.png", Texture.class);
                buttonFont.fontColor = MAGENTA;
                area--;
                game.setHealthAtlas(new TextureAtlas("sprites/Objects/HealthCrate.pack"));
                game.setKeycardAtlas(new TextureAtlas("sprites/Objects/keycard.pack"));
                game.setDoorAtlas(new TextureAtlas("sprites/Objects/industrialDoor.pack"));

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
                GAME.setScreen(new CharacterSelect(GAME , area,1));
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
                GAME.setScreen(new CharacterSelect(GAME ,area, 2));
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
                GAME.setScreen(new CharacterSelect(GAME ,area, 3));
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
                    GAME.manager.get("audio/sounds/421837__prex2202__blipbutton.mp3", Sound.class).play(GAME.getSoundVolume());
                }


                GAME.music.stop();
                GAME.setScreen(new CharacterSelect(GAME , area,4));
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
                GAME.setScreen(new CharacterSelect(GAME ,area, 5));
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
                GAME.setScreen(new CharacterSelect(GAME ,area, 6));
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
                GAME.setScreen(new CharacterSelect(GAME , area,7));
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
                GAME.setScreen(new CharacterSelect(GAME ,area, 8));
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
                GAME.setScreen(new CharacterSelect(GAME ,area, 9));
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
                GAME.setScreen(new CharacterSelect(GAME ,area, 10));
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
