package com.mygdx.sfs.Sprites.Items;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;
import com.mygdx.sfs.Scenes.Screens.PlayScreen;
import com.mygdx.sfs.shootForSurvival;

public class Bullets{

    public static final float SPEED = 3f;
    private TextureRegion clip;
    private shootForSurvival sfs;
    private FixtureDef bulletDef;
    public Body bulletBody;
    public boolean destroy;
    private World world;
    private PlayScreen screen;
    private int powerLVL = 1;

    private int pistolLvl = 1;

    private double damage = 0.5;

    public float x,y;

    public Bullets(shootForSurvival sfs,PlayScreen screen, float x, float y) {

        world = screen.getWorld();
        this.sfs = sfs;
        this.y = y + 5 / sfs.PPM;
        this.x = x + 2 / sfs.PPM;
        this.screen = screen;
        destroy = false;
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
        bulletDef.filter.maskBits = shootForSurvival.ENEMY_BIT|
                shootForSurvival.BOSS_BIT |
                shootForSurvival.GROUND_BIT|
                shootForSurvival.WALL_BIT|
                shootForSurvival.ITEM_BIT|
                shootForSurvival.PLAYER_BIT;

        bulletDef.shape = shape;
        Fixture fix1 = bulletBody.createFixture(bulletDef);
        fix1.setUserData(this);
        fix1.setFriction(1f);
        bulletBody.setGravityScale(0);
        Gdx.app.log("bullet", "shoot");

        if(destroy) {
            bulletBody.setUserData(null);
            bodyRemoval();
        }

        return fix1;
    }

    public void destroy(){
        destroy = true;
        bodyRemoval();
    }

    public void update(float dt) {
        y = SPEED * dt;

        if (dt > 2f) {
            destroy();
        }

        pistolLvl = sfs.getPistolLvl();
        
         switch(pistolLvl){
             case 1:
                 sfs.setPowerLVL(1);
                 break;
             case 2:
                 sfs.setPowerLVL(2);
                 break;
             case 3:
                 sfs.setPowerLVL(3);
                 break;
             case 4:
                 sfs.setPowerLVL(4);
                 break;
             case 5:
                 sfs.setPowerLVL(5);
                 break;
             case 6:
                 sfs.setPowerLVL(6);
                 break;
             case 7:
                 sfs.setPowerLVL(7);
                 break;
             case 8:
                 sfs.setPowerLVL(8);
                 break;
             case 9:
                 sfs.setPowerLVL(9);
                 break;
             case 10:
                 sfs.setPowerLVL(10);
                 break;
         }

         switch (pistolLvl) {
             case 1:
                 clip = sfs.getPistolBullets().findRegion("1");
                 damage = 0.5;
                 break;
             case 2:
                 clip = sfs.getPistolBullets().findRegion("2");
                 damage = 1.5;
                 break;
             case 3:
                 clip = sfs.getPistolBullets().findRegion("3");
                 damage = 2.5;
                 break;
             case 4:
                 clip = sfs.getPistolBullets().findRegion("4");
                 damage = 3.5;
                 break;
             case 5:
                 clip = sfs.getPistolBullets().findRegion("5");
                 damage = 4.5;
                 break;
             case 6:
                 clip = sfs.getPistolBullets().findRegion("6");
                 damage = 5.5;
                 break;
             case 7:
                 clip = sfs.getPistolBullets().findRegion("7");
                 damage = 6.5;
                 break;
             case 8:
                 clip = sfs.getPistolBullets().findRegion("8");
                 damage = 7.5;
                 break;
             case 9:
                 clip = sfs.getPistolBullets().findRegion("9");
                 damage = 8.5;
                 break;
             case 10:
                 clip = sfs.getPistolBullets().findRegion("10");
                 damage = 9.5;
                 break;
         }


         switch (powerLVL) {
             case 1:
                 clip = sfs.getRifleBullets().findRegion("1");
                 damage = 1;
                 break;
             case 2:
                 clip = sfs.getRifleBullets().findRegion("2");
                 clip = sfs.getRifleBullets().findRegion("2");
                 damage = 2;
                 break;
             case 3:
                 clip = sfs.getRifleBullets().findRegion("3");
                 damage = 3;
                 break;
             case 4:
                 clip = sfs.getRifleBullets().findRegion("4");
                 damage = 4;
                 break;
             case 5:
                 clip = sfs.getRifleBullets().findRegion("5");
                 damage = 5;
                 break;
             case 6:
                 clip = sfs.getRifleBullets().findRegion("6");
                 damage = 6;
                 break;
             case 7:
                 clip = sfs.getRifleBullets().findRegion("7");
                 damage = 7;
                 break;
             case 8:
                 clip = sfs.getRifleBullets().findRegion("8");
                 damage = 8;
                 break;
             case 9:
                 clip = sfs.getRifleBullets().findRegion("9");
                 damage = 9;
                 break;
             case 10:
                 clip = sfs.getRifleBullets().findRegion("10");
                 damage = 10;
                 break;
         }

    }

        public void bodyRemoval(){
        if (destroy && !world.isLocked()) {
            world.destroyBody(bulletBody);
            world.clearForces();
        }
    }
    public void render(SpriteBatch batch){
        batch.draw(clip, bulletBody.getPosition().x, bulletBody.getPosition().y, clip.getRegionWidth() / sfs.PPM, clip.getRegionHeight() / sfs.PPM);
    }

    public double getDamage() {
        return damage;
    }
}
