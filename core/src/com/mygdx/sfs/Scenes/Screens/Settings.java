package com.mygdx.sfs.Scenes.Screens;

import static com.badlogic.gdx.graphics.Color.CYAN;
import static com.badlogic.gdx.graphics.Color.GREEN;
import static com.badlogic.gdx.graphics.Color.MAGENTA;
import static com.badlogic.gdx.graphics.Color.RED;

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
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.CheckBox;
import com.badlogic.gdx.scenes.scene2d.ui.Container;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Slider;
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
    private Label musicLabel;
    private Label soundLabel;
    private Label page;

    private SpriteBatch batch;

    Slider music;
    Slider sound;
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
        title.fontColor = MAGENTA;

        page = new Label("SETTINGS",title);

        musicLabel = new Label("Music Volume",title);
        soundLabel = new Label("Sound Volume", title);


        //skin setup
        skin = new Skin(Gdx.files.internal("skins/quantum-horizon/skin/quantum-horizon-ui.json"));

        music = new Slider(0f,1f,0.01f,false,skin);
        music.setValue(GAME.getVolume());

        sound = new Slider(0f,1f,0.01f,false,skin);
        sound.setValue(GAME.getSoundVolume());

        music.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                if(!music.isDragging()) {
                    GAME.setVolume(music.getValue());
                    GAME.music.setVolume(GAME.getVolume());

                    if(!GAME.music.isPlaying()){
                        GAME.music.play();
                    }
                }
            }
        });

        sound.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                if(!sound.isDragging()){
                    GAME.setSoundVolume(sound.getValue());
                    if(Gdx.app.getType() == Application.ApplicationType.Desktop) {
                        GAME.loadSound("audio/sounds/gun pickup.mp3");
                        long id = GAME.sound.play();
                        if (GAME.getSoundVolume() != 0)
                            GAME.sound.setVolume(id, GAME.getSoundVolume());
                        else {
                            GAME.sound.setVolume(id, 0);
                        }
                    }
                    if(Gdx.app.getType() == Application.ApplicationType.Android) {
                        GAME.manager.get("audio/sounds/gun pickup.mp3", Sound.class).play(GAME.getSoundVolume());
                    }
                }
            }
        });

        Container<Slider> container = new Container<Slider>(music);
        container.setTransform(true); // enables scaling and rotation
        container.setSize(350,100);
        container.setOrigin(container.getWidth() / 2 , container.getHeight() / 2);
        container.setScale(1);

        Container<Slider> container1 = new Container<Slider>(sound);
        container1.setTransform(true); // enables scaling and rotation
        container1.setSize(350,100);
        container1.setOrigin(container.getWidth() / 2 , container.getHeight() / 2);
        container1.setScale(1);

        backButton = new TextButton("BACK",textStyle);
        Table table = new Table();
        table.setFillParent(true);
        table.center();



        table.row();
        table.add(page).expandX().padBottom(25).center().padLeft(230);
        table.row();
        table.add(musicLabel).expandX().padRight(35).padLeft(110);
        table.add(music).left().padLeft(15);
        table.add(container).center().expandX().padBottom(70).padLeft(105);
        table.row();
        table.row();
        table.add(soundLabel).expandX().padRight(35).padLeft(110);
        table.add(sound).center().padLeft(15);
        table.add(container1).center();
        table.row();
        table.row();
        table.add(backButton).expandX().padTop(10).center().padLeft(230);
        table.row();



        backButton.addListener(new ClickListener(){
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
        buttonFont.dispose();
        batch.dispose();
        GAME.dispose();
        GAME.batch.dispose();
    }
}
