package com.mygdx.sfs.Screens;

import static com.badlogic.gdx.graphics.Color.GOLD;
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
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
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
    private Label page;
    private Label musicLabel;
    private Label soundLabel;

    private ShapeRenderer border;
    private ShapeRenderer border2;
    private ShapeRenderer border3;
    private ShapeRenderer border4;
    private ShapeRenderer border5;
    private ShapeRenderer border6;

    private SpriteBatch batch;

    Slider music;
    Slider sound;
    Skin skin;

    Stage stage;
    TextButton.TextButtonStyle textStyle;
    BitmapFont buttonFont;

    private Texture background;
    float startX;

    public Settings(final shootForSurvival game){
        this.GAME = game;
        viewport = new FitViewport(shootForSurvival.V_WIDTH, shootForSurvival.V_HEIGHT, new OrthographicCamera());
        stage = new Stage(viewport, game.batch);
        batch = new SpriteBatch();
        background = GAME.manager.get("wave-scaled.jpg", Texture.class);

        border = new ShapeRenderer();
        border2 = new ShapeRenderer();
        border3 = new ShapeRenderer();
        border4 = new ShapeRenderer();
        border5 = new ShapeRenderer();
        border6 = new ShapeRenderer();

        textStyle = new TextButton.TextButtonStyle();
        buttonFont = new BitmapFont(Gdx.files.internal("skins/comic/comic-ui_data/font-export.fnt"));
        textStyle.font = buttonFont;
        textStyle.fontColor = RED;

        Label.LabelStyle label = new Label.LabelStyle();
        label.font = buttonFont;
        label.fontColor = RED;

        Label.LabelStyle title = new Label.LabelStyle();
        title.font = buttonFont;
        title.fontColor = GOLD;

        page = new Label("SETTINGS",title);
        musicLabel = new Label("Music Volume",label);
        soundLabel = new Label("Sound Volume", label);

        //skin setup
        skin = new Skin(Gdx.files.internal("skins/comic/comic-ui.json"));


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
                        GAME.loadSound("audio/sounds/coin.mp3");
                        long id = GAME.sound.play();
                        if (GAME.getSoundVolume() != 0)
                            GAME.sound.setVolume(id, GAME.getSoundVolume());
                        else {
                            GAME.sound.setVolume(id, 0);
                        }
                    }
                    if(Gdx.app.getType() == Application.ApplicationType.Android) {
                        GAME.manager.get("audio/sounds/coin.mp3", Sound.class).play(GAME.getSoundVolume());
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
        table.add(page).expandX().padBottom(25).padRight(35);
        table.row();
        table.add(musicLabel).expandX().padRight(35).padLeft(10);
        table.add(music).left().padLeft(15);
        table.add(container).center().expandX().padBottom(70).padLeft(105);
        table.row();
        table.row();
        table.add(soundLabel).expandX().padRight(35).padLeft(10);
        table.add(sound).left().padLeft(15);
        table.add(container1).center().expandX().padLeft(105);
        table.row();
        table.row();
        table.add(backButton).expandX().padTop(10).padRight(35);
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

        batch.begin();
        batch.draw(background,0,0,2080,1080);
        batch.end();

       /*Desktop Edition*/
        if(Gdx.app.getType() == Application.ApplicationType.Desktop) {

            border.begin(ShapeRenderer.ShapeType.Filled);
            border.setColor(Color.RED);
            border.rect(635, 480, 870, 140);
            border.end();

            border2.begin(ShapeRenderer.ShapeType.Filled);
            border2.setColor(Color.RED);
            border2.rect(635, 230, 870, 140);
            border2.end();

            border3.begin(ShapeRenderer.ShapeType.Filled);
            border3.setColor(Color.WHITE);
            border3.rect(45, 510, 450, 70);
            border3.end();

            border4.begin(ShapeRenderer.ShapeType.Filled);
            border4.setColor(Color.WHITE);
            border4.rect(45, 260, 450, 70);
            border4.end();

            border5.begin(ShapeRenderer.ShapeType.Filled);
            border5.setColor(Color.WHITE);
            border5.rect(55, 835, 375, 70);
            border5.end();

            border6.begin(ShapeRenderer.ShapeType.Filled);
            border6.setColor(Color.WHITE);
            border6.rect(150, 110, 200, 70);
            border6.end();
        }

        /*Phone Edition*/
        else if(Gdx.app.getType() == Application.ApplicationType.Android){
            border.begin(ShapeRenderer.ShapeType.Filled);
            border.setColor(Color.RED);
            border.rect(725, 515,1000,140);
            border.end();

            border2.begin(ShapeRenderer.ShapeType.Filled);
            border2.setColor(Color.RED);
            border2.rect(725, 245,1000,140);
            border2.end();

            border3.begin(ShapeRenderer.ShapeType.Filled);
            border3.setColor(Color.WHITE);
            border3.rect(45, 550, 510, 70);
            border3.end();

            border4.begin(ShapeRenderer.ShapeType.Filled);
            border4.setColor(Color.WHITE);
            border4.rect(45, 285, 510, 70);
            border4.end();

            border5.begin(ShapeRenderer.ShapeType.Filled);
            border5.setColor(Color.WHITE);
            border5.rect(75, 885, 400, 70);
            border5.end();

            border6.begin(ShapeRenderer.ShapeType.Filled);
            border6.setColor(Color.WHITE);
            border6.rect(150, 110, 250, 70);
            border6.end();
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
        GAME.dispose();
    }
}
