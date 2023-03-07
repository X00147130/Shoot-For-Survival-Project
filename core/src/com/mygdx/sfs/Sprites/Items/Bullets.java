package com.mygdx.sfs.Sprites.Items;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;
import com.mygdx.sfs.Screens.PlayScreen;
import com.mygdx.sfs.Sprites.Enemies.Enemy;
import com.mygdx.sfs.shootForSurvival;

public class Bullets {

    public static final float SPEED = 3f;
    private Texture clip;
    private shootForSurvival sfs;
    private FixtureDef bulletDef;
    public Body bulletBody;
    public boolean destroyed;
    private World world;
    private PlayScreen screen;

    public float x,y;
    public boolean shot = false;

    public Bullets(shootForSurvival sfs,PlayScreen screen, float x, float y) {

        world = screen.getWorld();
        this.sfs = sfs;
        this.y = y;
        this.x = x;
        this.screen = screen;
        clip = new Texture("sprites/bullet.png");
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
                shootForSurvival.GROUND_BIT;

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
    }

    public void render(SpriteBatch batch){
        batch.begin();
        batch.draw(clip, bulletBody.getPosition().x, bulletBody.getPosition().y,clip.getWidth() / sfs.PPM,clip.getHeight() / sfs.PPM);
        batch.end();

    }
    public void dispose(){
        clip.dispose();
    }
}
