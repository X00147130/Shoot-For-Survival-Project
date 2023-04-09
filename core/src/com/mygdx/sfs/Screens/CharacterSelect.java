package com.mygdx.sfs.Screens;

import static com.badlogic.gdx.graphics.Color.MAGENTA;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mygdx.sfs.Sprites.Player;
import com.mygdx.sfs.shootForSurvival;

import java.awt.Button;
import java.util.ArrayList;


public class CharacterSelect implements Screen {
    /* Admin Inits */
    private shootForSurvival sfs;
    private Viewport viewport;
    private Animation preview;

    /* Arrays To Loop through for Selection */
    private ArrayList<TextureAtlas> characterSprites;
    private ArrayList<String> characterNames;

    //Selection and buttons
    private TextureAtlas selected;
    private Image left, right;
    private TextButton choose;
    private Table table;
    private Stage stage;


    public CharacterSelect(shootForSurvival game){
        this.sfs = game;
        viewport = new FitViewport(sfs.V_WIDTH,sfs.V_HEIGHT, new OrthographicCamera());


        //Sprites added into arraylist
        characterSprites.add(sfs.getPunkAtlas());
        characterSprites.add(sfs.getBikerAtlas());
        characterSprites.add(sfs.getCyborgAtlas());


        //Names of characters added to the arraylist
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


        right = new Image(new Texture("controller/rightArrow.png"));
        right.setSize(30,30);


        left = new Image(new Texture("LTd5arLKc.png"));
        left.setSize(30,30);


        choose = new TextButton("Select",textStyle);


        stage = new Stage(viewport, sfs.batch);
        table = new Table();
        table.setFillParent(true);
        table.center();

        table.add(left).left();
        table.add(right).right();
        table.row();
        table.row();
        table.add(choose);
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

                select();
                sfs.setScreen(new PlayScreen(sfs,1));
            }
        });
        //Getting animations to run

        /*Setting up a for loop to iterate through the array so i can use the texture atlas i want.
        When click button hopefully i can add to the iterator so that i can display three characters in idle animation.*/

        Array<TextureRegion> frames = new Array<TextureRegion>();
        frames.clear();

        for(int i =0; i< characterSprites.size(); i++){

            switch(i){
                case 0:
                    frames.add(sfs.getPunkAtlas().findRegion("Idle1"));
                    frames.add(sfs.getPunkAtlas().findRegion("Idle2"));
                    frames.add(sfs.getPunkAtlas().findRegion("Idle3"));
                    frames.add(sfs.getPunkAtlas().findRegion("Idle4"));
                    preview = new Animation<TextureRegion>(0.3f, frames, Animation.PlayMode.LOOP);
                    /*setBounds(0, 0, 18 / PPM, 20 / PPM);*/
                    /*if(Gdx.app.getType() == Application.ApplicationType.Android){
                        setBounds(0, 0, 18 / PPM, 20 / PPM);
                    }*/
                    frames.clear();
                    break;

                case 1:
                    frames.add(sfs.getBikerAtlas().findRegion("Idle1"));
                    frames.add(sfs.getBikerAtlas().findRegion("Idle2"));
                    frames.add(sfs.getBikerAtlas().findRegion("Idle3"));
                    frames.add(sfs.getBikerAtlas().findRegion("Idle4"));
                    preview = new Animation<TextureRegion>(0.3f, frames, Animation.PlayMode.LOOP);
                    /*setBounds(0, 0, 18 / PPM, 20 / PPM);*/
                    /*if(Gdx.app.getType() == Application.ApplicationType.Android){
                           setBounds(0, 0, 18 / PPM, 20 / PPM);
                    }*/
                    frames.clear();
                    break;

                case 2:
                    frames.add(sfs.getCyborgAtlas().findRegion("Idle1"));
                    frames.add(sfs.getCyborgAtlas().findRegion("Idle2"));
                    frames.add(sfs.getCyborgAtlas().findRegion("Idle3"));
                    frames.add(sfs.getCyborgAtlas().findRegion("Idle4"));
                    preview = new Animation<TextureRegion>(0.3f, frames, Animation.PlayMode.LOOP);
                    /*setBounds(0, 0, 18 / PPM, 20 / PPM);*/
                    /*if(Gdx.app.getType() == Application.ApplicationType.Android){
                            setBounds(0, 0, 18 / PPM, 20 / PPM);
                    }*/
                    frames.clear();
                    break;

                default:

            }

        }
    }


    public TextureAtlas select(){
       for(int i = 0; i < characterSprites.size();)
        if(choose.isPressed()){
            selected = characterSprites.get(i);
        }
       else{
           i++;
        }
        return selected;
    }

    @Override
    public void show() {

    }


    @Override
    public void render(float delta) {

    }

    @Override
    public void resize(int width, int height) {

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
