package com.mygdx.sfs.Screens;

import static com.mygdx.sfs.shootForSurvival.PPM;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;
import com.mygdx.sfs.Sprites.Player;
import com.mygdx.sfs.shootForSurvival;

import java.util.ArrayList;


public class CharacterSelect implements Screen {
    /* Admin Inits */
    private shootForSurvival sfs;
    private AssetManager manager;
    private Player player;
    private PlayScreen screen;
    private Animation preview;

    /* Arrays To Loop through for Selection */
    private ArrayList<TextureAtlas> characterSprites;
    private ArrayList<String> characterNames;

    public CharacterSelect(shootForSurvival sfs){
        this.sfs = sfs;
        this.manager = sfs.manager;

        //Sprites added into arraylist
        characterSprites.add(sfs.getPunkAtlas());
        characterSprites.add(sfs.getBikerAtlas());
        characterSprites.add(sfs.getCyborgAtlas());

        //Names of characters added to the arraylist
        characterNames.add("Chad");
        characterNames.add("Clyde");
        characterNames.add("X01F");

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
