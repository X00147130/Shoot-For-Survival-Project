package com.mygdx.sfs.Scenes.Screens;


import static com.badlogic.gdx.graphics.Color.MAGENTA;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mygdx.sfs.shootForSurvival;

public class Controls implements Screen {

    private final shootForSurvival GAME;
    private Viewport viewport;

    /*Labels*/
    private Label title;
    private Label pause;
    private Label forward;
    private Label backward;
    private Label shoot;
    private Label dash;
    private Label jump;
    private Label back;

    /*labelStyle*/
    private Label.LabelStyle titleStyle;
    private Label.LabelStyle style;

    private Stage stage;
    private Texture background;

    public Controls(shootForSurvival game) {
        this.GAME = game;
        viewport = new FitViewport(shootForSurvival.V_WIDTH, shootForSurvival.V_HEIGHT, new OrthographicCamera());
        stage = new Stage(viewport,GAME.batch);

        /*Label Style*/
        titleStyle = new Label.LabelStyle(new BitmapFont(Gdx.files.internal("skins/quantum-horizon/raw/font-title-export.fnt")), MAGENTA);
        style = new Label.LabelStyle(new BitmapFont(Gdx.files.internal("skins/quantum-horizon/raw/font-export.fnt")), Color.GREEN);

        /*Labels*/
        title = new Label("Controls",titleStyle);

        /*Desktop*/
        if(Gdx.app.getType() == Application.ApplicationType.Desktop) {
            pause = new Label("Pause: esc ", style);
            forward = new Label("Forward: Right Arrow Key", style);
            backward = new Label("Backward: Left Arrow Key", style);
            shoot = new Label("Shoot: Space Bar ", style);
            jump = new Label("Jump: Up Arrow Key", style);
            dash = new Label("Dash: D", style);
            back = new Label("Press space bar to go back", style);
        }
        /*Android*/
        if(Gdx.app.getType() == Application.ApplicationType.Android) {
            pause = new Label("Pause: Pause Button", style);
            forward = new Label("Forward: Right Arrow", style);
            backward = new Label("Backward: Left Arrow", style);
            shoot = new Label("Shoot: S", style);
            jump = new Label("Jump: Jump", style);
            dash = new Label("Dash: D", style);
            back = new Label("Go Back", style);
        }

        //Background Texture
        background = GAME.manager.get("backgrounds/controls.png",Texture.class);

        Table table = new Table();
        table.setFillParent(true);

        table.add(title).center().top().padBottom(20);
        table.row();
        table.row();
        table.add(forward).center();
        table.row();
        table.add(backward).center();
        table.row();
        table.add(jump).center();
        table.row();
        table.add(shoot).center();
        table.row();
        table.add(dash).center();
        table.row();
        table.add(pause).center();
        table.row();
        table.add(back).center().padTop(30);


        stage.addActor(table);
        Gdx.input.setInputProcessor(stage);


        if(Gdx.app.getType() == Application.ApplicationType.Android) {
            back.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    GAME.manager.get("audio/sounds/mixkit-gear-metallic-lock-sound-2858.wav", Sound.class).play(GAME.getSoundVolume());
                    GAME.music.stop();
                    GAME.setScreen(new MenuScreen(GAME));
                }
            });
        }
    }

    @Override
    public void show() {

    }

    public void handleInput(float dt){
        if(Gdx.app.getType() == Application.ApplicationType.Desktop){
            if(Gdx.input.isKeyJustPressed(Input.Keys.SPACE)) {
                GAME.music.stop();
                GAME.setScreen(new MenuScreen(GAME));
            }
        }
    }


    public void update(float dt){
        handleInput(dt);
    }

    @Override
    public void render(float delta) {
      if(Gdx.app.getType() == Application.ApplicationType.Desktop) {
          update(delta);
      }
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        GAME.batch.begin();
        GAME.batch.draw(background,0,0,400,350);
        GAME.batch.end();

        stage.draw();
    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height);
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
        background.dispose();
        stage.dispose();
    }
}


