package com.mygdx.sfs.Sprites.Items;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;
import com.mygdx.sfs.Scenes.Screens.PlayScreen;
import com.mygdx.sfs.Sprites.Player;
import com.mygdx.sfs.shootForSurvival;

public abstract class Item extends Sprite {
    protected PlayScreen screen;
    protected World world;
    protected boolean todestroy;
    protected boolean destroyed;
    protected Body body;

    public Item ( PlayScreen screen, float x, float y){
        this.screen = screen;
        this.world = screen.getWorld();
        setPosition(x,y);
        setBounds(getX(), getY(),16 / shootForSurvival.PPM, 16 / shootForSurvival.PPM);
        defineItem();
        todestroy= false;
        destroyed= false;

    }

    public abstract void defineItem();
    public abstract void useItem(Player player);

    public void update(float dt){
        if(todestroy && !destroyed){
            world.destroyBody(body);
            destroyed = true;
        }
    }

    public void draw(Batch batch){
        if(!destroyed)
            super.draw(batch);
    }

    public void destroy(){
        todestroy = true;
    }

    public boolean getDestroyed(){
        return destroyed;
    }

}