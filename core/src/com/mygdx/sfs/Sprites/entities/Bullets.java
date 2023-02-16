package com.mygdx.sfs.Sprites.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Timer;
import com.mygdx.sfs.Screens.PlayScreen;
import com.mygdx.sfs.shootForSurvival;

public class Bullets {

    public static final float SPEED = 3f;
    public static final float BACK = -3f;
    private static Texture texture;
    private shootForSurvival sfs;
    private FixtureDef bulletDef;
    private Body bulletBody;
    private boolean shooting;
    private World world;
    private PlayScreen screen;

    public float x,y;
    public boolean todestroy = false;

    public Bullets(shootForSurvival sfs, PlayScreen screen, float x, float y) {

        world = screen.getWorld();
        this.sfs = sfs;
        this.screen = screen;
        this.y = y + 4 / sfs.PPM;
        this.x = x;

        shooting = false;

        defineBullet();

        if (bulletBody.getPosition().x > screen.getGamePort().getScreenWidth()){
            todestroy = true;
        }
    }

    public Fixture defineBullet() {
        BodyDef bdef = new BodyDef();
        bdef.position.set(x,y);
        bdef.type = BodyDef.BodyType.DynamicBody;
        bulletBody = world.createBody(bdef);
        bulletBody.setBullet(true);
        if(!screen.getPlayer().isFlipX()) {
            bulletBody.setLinearVelocity(SPEED,0);
        }else {
            bulletBody.setLinearVelocity(BACK, 0);
        }
        bulletDef = new FixtureDef();
        CircleShape shape = new CircleShape();
        shape.setRadius(0.3f / shootForSurvival.PPM);
        bulletDef.filter.categoryBits = shootForSurvival.BULLET_BIT;
        bulletDef.filter.maskBits = shootForSurvival.ENEMY_BIT |
                shootForSurvival.GROUND_BIT;

        bulletDef.shape = shape;
        Fixture fix1 = bulletBody.createFixture(bulletDef);
        bulletBody.setGravityScale(0);
        Gdx.app.log("bullet", "shoot");

        return fix1;
    }

    public void update(float dt){
        if (dt < 5f){
            if(screen.getPlayer().getX() + 40 == bulletBody.getPosition().x){
                shooting = true;
                destroy();
                Gdx.app.log("Bullet", "Destroyed");
            }
        }
    }

    public void destroy(){
        if(todestroy == true || bulletBody.getPosition().x > screen.getPlayer().getX() + 40){
            screen.getWorld().destroyBody(bulletBody);
        }
    }

    public boolean isAttacking(){
        return shooting;
    }

    public void setIsAttacking(boolean shot){
        shooting = shot;
    }

    public void setDestroy(boolean destroy) {
        this.todestroy = destroy;
    }
}
