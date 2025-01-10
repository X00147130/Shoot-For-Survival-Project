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
/*Game Variables*/
    private final shootForSurvival GAME;
    private Viewport viewport;

/*Labels*/
    private Label title;
    private Label pause;
    private Label forward;
    private Label backward;
    private Label shoot;
    private Label interact;
    private Label dash;
    private Label jump;
    private Label back;

    private Texture pauseImg;
    private Texture forwardImg;
    private Texture backwardImg;
    private Texture shootImg;
    private Texture interactImg;
    private Texture jumpImg;
    private Texture dashImg;

/*labelStyle*/
    private Label.LabelStyle titleStyle;
    private Label.LabelStyle style;

/*Background Settings*/
    private Stage stage;
    private Texture background;

    public Controls(shootForSurvival game) {
        this.GAME = game;
        viewport = new FitViewport(shootForSurvival.V_WIDTH, shootForSurvival.V_HEIGHT, new OrthographicCamera());
        stage = new Stage(viewport,GAME.batch);

 /*Label Style*/
        titleStyle = new Label.LabelStyle(new BitmapFont(Gdx.files.internal("skins/quantum-horizon/raw/font-title-export.fnt")), MAGENTA);
        style = new Label.LabelStyle(new BitmapFont(Gdx.files.internal("skins/CyberpunkCraftpixFont.fnt")), Color.GREEN);

/*Labels*/
        title = new Label("Controls",titleStyle);

/*Desktop*/
        if(Gdx.app.getType() == Application.ApplicationType.Desktop) {
            pause = new Label("Pause:   esc ", style);
            forward = new Label("Forward:   Right Arrow Key", style);
            backward = new Label("Backward:   Left Arrow Key", style);
            shoot = new Label("Shoot:   Space Bar ", style);
            jump = new Label("Jump:   Up Arrow Key", style);
            dash = new Label("Dash:   D", style);
            back = new Label("Press space bar to go back", style);

            pause.setFontScale(0.3f, 0.3f);
            forward.setFontScale(0.3f, 0.3f);
            backward.setFontScale(0.3f, 0.3f);
            shoot.setFontScale(0.3f, 0.3f);
            jump.setFontScale(0.3f, 0.3f);
            dash.setFontScale(0.3f, 0.3f);
            back.setFontScale(0.3f, 0.3f);
        }

/*Android*/
        if(Gdx.app.getType() == Application.ApplicationType.Android) {
            pause = new Label("Pause:   " , style);
            forward = new Label("Forward:   ", style);
            backward = new Label("Backward:   ", style);
            shoot = new Label("Shoot:   ", style);
            interact = new Label("Interact:   ", style);
            jump = new Label("Jump:   ", style);
            dash = new Label("Dash:   ", style);
            back = new Label("Go Back", style);

            pauseImg = new Texture("controller/pauseBtn.png");
            forwardImg = new Texture("controller/Forward.png");
            backwardImg = new Texture("controller/Backward.png");
            shootImg = new Texture("controller/Shoot.png");
            interactImg = new Texture("controller/Interact.png");
            jumpImg = new Texture("controller/jump.png");
            dashImg = new Texture("controller/Dash.png");

            pause.setFontScale(0.3f, 0.3f);
            forward.setFontScale(0.3f, 0.3f);
            backward.setFontScale(0.3f, 0.3f);
            shoot.setFontScale(0.3f, 0.3f);
            interact.setFontScale(0.3f, 0.3f);
            jump.setFontScale(0.3f, 0.3f);
            dash.setFontScale(0.3f, 0.3f);
            back.setFontScale(0.3f, 0.3f);
        }

/*Background Texture*/
        background = GAME.manager.get("backgrounds/controls.png",Texture.class);


/*Table Creation*/
        Table table = new Table();
        table.setFillParent(true);

/*Populating Table*/
        table.add(title).center().top().padBottom(10);
        table.row();
        table.add(forward).center().padBottom(10);
        table.row();
        table.add(backward).center().padBottom(10);
        table.row();
        table.add(jump).center().padBottom(10);
        table.row();
        table.add(shoot).center().padBottom(10);
        table.row();
        table.add(interact).center().padBottom(10);
        table.row();
        table.add(dash).center().padBottom(10);
        table.row();
        table.add(pause).center();
        table.row();
        table.add(back).center().padTop(15);

/*Passing Table into the Stage and Stage then into the input processor*/
        stage.addActor(table);
        Gdx.input.setInputProcessor(stage);

/*Android back button Click listener*/
        if(Gdx.app.getType() == Application.ApplicationType.Android) {
            back.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    GAME.manager.get("audio/sounds/421837__prex2202__blipbutton.mp3", Sound.class).play(GAME.getSoundVolume());
                    GAME.music.stop();
                    GAME.setScreen(new MenuScreen(GAME));
                }
            });
        }
    }

    @Override
    public void show() {

    }
/*Back button set up for Desktop*/
    public void handleInput(float dt){
        if(Gdx.app.getType() == Application.ApplicationType.Desktop){
            if(Gdx.input.isKeyJustPressed(Input.Keys.SPACE)) {
                GAME.music.stop();
                GAME.setScreen(new MenuScreen(GAME));
            }
        }
    }

/*Update that checks for inputs */
    public void update(float dt){
        handleInput(dt);
    }

    /*Clearing the screen and then displaying the background and drawing the stage on top of the background*/
    @Override
    public void render(float delta) {
      if(Gdx.app.getType() == Application.ApplicationType.Desktop) {
          update(delta);
      }
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        GAME.batch.begin();
        GAME.batch.draw(background,0,0,400,350);
        if(Gdx.app.getType() == Application.ApplicationType.Android) {
            GAME.batch.draw(forwardImg,260,140,20,20);
            GAME.batch.draw(backwardImg,260,120,20,20);
            GAME.batch.draw(jumpImg,260,100,20,20);
            GAME.batch.draw(shootImg,260,80,20,20);
            GAME.batch.draw(interactImg,260,60,20,20);
            GAME.batch.draw(dashImg,260,40,20,20);
            GAME.batch.draw(pauseImg,260,20,20,20);
        }

        GAME.batch.end();

        stage.draw();
    }

/*Resizes the viewport of the app to fit the device your on*/
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

/*Takes out the trash*/
    @Override
    public void dispose() {
        background.dispose();
        stage.dispose();
    }
}


