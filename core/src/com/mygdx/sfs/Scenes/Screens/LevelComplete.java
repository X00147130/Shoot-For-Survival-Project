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
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
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

public class LevelComplete implements Screen {

    //Admin
    private shootForSurvival sfs;
    private Viewport screen;
    private Stage stage;
    private SpriteBatch batch;
    private int score = 0;


    //Next level button variables
    private int area;
    private int map;


    //Buttons
    Label menuButton;
    Label nextLevelButton;
    Label levelSelectButton;

    Label title;
    Label title2;
    Label Coins;
    private Texture background;

    public LevelComplete(shootForSurvival game, int location, int level){
        super();
        //admin setup
        this.sfs = game;
        screen = new FitViewport(shootForSurvival.V_WIDTH,shootForSurvival.V_HEIGHT,new OrthographicCamera());
        stage = new Stage(screen, sfs.batch);
        area = location;
        if(area == 1 && map == 11)
            area = 2;

        map = level + 1;
        batch = game.batch;

        score = sfs.getMoney();

        background = sfs.manager.get("backgrounds/lvlcompletebg.png",Texture.class);

        //Label Admin
        Label.LabelStyle style = new Label.LabelStyle(new BitmapFont(Gdx.files.internal("skins/CyberpunkCraftpixFont.fnt")), Color.MAGENTA);
        Label.LabelStyle style2 = new Label.LabelStyle(new BitmapFont(Gdx.files.internal("skins/CyberpunkCraftpixFont.fnt")), Color.CYAN);
        title = new Label("Level",style);
        title.setFontScale(0.9f,0.8f);
        title2 = new Label("Complete",style);
        title2.setFontScale(0.9f,0.8f);
        Coins = new Label(String.format("Score: %4d" ,score),style2);
        Coins.setFontScale(0.5f,0.5f);

        //Setting up the TextButtons
        menuButton = new Label("Main Menu", style2);
        menuButton.setFontScale(0.5f,0.5f);
        nextLevelButton = new Label("Next Level", style2);
        nextLevelButton.setFontScale(0.5f,0.5f);
        levelSelectButton = new Label("Level Select", style2);
        levelSelectButton.setFontScale(0.5f,0.5f);

        //Table Setup
        Table table = new Table();
        table.center();
        table.setFillParent(true);


        table.add(title).expandX().top();
        table.row();
        table.add(title2).expandX().padBottom(10);
        table.row();
        table.add(Coins).center().expandX();
        table.row();

        if(map != 10) {
            table.add(nextLevelButton).expandX().padTop(20).padBottom(5);
            table.row();
        }
        table.add(levelSelectButton).expandX().center().padBottom(5);
        table.row();
        table.add(menuButton).expandX().center().padBottom(5);
        table.row();

        //Setting up the stage
        stage.addActor(table);
        Gdx.input.setInputProcessor(stage);

        if(sfs.music.getVolume() == 0)
            sfs.setVolume(0);

        //Setting up ClickListners for buttons
      if(map != 10) {
          nextLevelButton.addListener(new ClickListener() {
              @Override
              public void clicked(InputEvent event, float x, float y) {

                  if(Gdx.app.getType() == Application.ApplicationType.Desktop) {
                      sfs.sound.stop();
                      sfs.loadSound("audio/sounds/421837__prex2202__blipbutton.mp3");
                      long id = sfs.sound.play();
                      if (sfs.getSoundVolume() != 0) {
                          sfs.sound.setVolume(id, sfs.getSoundVolume());
                      } else {
                          sfs.sound.setVolume(id, 0);
                      }
                  }

                  if(Gdx.app.getType() == Application.ApplicationType.Android) {
                      sfs.manager.get("audio/sounds/672801__silverillusionist__level-upmission-complete-resistance.wav", Sound.class).stop();
                      sfs.manager.get("audio/sounds/421837__prex2202__blipbutton.mp3", Sound.class).play(sfs.getSoundVolume());
                  }



                  sfs.setScreen(new Upgrade(sfs, area, map));
                  /*sfs.setMoney(0);*/
              }
          });
      }

        menuButton.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y){

                if(Gdx.app.getType() == Application.ApplicationType.Desktop) {
                    sfs.sound.stop();
                    sfs.loadSound("audio/sounds/421837__prex2202__blipbutton.mp3");
                    long id = sfs.sound.play();
                    if (sfs.getSoundVolume() != 0) {
                        sfs.sound.setVolume(id, sfs.getSoundVolume());
                    } else {
                        sfs.sound.setVolume(id, 0);
                    }
                }

                if(Gdx.app.getType() == Application.ApplicationType.Android) {
                    sfs.manager.get("audio/sounds/672801__silverillusionist__level-upmission-complete-resistance.wav", Sound.class).stop();
                    sfs.manager.get("audio/sounds/421837__prex2202__blipbutton.mp3", Sound.class).play(sfs.getSoundVolume());
                }

                sfs.setHealthAtlas(new TextureAtlas("sprites/Objects/HealthCrate.pack"));
                sfs.setKeycardAtlas(new TextureAtlas("sprites/Objects/keycard.pack"));
                sfs.setDoorAtlas(new TextureAtlas("sprites/Objects/industrialDoor.pack"));
                sfs.setScreen(new MenuScreen(sfs));
            }
        });

        levelSelectButton.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {

                if(Gdx.app.getType() == Application.ApplicationType.Desktop) {
                    sfs.sound.stop();
                    sfs.loadSound("audio/sounds/421837__prex2202__blipbutton.mp3");

                    long id = sfs.sound.play();
                    if (sfs.getSoundVolume() != 0) {
                        sfs.sound.setVolume(id, sfs.getSoundVolume());
                    } else {
                        sfs.sound.setVolume(id, 0);
                    }
                }
                if(Gdx.app.getType() == Application.ApplicationType.Android) {
                    sfs.manager.get("audio/sounds/672801__silverillusionist__level-upmission-complete-resistance.wav", Sound.class).stop();
                    sfs.manager.get("audio/sounds/421837__prex2202__blipbutton.mp3", Sound.class).play(sfs.getSoundVolume());
                }
                sfs.setScreen(new LevelSelect(sfs));
                sfs.loadMusic("audio/music/jantrax - ai.mp3");
                if (sfs.getVolume() != 0) {
                    sfs.music.play();
                    sfs.setVolume(sfs.getVolume());
                }
                sfs.setHealthAtlas(new TextureAtlas("sprites/Objects/HealthCrate.pack"));
                sfs.setKeycardAtlas(new TextureAtlas("sprites/Objects/keycard.pack"));
                sfs.setDoorAtlas(new TextureAtlas("sprites/Objects/industrialDoor.pack"));
            }
        });

    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0,0,0,1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        batch.begin();
        batch.draw(background,0,0,400,400);
        batch.end();

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
    }
}
