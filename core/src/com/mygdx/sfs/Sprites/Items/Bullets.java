package com.mygdx.sfs.Sprites.Items;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.sfs.shootForSurvival;

public class Bullets {

    public static final float SPEED = 3f;
    private static Texture texture;
    private shootForSurvival sfs;

    private float x,y;
    public boolean todestroy = false;

    public Bullets(shootForSurvival sfs, float x, float y) {
        this.x = x;
        this.y = y;
        this.sfs = sfs;

        texture = sfs.manager.get("sprites/bullet.png", Texture.class);
    }



    public void update(float dt){
       y = SPEED * dt;
       if( y > Gdx.graphics.getWidth())
           todestroy = true;
    }

    public void render(SpriteBatch sb){
        sb.draw(texture, x, y);
    }

}
