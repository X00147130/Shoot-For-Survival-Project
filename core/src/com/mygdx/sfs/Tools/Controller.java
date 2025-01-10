package com.mygdx.sfs.Tools;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mygdx.sfs.Scenes.Screens.PauseScreen;
import com.mygdx.sfs.shootForSurvival;

public class Controller {
    private Viewport view;
    private boolean upPressed = false, shootPressed = false,leftPressed = false,rightPressed = false,dashPressed = false;
    public Stage stage;
    private OrthographicCamera cam;
    private shootForSurvival gameplay;
    private float timeSeconds = 0f;
    private float period = 0.01f;

    //Image Button Variable
    private ImageButton pause;
    private Texture image;
    private Drawable draw;

    private Image attackImg;
    private Image upImg;
    private Image dashImg;

    public Controller(final shootForSurvival game){
        cam = new OrthographicCamera(480,320);
        cam.position.set(480/2f, 320/2f,0);
        view = new FitViewport(shootForSurvival.V_WIDTH,shootForSurvival.V_HEIGHT,cam);
        stage = new Stage(view);
        Gdx.input.setInputProcessor(stage);
        gameplay = game;

        Texture pic = new Texture("controller/jump.png");
        TextureRegionDrawable picDraw = new TextureRegionDrawable(pic);
        final ImageButton upImg = new ImageButton(picDraw);
        upImg.setSize(40,35);
        if (upImg.isPressed() == Gdx.input.justTouched()){
            upImg.addListener(new InputListener(){

                @Override
                public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                    upPressed = true;
                    gameplay.justTouched++;
                    return true;
                }
                @Override
                public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                    upPressed = false;
                }
            });}

        attackImg = new Image(new Texture("controller/Shoot.png"));
        attackImg.setSize(40, 35);
        attackImg.addListener(new InputListener() {


            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                shootPressed = true;
                return true;
            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                shootPressed = false;
            }
        });


        Image rightImg = new Image(new Texture("controller/Forward.png"));
        rightImg.setSize(40,35);
        rightImg.addListener(new InputListener(){

            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                rightPressed = true;
                return true;
            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                rightPressed = false;
            }
        });

        Image leftImg = new Image(new Texture("controller/Backward.png"));
        leftImg.setSize(40,35);
        leftImg.addListener(new InputListener(){

            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                leftPressed = true;
                return true;
            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                leftPressed = false;
            }
        });

        dashImg = new Image(new Texture("controller/Dash.png"));
        dashImg.setSize(40,35);
        dashImg.addListener(new InputListener(){

            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                dashPressed = true;
                return true;
            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                dashPressed = false;
            }
        });
        //Image button
        image = new Texture("controller/pauseBtn.png");
        draw = new TextureRegionDrawable(image);
        pause = new ImageButton(draw);
        pause.setSize(70,70);
        pause.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event,float x, float y){
                gameplay.setScreen(new PauseScreen(gameplay));
            }

        });

        Table paused = new Table();
        paused.left();
        paused.setFillParent(true);

        Table table = new Table();
        table.left().bottom();
        table.setFillParent(true);

        Table action = new Table();
        action.right().bottom();
        action.setFillParent(true);

        paused.add(pause).maxSize(70,70).left();

        stage.addActor(paused);

        table.row().pad(5,5,5,5);
        table.add(leftImg).size(leftImg.getWidth(),leftImg.getHeight());
        table.add();
        table.add(rightImg).size(rightImg.getWidth(),rightImg.getHeight());
        table.row().padBottom(5);

        stage.addActor(table);

        action.add(upImg).size(upImg.getWidth(), upImg.getHeight()).padRight(10);
        action.add();
        action.add(dashImg).size(dashImg.getWidth(), dashImg.getHeight()).padRight(10).padLeft(10);
        action.add();
        action.add(attackImg).size(attackImg.getWidth(), attackImg.getHeight()).padLeft(10);


        stage.addActor(action);

        stage.addListener(new InputListener() {

            @Override
            public boolean keyDown(InputEvent event, int keycode) {
                switch (keycode) {
                    case Input.Keys.UP:
                        upPressed = true;
                        break;
                    case Input.Keys.SPACE:
                        shootPressed = true;
                        break;
                    case Input.Keys.LEFT:
                        leftPressed = true;
                        break;
                    case Input.Keys.RIGHT:
                        rightPressed = true;
                        break;
                    case Input.Keys.DOWN:
                        dashPressed = true;
                        break;
                }
                return true;
            }

            @Override
            public boolean keyUp(InputEvent event, int keycode) {
                switch (keycode) {
                    case Input.Keys.UP:
                        upPressed = false;
                        break;
                    case Input.Keys.SPACE:
                        shootPressed = false;
                        break;
                    case Input.Keys.LEFT:
                        leftPressed = false;
                    case Input.Keys.RIGHT:
                        rightPressed = false;
                        break;
                    case Input.Keys.DOWN:
                        dashPressed = false;
                        break;
                }
                return true;
            }

        });
    }
    public void draw(){
        if(Gdx.app.getType() == Application.ApplicationType.Android) {
            Gdx.input.setInputProcessor(stage);
            timeSeconds += Gdx.graphics.getRawDeltaTime();
            if (timeSeconds > period) {
                timeSeconds -= period;
                handleEvent();
            }
        }
        stage.draw();

        /*Table action = new Table();
        action.right().bottom();
        action.setFillParent(true);
        if (gameplay.interact) {

            attackImg = new Image(new Texture("controller/Interact.png"));

            action.add(upImg).size(upImg.getWidth(), upImg.getHeight()).padRight(10);
            action.add();
            action.add(dashImg).size(dashImg.getWidth(), dashImg.getHeight()).padRight(10).padLeft(10);
            action.add();
            action.add(attackImg).size(attackImg.getWidth(), attackImg.getHeight()).padLeft(10);
        }

        stage.addActor(action);

        stage.addListener(new InputListener(){

            @Override
            public boolean keyDown(InputEvent event, int keycode){
                switch(keycode){
                    case Input.Keys.UP:
                        upPressed = true;
                        break;
                    case Input.Keys.SPACE:
                        shootPressed = true;
                        break;
                    case Input.Keys.LEFT:
                        leftPressed = true;
                        break;
                    case Input.Keys.RIGHT:
                        rightPressed = true;
                        break;
                    case Input.Keys.DOWN:
                        dashPressed = true;
                        break;
                }
                return true;
            }

            @Override
            public boolean keyUp(InputEvent event, int keycode){
                switch(keycode){
                    case Input.Keys.UP:
                        upPressed = false;
                        break;
                    case Input.Keys.SPACE:
                        shootPressed = false;
                        break;
                    case Input.Keys.LEFT:
                        leftPressed = false;
                    case Input.Keys.RIGHT:
                        rightPressed = false;
                        break;
                    case Input.Keys.DOWN:
                        dashPressed = false;
                        break;
                }
                return true;
            }

        });*/
    }

    public void handleEvent(){
        if(shootPressed == true)
            shootPressed = false;

        if(upPressed == true)
            upPressed = false;

        if(dashPressed == true)
            dashPressed = false;
    }
    public boolean isUpPressed() {
        return upPressed;
    }

    public boolean isDownPressed() {
        return dashPressed;
    }

    public boolean isSpacePressed(){return shootPressed;}

    public boolean isLeftPressed() {
        return leftPressed;
    }

    public boolean isRightPressed() {
        return rightPressed;
    }

    public void resize(int width, int height){
        view.update(width,height);
    }

    public void dispose(){
        stage.dispose();
    }
}