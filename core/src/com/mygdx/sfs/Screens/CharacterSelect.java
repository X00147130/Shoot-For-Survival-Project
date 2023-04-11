package com.mygdx.sfs.Screens;

import static com.badlogic.gdx.graphics.Color.MAGENTA;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mygdx.sfs.shootForSurvival;

import java.util.ArrayList;


public class CharacterSelect implements Screen {
    /* Admin Inits */
    private shootForSurvival sfs;
    private Viewport viewport;
    private Animation preview;

    /* Arrays To Loop through for Selection */
    private ArrayList<TextureRegion> characterSprites;
    private ArrayList<String> characterNames;
    private int i = 0;

    //Selection and buttons
    private TextureAtlas selected;
    private Image left, right;
    private TextButton choose;
    private Table table;
    private Stage stage;



    public CharacterSelect(shootForSurvival game){
        this.sfs = game;
        viewport = new FitViewport(sfs.V_WIDTH,sfs.V_HEIGHT, new OrthographicCamera());


        /*initialising and instantiating of animatimation arrays*/
        characterSprites = new ArrayList<TextureRegion>(3);

        characterSprites.add(sfs.getBikerAtlas().findRegion("Idle1"));

        characterSprites.add(sfs.getPunkAtlas().findRegion("Idle1"));
        characterSprites.add(sfs.getPunkAtlas().findRegion("Idle1"));

        characterSprites.add(sfs.getCyborgAtlas().findRegion("Idle1"));

        /*Intiatialising and Names of characters added to the arraylist*/
        characterNames = new ArrayList<String>(3);

        characterNames.add("Chad");
        characterNames.add("Clyde");
        characterNames.add("X01F");


        //Setup of Screen
        TextButton.TextButtonStyle textStyle;
        BitmapFont buttonFont;


        textStyle = new TextButton.TextButtonStyle();
        buttonFont = new BitmapFont(Gdx.files.internal("skins/quantum-horizon/raw/font-export.fnt"));
        textStyle.font = buttonFont;
        textStyle.fontColor = MAGENTA;

        Texture button1 = new Texture("controller/rightArrow.png");
        Texture button2 = new Texture("controller/LTd5arLKc.png");

        right = new Image(button1);
        right.setSize(3 / sfs.PPM,3 / sfs.PPM);

        left = new Image(button2);
        left.setSize(3 / sfs.PPM,3/ sfs.PPM);



        choose = new TextButton("Select",textStyle);



        stage = new Stage(viewport, sfs.batch);
        table = new Table();
        table.setFillParent(true);
        table.center();

        table.add(left).left().size(button2.getWidth() / 10,button2.getHeight() / 10).padRight(80).padBottom(30);
        table.add(right).right().size(button1.getWidth() / 10, button1.getHeight() / 10).padLeft(80).padBottom(30);
        table.row();
        table.row();
        table.add(choose).center().padLeft(140);
        table.row();

        stage.addActor(table);
        Gdx.input.setInputProcessor(stage);

        right.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y){
                if(Gdx.app.getType() == Application.ApplicationType.Desktop) {
                    sfs.loadSound("audio/sounds/mixkit-gear-metallic-lock-sound-2858.wav");
                    long id = sfs.sound.play();
                    if (sfs.getSoundVolume() != 0)
                        sfs.sound.setVolume(id, sfs.getSoundVolume());
                    else {
                        sfs.sound.setVolume(id, 0);
                    }
                }
                if(Gdx.app.getType() == Application.ApplicationType.Android) {
                    sfs.manager.get("audio/sounds/mixkit-gear-metallic-lock-sound-2858.wav", Sound.class).play(sfs.getSoundVolume());
                }
                if (i < 2) {
                    characterSprites.get(i++);
                    characterNames.get(i++);
                }else if(i == 2){
                    i = 0;
                    characterSprites.get(i);
                    characterNames.get(i);
                }
            }
        });


        left.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y){
                if(Gdx.app.getType() == Application.ApplicationType.Desktop) {
                    sfs.loadSound("audio/sounds/mixkit-gear-metallic-lock-sound-2858.wav");
                    long id = sfs.sound.play();
                    if (sfs.getSoundVolume() != 0)
                        sfs.sound.setVolume(id, sfs.getSoundVolume());
                    else {
                        sfs.sound.setVolume(id, 0);
                    }
                }
                if(Gdx.app.getType() == Application.ApplicationType.Android) {
                    sfs.manager.get("audio/sounds/mixkit-gear-metallic-lock-sound-2858.wav", Sound.class).play(sfs.getSoundVolume());
                }
                if (i > 0) {
                    characterSprites.get(i--);
                    characterNames.get(i--);
                }else if(i  == 0){
                    i = 2;
                    characterSprites.get(i);
                    characterNames.get(i);
                }
            }
        });


        choose.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y){
                if(Gdx.app.getType() == Application.ApplicationType.Desktop) {
                    sfs.loadSound("audio/sounds/mixkit-gear-metallic-lock-sound-2858.wav");
                    long id = sfs.sound.play();
                    if (sfs.getSoundVolume() != 0)
                        sfs.sound.setVolume(id, sfs.getSoundVolume());
                    else {
                        sfs.sound.setVolume(id, 0);
                    }
                }
                if(Gdx.app.getType() == Application.ApplicationType.Android) {
                    sfs.manager.get("audio/sounds/mixkit-gear-metallic-lock-sound-2858.wav", Sound.class).play(sfs.getSoundVolume());
                }

                switch(i){
                    case 0:
                        selected = sfs.getBikerAtlas();
                        break;

                    case 1:
                        selected = sfs.getPunkAtlas();
                        break;

                    case 2:
                        selected = sfs.getCyborgAtlas();
                        break;

                    default:
                        selected = sfs.getBikerAtlas();
                }
                sfs.setPlayersChoice(selected);
                sfs.setScreen(new PlayScreen(sfs,1));
            }
        });
    }


        @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0,0,0,1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        stage.draw();

        sfs.batch.begin();
        sfs.batch.draw(characterSprites.get(i),viewport.getScreenWidth() / 2,viewport.getScreenHeight() / 2);/*
        sfs.batch.draw(characterNames.get(i),400,380);*/
        sfs.batch.end();
    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void show() {

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
