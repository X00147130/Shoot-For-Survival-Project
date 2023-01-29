package com.mygdx.sfs.Sprites.entities;


import com.badlogic.gdx.Application;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.EdgeShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Timer;
import com.mygdx.sfs.Screens.PlayScreen;
import com.mygdx.sfs.Sprites.Ryu;
import com.mygdx.sfs.shootForSurvival;

public class Bullets {

    public static final int SPEED = 10;
    public static final float DEFAULT_X = 40;
    private static Texture texture;
    private shootForSurvival sfs;
    private FixtureDef bulletDef;
    private Body bulletBody;
    private Ryu player;
    private boolean shooting;


    public World world;
    public float x,y;
    public boolean remove = false;

    public Bullets(shootForSurvival sfs, PlayScreen screen, Ryu player, float x, float y){

        this.y = y;
        this.x = x;
        this.sfs= sfs;
        this.player = player;
        shooting = false;
        world = screen.getWorld();


        if(texture == null){
            texture = new Texture("assets/istockphoto-510805878-612x612.jpg");
        }
    }

    public void createBullet(){
        if(!player.isFlipX()){
            bulletBody.applyLinearImpulse(new Vector2(3,0),player.b2body.getPosition(), true);
        }
        else {
            bulletBody.applyLinearImpulse(new Vector2(-3,0),player.b2body.getPosition(), true);
        }
        player.b2body.setAwake(true);


        //Collision Detection Line

        BodyDef bullet = new BodyDef();
        bullet.position.set(player.getX() + 10 ,player.getY());
        bullet.type = BodyDef.BodyType.DynamicBody;
        bulletBody = world.createBody(bullet);


        bulletDef = new FixtureDef();
        EdgeShape shape = new EdgeShape();
        if(!player.isFlipX()){
            shape.set(new Vector2(3/ shootForSurvival.PPM,0/shootForSurvival.PPM),new Vector2(-1/shootForSurvival.PPM,  0 / shootForSurvival.PPM));
        }
        else{
            shape.set(new Vector2(1/ shootForSurvival.PPM,0/shootForSurvival.PPM),new Vector2(-3/shootForSurvival.PPM, 0  / shootForSurvival.PPM));
        }

        bulletDef.shape = shape;
        bulletDef.isSensor = false;
        bulletDef.restitution = 1f;
        bulletDef.friction = 2f;
        bulletDef.filter.categoryBits= shootForSurvival.BULLET_BIT;
        bulletDef.filter.maskBits = shootForSurvival.ENEMY_BIT| shootForSurvival.FINISH_BIT;

        bulletBody.createFixture(bulletDef).setUserData(this);

        if(Gdx.app.getType() == Application.ApplicationType.Desktop) {
            sfs.loadSound("audio/sounds/mixkit-fast-sword-whoosh-2792.wav");
            long id = sfs.sound.play();
            if (sfs.getSoundVolume() != 0)
                sfs.sound.setVolume(id, sfs.getSoundVolume());
            else {
                sfs.sound.setVolume(id, 0);
            }
        }
        if(Gdx.app.getType() == Application.ApplicationType.Android) {
            shootForSurvival.manager.get("audio/sounds/mixkit-fast-sword-whoosh-2792.wav", Sound.class).play(sfs.getSoundVolume());
        }
    }
    public boolean isAttacking(){
        return shooting;
    }

    public void setIsAttacking(boolean shot){
        shooting = shot;
    }

    public void shoot(){
        if(!shooting&& player.currentState != Ryu.State.DEAD){
            shooting = true;
            Timer time = new Timer();
            player.currentState = Ryu.State.SHOOTING;
            Timer.Task task = time.scheduleTask(new Timer.Task(){
                @Override
                public void run() {
                    player.currentState = Ryu.State.STANDING;
                }
            },0.5f);

            createBullet();
            if(isAttacking() == true){
                Timer.Task task1 = time.scheduleTask(new Timer.Task() {
                    @Override
                    public void run() {
                        while(player.b2body.getFixtureList().size > 2){

                            player.b2body.destroyFixture(player.b2body.getFixtureList().pop());
                        }
                        shooting = false;
                    }
                },1f);
            }

        }
    }

}
