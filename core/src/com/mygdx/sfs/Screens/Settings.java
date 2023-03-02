package com.mygdx.sfs.Screens;

import static com.badlogic.gdx.graphics.Color.MAGENTA;
import static com.badlogic.gdx.graphics.Color.RED;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.CheckBox;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mygdx.sfs.shootForSurvival;

public class Settings implements Screen {
    private final shootForSurvival GAME;
    private Viewport viewport;

    private Button backButton;
    private Label page;

    private SpriteBatch batch;

    CheckBox music;
    CheckBox sound;
    Skin skin;

    Stage stage;
    TextButton.TextButtonStyle textStyle;
    BitmapFont buttonFont;

    private Texture background;

    public Settings(final shootForSurvival game){
        this.GAME = game;
        viewport = new FitViewport(shootForSurvival.V_WIDTH, shootForSurvival.V_HEIGHT, new OrthographicCamera());
        stage = new Stage(viewport, game.batch);
        batch = new SpriteBatch();
        background = GAME.manager.get("backgrounds/settingsbg.png",Texture.class);


        textStyle = new TextButton.TextButtonStyle();
        buttonFont = new BitmapFont(Gdx.files.internal("skins/quantum-horizon/raw/font-export.fnt"));
        textStyle.font = buttonFont;
        textStyle.fontColor = MAGENTA;

        Label.LabelStyle title = new Label.LabelStyle();
        title.font = buttonFont;
        title.fontColor = RED;

        page = new Label("SETTINGS",title);

        //skin setup
        skin = new Skin(Gdx.files.internal("skins/star-soldier/skin/star-soldier-ui.json"));
        music = new CheckBox(" : Mute Music", skin);
        music.setColor(RED);
        if (GAME.musicIsChecked == true)
            music.setChecked(true);

        sound = new CheckBox(" : Mute Sound", skin);
        sound.setColor(RED);
        if (GAME.soundIsChecked == true)
            sound.setChecked(true);

        music.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                if(!music.isChecked()) {
                    GAME.music.setVolume(50);
                    GAME.setVolume(GAME.music.getVolume());
                    GAME.setMusicIsChecked(false);
                    GAME.music.play();
                }else{
                    GAME.music.setVolume(0);
                    GAME.setVolume(GAME.music.getVolume());
                    GAME.setMusicIsChecked(true);
                }
            }
        });

        sound.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                GAME.loadSound("audio/sounds/mixkit-gear-metallic-lock-sound-2858.wav");
                long id = GAME.sound.play();
                if (!sound.isChecked()) {
                    GAME.sound.setVolume(id, 50);
                    GAME.setSoundVolume(50);
                    GAME.setSoundIsChecked(false);
                }else {
                    GAME.sound.setVolume(id, 0);
                    GAME.setSoundVolume(0);
                    GAME.setSoundIsChecked(true);
                }
            }
        });

        backButton = new TextButton("BACK",textStyle);
        Table table = new Table();
        table.setFillParent(true);
        table.center();

        table.row();
        table.add(page).expandX().padBottom(25).center();
        table.row();
        table.add(music).center().padLeft(15).padBottom(5);
        table.row();
        table.add(sound).center().padLeft(18);
        table.row();
        table.row();
        table.add(backButton).expandX().padTop(10).center();
        table.row();



        backButton.addListener(new ClickListener(){
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

        stage.addActor(table);
        Gdx.input.setInputProcessor(stage);



    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0,0,0,1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        GAME.batch.begin();
        GAME.batch.draw(background,0,-10,400,350);
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
        GAME.batch.dispose();
    }
}
