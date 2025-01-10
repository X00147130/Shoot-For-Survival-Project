package com.mygdx.sfs.Scenes.Screens;

import static com.badlogic.gdx.graphics.Color.CYAN;
import static com.badlogic.gdx.graphics.Color.WHITE;

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
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mygdx.sfs.Scenes.Hud;
import com.mygdx.sfs.shootForSurvival;

public class PauseScreen implements Screen {

//Display tools
    private Table table;
    private Stage stage;

//Labels and Buttons
    private Label titleLabel;
    private Label.LabelStyle style;
    private Label.LabelStyle buttonStyle;
    private Label resume;
    private Label quit;

//Admin
    private shootForSurvival game;
    private Screen screen;
    private Viewport viewport;
    private Hud hud;
    private Texture background;



//Constructor
    public PauseScreen(final shootForSurvival gameplay){
//Variable intialisations
        game = gameplay;
        screen = gameplay.getScreen();
        hud = gameplay.getHud();
        viewport = new FitViewport(shootForSurvival.V_WIDTH, shootForSurvival.V_HEIGHT,  new OrthographicCamera());
        stage = new Stage(viewport, game.batch);

//Setting up of the background
        background = game.manager.get("backgrounds/pausebg.png",Texture.class);


//Label Style set up
        style = new Label.LabelStyle(new BitmapFont(Gdx.files.internal("skins/quantum-horizon/raw/font-title-export.fnt")), CYAN);
        buttonStyle = new Label.LabelStyle(new BitmapFont(Gdx.files.internal("skins/CyberpunkCraftpixFont.fnt")), WHITE);

//Title Label Setup
        titleLabel = new Label("PAUSED",style);
        titleLabel.setFontScale(0.8f, 0.6f);

//Button Setups using Labels as Buttons
        resume = new Label("resume", buttonStyle);
        quit = new Label("quit", buttonStyle);
        resume.setFontScale(0.5f, 0.5f);
        quit.setFontScale(0.5f, 0.5f);

//Table Setup
        table = new Table ();
        table.center();
        table.setFillParent(true);

//Filling the Table
        table.add(titleLabel).width(70).height(60).center().padRight(105);
        table.row();
        table.add(resume).width(110).height(50).center().padLeft(10);
        table.row();
        table.add(quit).width(110).height(50).center().padLeft(45);

//Adding Table to the Stage and setting input reading on the stage to pick up any Clicks/Taps
        stage.addActor(table);
        Gdx.input.setInputProcessor(stage);

//Setting up the Resume Button backend
        resume.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y){
//Sets the sound of the volume and retrieves the volume from the settings slider bar that updates a variable in the main page ShootForSurvival
//this sets it for Desktop devices
                if(Gdx.app.getType() == Application.ApplicationType.Desktop) {
                    game.loadSound("audio/sounds/421837__prex2202__blipbutton.mp3");
                    long id = game.sound.play();
                    if (game.getSoundVolume() != 0)
                        game.sound.setVolume(id, game.getSoundVolume());
                    else {
                        game.sound.setVolume(id, 0);
                    }
                }
//Same as the above but this sets it for android devices
                if(Gdx.app.getType() == Application.ApplicationType.Android) {
                    game.manager.get("audio/sounds/421837__prex2202__blipbutton.mp3", Sound.class).play(game.getSoundVolume());
                }

//Set the screen back to game that we read in in the constructor and dispose of this pause screen
                game.setScreen(screen);
                dispose();

            }
        });

//Setting up the Quit Button backend
        quit.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y){
//Sets the sound of the volume and retrieves the volume from the settings slider bar that updates a variable in the main page ShootForSurvival
//this sets it for Desktop devices
                if(Gdx.app.getType() ==Application.ApplicationType.Desktop){
                    game.loadSound("audio/sounds/421837__prex2202__blipbutton.mp3");
                    long id = game.sound.play();
                    if (game.getSoundVolume() != 0)
                        game.sound.setVolume(id, game.getSoundVolume());
                    else {
                        game.sound.setVolume(id, 0);
                    }
                }
//Same as the above but this sets it for android devices
                if(Gdx.app.getType() == Application.ApplicationType.Android) {
                    game.manager.get("audio/sounds/421837__prex2202__blipbutton.mp3", Sound.class).play(game.getSoundVolume());
                }

//Set the screen back to Main Menu dispose of this pause screen
//Set the atlas packs back to stage 1 as the game needs to have them set in case the person chooses play currently as not set up continue button
                game.setHealthAtlas(new TextureAtlas("sprites/Objects/HealthCrate.pack"));
                game.setKeycardAtlas(new TextureAtlas("sprites/Objects/keycard.pack"));
                game.setDoorAtlas(new TextureAtlas("sprites/Objects/industrialDoor.pack"));
                game.setPowerLVL(0);
                game.music.stop();
                game.setScreen(new MenuScreen (game));
                dispose();
            }
        });

    }

    @Override
    public void show() {

    }

//Render Method clears the screen and then uses main classes batch to draw the background to the screen.
    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        game.batch.begin();
        game.batch.draw(background, 0, 0, 400, 300);
        game.batch.end();

        stage.draw();
    }

//Resizes the screen to fit the Device it is currently running on
    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width,height,true);
    }

    @Override
    public void pause() {
    }

    @Override
    public void resume(){
    }

    @Override
    public void hide() {
    }

//Disposes of the stage created in the constructor
    @Override
    public void dispose() {
        stage.dispose();
    }
}
