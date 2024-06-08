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
    private int powerLVL = 0;

    private int pistolLvl = 1;

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

        if(destroy)
            bulletBody.setUserData(null);

        return fix1;
    }

    public void destroy(){
        destroy = true;
        bodyRemoval();
    }

    public void update(float dt) {
        y = SPEED * dt;

        if (dt > 10f) {
            destroy();
        }

        pistolLvl = sfs.getPistolLvl();

        for (int i = 1; i <= sfs.getPistolBullets().getRegions().size; i++) {
            if (pistolLvl == i) {
                clip = sfs.getPistolBullets().findRegion("" + i);
            }
        }

        powerLVL = sfs.getPowerLVL();
        for (int i = 1; i <= sfs.getRifleBullets().getRegions().size; i++) {
            if (powerLVL == i) {
                clip = sfs.getRifleBullets().findRegion("" + i);
            }
        }
    }

        public void bodyRemoval(){
        if (destroy && !world.isLocked()) {
            world.destroyBody(bulletBody);
            world.clearForces();
        }
    }
    public void render(SpriteBatch batch){
        batch.draw(clip, bulletBody.getPosition().x, bulletBody.getPosition().y,clip.getRegionWidth() / sfs.PPM,clip.getRegionHeight() / sfs.PPM);
    }
}
