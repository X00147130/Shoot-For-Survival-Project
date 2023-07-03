package com.mygdx.sfs.Sprites.Items;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import com.mygdx.sfs.Scenes.Screens.PlayScreen;
import com.mygdx.sfs.Sprites.Enemies.Grunts.Worker;
import com.mygdx.sfs.shootForSurvival;

public class Bullets {

    public static final float SPEED = 3f;
    private TextureRegion clip;
    private shootForSurvival sfs;
    private FixtureDef bulletDef;
    public Body bulletBody;
    public boolean destroyed;
    private World world;
    private PlayScreen screen;
    private int powerLVL = 0;

    public float x,y;
    public boolean shot = false;

    public Bullets(shootForSurvival sfs,PlayScreen screen, float x, float y) {

        world = screen.getWorld();
        this.sfs = sfs;
        this.y = y + 5 / sfs.PPM;
        this.x = x + 2 / sfs.PPM;
        this.screen = screen;
        clip = sfs.getPistolBullets().findRegion("1");
        destroyed = false;
        defineBullet();
    }

    public Fixture defineBullet() {
        BodyDef bdef = new BodyDef();
        bdef.position.set(x,y);
        bdef.type = BodyDef.BodyType.DynamicBody;
        bulletBody = world.createBody(bdef);


        bulletBody.setBullet(true);
        if(!screen.getPlayer().isFlipX()) {
            bulletBody.setLinearVelocity(SPEED,0);
        }else{
            bulletBody.setLinearVelocity(-SPEED,0);
        }


        bulletDef = new FixtureDef();
        CircleShape shape = new CircleShape();
        shape.setRadius(0.6f / shootForSurvival.PPM);
        bulletDef.filter.categoryBits = shootForSurvival.BULLET_BIT;
        bulletDef.filter.maskBits = shootForSurvival.ENEMY_BIT |
                shootForSurvival.GROUND_BIT|
                shootForSurvival.WALL_BIT|
                shootForSurvival.ITEM_BIT|
                shootForSurvival.PLAYER_BIT|
                shootForSurvival.HAMMER_BIT;

        bulletDef.shape = shape;
        Fixture fix1 = bulletBody.createFixture(bulletDef);
        fix1.setUserData(this);
        bulletBody.setGravityScale(0);
        Gdx.app.log("bullet", "shoot");

        return fix1;
    }

    public void destroy(){
        shot = true;
    }

    public void update(float dt){
       y = SPEED * dt;
       if(shot && !destroyed) {
           world.destroyBody(bulletBody);
           destroyed = true;
       }


       powerLVL = sfs.getPowerLVL();
        if(powerLVL == 1) {
            clip = sfs.getRifleBullets().findRegion("1");
        }
       else if(powerLVL == 2) {
            clip = sfs.getRifleBullets().findRegion("2");
        }

        else if(powerLVL == 3){
            clip = sfs.getRifleBullets().findRegion("3");
        }

        else if(powerLVL == 4){
            clip = sfs.getRifleBullets().findRegion("4");
        }

        else if(powerLVL == 5){
            clip = sfs.getRifleBullets().findRegion("5");
        }

        else if(powerLVL == 6){
            clip = sfs.getRifleBullets().findRegion("6");
        }

        else if(powerLVL == 7){
            clip = sfs.getRifleBullets().findRegion("7");
        }

        else if(powerLVL == 8){
            clip = sfs.getRifleBullets().findRegion("8");
        }

        else if(powerLVL == 9){
            clip = sfs.getRifleBullets().findRegion("9");
        }

        else if(powerLVL == 10){
            clip = sfs.getRifleBullets().findRegion("10");
        }

    }

    public void render(SpriteBatch batch){
        batch.begin();
        batch.draw(clip, bulletBody.getPosition().x, bulletBody.getPosition().y,clip.getRegionWidth() / sfs.PPM,clip.getRegionHeight() / sfs.PPM);
        batch.end();

    }
}
