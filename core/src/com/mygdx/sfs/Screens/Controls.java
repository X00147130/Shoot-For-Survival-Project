package com.mygdx.sfs.Screens;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
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
    private Label attack;
    private Label jump;

    /*labelStyle*/
    private Label.LabelStyle titleStyle;
    private Label.LabelStyle style;

    private Stage stage;
    private float timeSeconds = 0f;
    private float period = 8f;

    public Controls(shootForSurvival game) {
        this.GAME = game;
        viewport = new FitViewport(shootForSurvival.V_WIDTH, shootForSurvival.V_HEIGHT, new OrthographicCamera());
        stage = new Stage(viewport,GAME.batch);

        /*Label Style*/
        titleStyle = new Label.LabelStyle(new BitmapFont(Gdx.files.internal("skins/arcade/raw/font-export.fnt")), Color.RED);
        style = new Label.LabelStyle(new BitmapFont(Gdx.files.internal("skins/arcade/raw/screen-export.fnt")), Color.WHITE);

        /*Labels*/
        title = new Label("Controls",titleStyle);

        /*Desktop*/
            pause = new Label("Pause: esc ", style);
            forward = new Label("Forward: Right Arrow Key", style);
            backward = new Label("Backward: Left Arrow Key", style);
            attack = new Label("Attack: Space Bar ", style);
            jump = new Label("Jump: Up Arrow Key", style);


//        background = new Texture("logos/logo_white_background.jpg");
//        backgroundIMG = new Image(background);

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
        table.add(attack).center();
        table.row();
        table.add(pause).center();

//        table.add(backgroundIMG);


        stage.addActor(table);


    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {

        timeSeconds += Gdx.graphics.getRawDeltaTime();
        if (timeSeconds > period) {
            timeSeconds -= period;
            handleEvent();
        }

        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stage.draw();
    }
    public void handleEvent(){
        GAME.setScreen(new PlayScreen (GAME,1));
        dispose();
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
        stage.dispose();
    }
}


