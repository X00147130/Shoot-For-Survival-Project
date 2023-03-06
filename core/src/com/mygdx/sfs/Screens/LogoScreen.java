package com.mygdx.sfs.Screens;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mygdx.sfs.shootForSurvival;

public class LogoScreen implements Screen {
    private final shootForSurvival GAME;
    private Viewport viewport;
    private Texture background;
    private Image backgroundIMG;
    private Stage stage;
    private float timeSeconds = 0f;
    private float period = 5f;

    public LogoScreen(shootForSurvival game) {
        this.GAME = game;
        viewport = new FitViewport(shootForSurvival.V_WIDTH, shootForSurvival.V_HEIGHT, new OrthographicCamera());
        stage = new Stage(viewport,GAME.batch);

        //Display mode for PC Debugging
        if(Gdx.app.getType() == Application.ApplicationType.Desktop)
            Gdx.graphics.setFullscreenMode(Gdx.graphics.getDisplayMode());


        background = new Texture("logos/CONWAY STUDIOS.png");
        backgroundIMG = new Image(background);


        GAME.manager.get("audio/music/yoitrax - Jade Dragon.mp3", Music.class).play();

        Table table = new Table();
        table.setFillParent(true);

        table.add(backgroundIMG);


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
        GAME.setScreen(new MenuScreen (GAME));
        shootForSurvival.manager.get("audio/music/yoitrax - Jade Dragon.mp3",Music.class).stop();
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
