package com.mygdx.sfs.Screens;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.mygdx.sfs.Sprites.Player;
import com.mygdx.sfs.shootForSurvival;

import java.util.ArrayList;


public class CharacterSelect implements Screen {
    /* Admin Inits */
    private shootForSurvival sfs;
    private AssetManager manager;
    private Player player;
    private PlayScreen screen;

    /* Arrays To Loop through for Selection */
    private ArrayList<Animation> characterSprites;
    private ArrayList<String> characterNames;

    /* Setters/inits for the Loops */
    private TextureAtlas character;
    private String sprite;

    public CharacterSelect(shootForSurvival sfs){
        this.sfs = sfs;
        this.manager = sfs.manager;
        screen = new PlayScreen(sfs,1);
        player = new Player(screen, sfs);

        //reading in the animations
        /*character = new TextureAtlas("sprites/characters/"+characterNames+".pack");*/



        //Sprites added into arraylist
        /*characterSprites.add();*/

        //Names of characters added to the arraylist
        /*characterNames.add();*/


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
