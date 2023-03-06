package com.mygdx.sfs.Screens;

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
    private shootForSurvival run;
    private Viewport screen;
    private Stage stage;
    private int score = 0;
    private SpriteBatch batch;


    //Next level button variables
    private int map;


    //Buttons
    private Button menuButton;
    private Button nextLevelButton;
    private Button levelSelectButton;
    private TextButton.TextButtonStyle buttonstyle;
    private BitmapFont font;

    Label title;
    Label title2;
    Label Coins;
    private Texture background;

    public LevelComplete(shootForSurvival game, int level){
        super();
        //admin setup
        this.run = game;
        screen = new FitViewport(shootForSurvival.V_WIDTH,shootForSurvival.V_HEIGHT,new OrthographicCamera());
        stage = new Stage(screen,run.batch);
        map = level + 1;
        batch = game.batch;

        score = run.getCoins();

        background = run.manager.get("backgrounds/lvlcompletebg.png",Texture.class);


        //TextButton Style Admin
        buttonstyle = new TextButton.TextButtonStyle();
        font = new BitmapFont(Gdx.files.internal("skins/quantum-horizon/raw/font-export.fnt"));
        buttonstyle.font = font;
        buttonstyle.font.setColor(Color.CYAN);

        //Setting up the TextButtons
        menuButton = new TextButton("Main Menu", buttonstyle);
        nextLevelButton = new TextButton("Next Level", buttonstyle);
        levelSelectButton = new TextButton("Level Select", buttonstyle);

        //Label Admin
        Label.LabelStyle style = new Label.LabelStyle(new BitmapFont(Gdx.files.internal("skins/quantum-horizon/raw/font-title-export.fnt")), Color.MAGENTA);
        Label.LabelStyle style2 = new Label.LabelStyle(new BitmapFont(Gdx.files.internal("skins/quantum-horizon/raw/font-export.fnt")), Color.CYAN);
        title = new Label("Level",style);
        title2 = new Label("Complete",style);
        Coins = new Label(String.format("Score: %4d" ,score),style2);

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

        if(map != 11) {
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

        if(run.music.getVolume() == 0)
            run.setVolume(0);

        //Setting up ClickListners for buttons
      if(map != 10) {
          nextLevelButton.addListener(new ClickListener() {
              @Override
              public void clicked(InputEvent event, float x, float y) {

                  if(Gdx.app.getType() == Application.ApplicationType.Desktop) {
                      run.sound.stop();
                      run.loadSound("audio/sounds/mixkit-gear-metallic-lock-sound-2858.wav");
                      long id = run.sound.play();
                      if (run.getSoundVolume() != 0) {
                          run.sound.setVolume(id, run.getSoundVolume());
                      } else {
                          run.sound.setVolume(id, 0);
                      }
                  }

                  if(Gdx.app.getType() == Application.ApplicationType.Android) {
                      run.manager.get("audio/sounds/Mission Accomplished Fanfare 1.mp3", Sound.class).stop();
                      run.manager.get("audio/sounds/mixkit-gear-metallic-lock-sound-2858.wav", Sound.class).play(run.getSoundVolume());
                  }



                  run.setScreen(new PlayScreen(run, map));
                  run.setCoins(0);
              }
          });
      }

        menuButton.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y){

                if(Gdx.app.getType() == Application.ApplicationType.Desktop) {
                    run.sound.stop();
                    run.loadSound("audio/sounds/mixkit-gear-metallic-lock-sound-2858.wav");
                    long id = run.sound.play();
                    if (run.getSoundVolume() != 0) {
                        run.sound.setVolume(id, run.getSoundVolume());
                    } else {
                        run.sound.setVolume(id, 0);
                    }
                }

                if(Gdx.app.getType() == Application.ApplicationType.Android) {
                    run.manager.get("audio/sounds/Mission Accomplished Fanfare 1.mp3", Sound.class).stop();
                    run.manager.get("audio/sounds/mixkit-gear-metallic-lock-sound-2858.wav", Sound.class).play(run.getSoundVolume());
                }

                run.setScreen(new MenuScreen(run));
            }
        });

        levelSelectButton.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {

                if(Gdx.app.getType() == Application.ApplicationType.Desktop) {
                    run.sound.stop();
                    run.loadSound("audio/sounds/mixkit-gear-metallic-lock-sound-2858.wav");

                    long id = run.sound.play();
                    if (run.getSoundVolume() != 0) {
                        run.sound.setVolume(id, run.getSoundVolume());
                    } else {
                        run.sound.setVolume(id, 0);
                    }
                }
                if(Gdx.app.getType() == Application.ApplicationType.Android) {
                    run.manager.get("audio/sounds/Mission Accomplished Fanfare 1.mp3", Sound.class).stop();
                    run.manager.get("audio/sounds/mixkit-gear-metallic-lock-sound-2858.wav", Sound.class).play(run.getSoundVolume());
                }
                run.setScreen(new LevelSelect(run));
                run.loadMusic("audio/music/yoitrax - Ronin.mp3");
                if (run.getVolume() != 0) {
                    run.music.play();
                    run.setVolume(run.getVolume());
                }
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
