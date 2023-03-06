package com.mygdx.sfs.Sprites;

import static com.mygdx.sfs.shootForSurvival.PPM;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.EdgeShape;
import com.badlogic.gdx.physics.box2d.Filter;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Timer;
import com.mygdx.sfs.Screens.PlayScreen;
import com.mygdx.sfs.Sprites.Items.Bullets;
import com.mygdx.sfs.shootForSurvival;

public class Player extends Sprite {
    //State Variables for animation purposes
    public enum State{ FALLING, JUMPING, STANDING, RUNNING, DEAD, COMPLETE}
    public State currentState;
    public State previousState;


    //Basic variables
    public World world;
    private PlayScreen screen;
    private shootForSurvival sfs;
    public Body b2body;
    public TextureRegion ryuStand;


    //Animation Variables
    private Animation <TextureRegion> ryuRun;
    private Animation <TextureRegion> ryuJump;
    private Animation <TextureRegion> ryuDead;
    private Animation<TextureRegion> ryuComplete;
    private boolean runningRight;
    private float stateTimer;


    //boolean tests
    private boolean ryuIsDead;


    //health variables
    private int health;
    private int damage;
    private static int hitCounter;

    /*Jump Variables*/
    private int jumpCounter = 0;

    //Attack Variables
    private Fixture fix;

    //movement variables
    private Vector2 limit;


    public Player(PlayScreen screen, shootForSurvival sfs){
        this.world = screen.getWorld();
        definePlayer();
        this.sfs = sfs;

        limit = new Vector2(0,0);

        this.screen = screen;
        
        //initialising health variables
        health = 100;
        damage = 50;
        hitCounter = 0;


        //Animation variables initialization
        currentState = State.STANDING;
        previousState = State.STANDING;
        stateTimer = 0;
        runningRight = true;

        //Animation initialization for Mario Standing
        ryuStand = new TextureRegion(screen.getAtlas().findRegion("attack1"));
        if(Gdx.app.getType() == Application.ApplicationType.Desktop) {
            setBounds(0, 0, 16 / PPM, 18 / PPM);
            setRegion(ryuStand);
        }
        if(Gdx.app.getType() == Application.ApplicationType.Android){
            setBounds(0, 0, 18 / PPM, 20 / PPM);
            setRegion(ryuStand);
        }

        //Creating Animation loop for Ryu running
        Array<TextureRegion> frames = new Array<TextureRegion>();
        frames.clear();

        frames.add(screen.getAtlas().findRegion("running1"));
        frames.add(screen.getAtlas().findRegion("running2"));
        frames.add(screen.getAtlas().findRegion("running3"));
        frames.add(screen.getAtlas().findRegion("running4"));
        frames.add(screen.getAtlas().findRegion("running5"));
        frames.add(screen.getAtlas().findRegion("running6"));

        ryuRun = new Animation <TextureRegion>(0.1f, frames);
        if(Gdx.app.getType() == Application.ApplicationType.Android) {
            setBounds(0, 0, 18 / PPM, 20 / PPM);
        }
        frames.clear();

        //Creating Jump Animation loop
        frames.clear();

        frames.add(screen.getAtlas().findRegion("jumpup2"));
        frames.add(screen.getAtlas().findRegion("jumpup4"));
        frames.add(screen.getAtlas().findRegion("jumpup5"));
        frames.add(screen.getAtlas().findRegion("jumpup6"));
        frames.add(screen.getAtlas().findRegion("jumpup7"));

        ryuJump = new Animation <TextureRegion>(0.1f, frames);
        if(Gdx.app.getType() == Application.ApplicationType.Android) {
            setBounds(0, 0, 18 / PPM, 20 / PPM);
        }
        frames.clear();


        //Ryu death animation

        frames.add(screen.getAtlas().findRegion("die2"));
        frames.add(screen.getAtlas().findRegion("die3"));
        frames.add(screen.getAtlas().findRegion("die4"));
        frames.add(screen.getAtlas().findRegion("die5"));
        frames.add(screen.getAtlas().findRegion("die7"));
        frames.add(screen.getAtlas().findRegion("die8"));
        frames.add(screen.getAtlas().findRegion("die10"));

        ryuDead = new Animation <TextureRegion>(0.1f, frames);
        if(Gdx.app.getType() == Application.ApplicationType.Android){
            setBounds(0, 0, 18 / PPM, 20 / PPM);}
        frames.clear();


        //Level Complete
        frames.add(screen.getAtlas().findRegion("attack2"));
        frames.add(screen.getAtlas().findRegion("attack3"));
        ryuComplete = new Animation<TextureRegion>(0.2f,frames);
        if(Gdx.app.getType() == Application.ApplicationType.Android) {
            setBounds(0, 0, 18 / PPM, 20 / PPM);
        }
        frames.clear();
    }

    public void update(float dt){
        if(Gdx.app.getType() == Application.ApplicationType.Android)
            setPosition(b2body.getPosition().x - getWidth() /2, b2body.getPosition().y - getHeight() /2);
        setPosition(b2body.getPosition().x - getWidth() /2, b2body.getPosition().y - getHeight() /3);
        setRegion(getFrame(dt));
        if(getY() < 0){
            ryuIsDead = true;
            b2body.applyLinearImpulse(new Vector2(0, 3f), b2body.getWorldCenter(), true);
        }
    }

    public TextureRegion getFrame(float dt){
        currentState = getState();

        TextureRegion region;


        switch(currentState){
           case DEAD:
                region = ryuDead.getKeyFrame(stateTimer, false);
            break;

            case JUMPING:
                region =  ryuJump.getKeyFrame(stateTimer, false);
                break;

            case RUNNING:
                region = ryuRun.getKeyFrame(stateTimer, true);
               break;


            case COMPLETE:
                region = ryuComplete.getKeyFrame(stateTimer, true);
                break;

            case FALLING:

            case STANDING:

            default:
                region = ryuStand;
                break;
        }
        if ((b2body.getLinearVelocity().x < 0 || !runningRight) && !region.isFlipX()) {
            region.flip(true, false);
            runningRight = false;
        } else if ((b2body.getLinearVelocity().x > 0 || runningRight) && region.isFlipX()) {
            region.flip(true, false);
            runningRight = true;
        }
        stateTimer = currentState == previousState ? stateTimer + dt : 0;
        previousState = currentState;
        return region;
        }

    public State getState(){
        if(ryuIsDead)
            return State.DEAD;

        else if (b2body.getLinearVelocity().y > 0 || (b2body.getLinearVelocity().y < 0 && previousState == State.JUMPING))
            return State.JUMPING;

        else if(b2body.getLinearVelocity().y < 0)
            return State.FALLING;

        else if (b2body.getLinearVelocity().x != 0)
            return State.RUNNING;


        else if(screen.complete == true) {
            return State.COMPLETE;

        }

        else
            return State.STANDING;
    }



    public void definePlayer(){
        BodyDef bdef = new BodyDef();
        bdef.position.set(282 / PPM,31 / PPM);
        bdef.type = BodyDef.BodyType.DynamicBody;
        b2body = world.createBody(bdef);

        FixtureDef fdef = new FixtureDef();
        CircleShape shape = new CircleShape();
        shape.setRadius(6 / PPM);
        fdef.filter.categoryBits = shootForSurvival.PLAYER_BIT;
        fdef.filter.maskBits = shootForSurvival.GROUND_BIT |
                shootForSurvival.FINISH_BIT |
                shootForSurvival.PLATFORM_BIT |
                shootForSurvival.ENEMY_BIT|
                shootForSurvival.MONEY_BIT|
                shootForSurvival.SKY_BIT|
                shootForSurvival.ITEM_BIT;

        fdef.shape = shape;
        fdef.restitution = 0f;
        fdef.friction = 2f;
        b2body.setGravityScale(1.5f);
        b2body.createFixture(fdef).setUserData(this);


    //Ryus Head(Used for colliding with bricks
        EdgeShape head = new EdgeShape();
        head.set(new Vector2(-2 / PPM, 6 / PPM), new Vector2(2 / PPM, 6 / PPM));
        fdef.filter.categoryBits=shootForSurvival.RYU_HEAD_BIT;
        fdef.shape = head;
        fdef.isSensor = true;

        b2body.createFixture(fdef).setUserData(this);

    }

    /*Jump Counter System*/
    public void jumpReset(){
        sfs.jumpCounter = 0;
        sfs.doubleJumped = false;
        Gdx.app.log("Jumps", "Reset");
    }

    //dash method
    public void dash(){
        if(!isFlipX()){
            b2body.applyLinearImpulse(new Vector2(3f,0),new Vector2(-0.1f,0), true);
        }
        else {
            b2body.applyLinearImpulse(new Vector2(-3f,0),new Vector2(0.1f,0), true);
        }
        b2body.setAwake(true);
    }


    //getting hit method
    public void hit(){

        if(hitCounter < 2){    //ryu is pushed back and says ow
            b2body.applyLinearImpulse(new Vector2(-1f,1f),b2body.getWorldCenter(),true);
            if(Gdx.app.getType() == Application.ApplicationType.Desktop) {
                sfs.loadSound("audio/sounds/getting-hit.wav");
                long id = sfs.sound.play();
                if (sfs.getSoundVolume() != 0) {
                    sfs.sound.setVolume(id, sfs.getSoundVolume());
                } else {
                    sfs.sound.setVolume(id, 0);
                }
            }
            if(Gdx.app.getType() == Application.ApplicationType.Android) {
                sfs.manager.get("audio/sounds/getting-hit.wav", Sound.class).play(sfs.getSoundVolume());
            }

            hitCounter++;
        }
        else{   //Ryus death
            sfs.music.stop();
            if(Gdx.app.getType() == Application.ApplicationType.Desktop) {
                sfs.loadSound("audio/sounds/sexynakedbunny-ouch.mp3");
                long id = sfs.sound.play();
                if (sfs.getSoundVolume() != 0) {
                    sfs.sound.setVolume(id, sfs.getSoundVolume());
                } else {
                    sfs.sound.setVolume(id, 0);
                }
            }
            if(Gdx.app.getType() == Application.ApplicationType.Android) {
                sfs.manager.get("audio/sounds/sexynakedbunny-ouch.mp3", Sound.class).play(sfs.getSoundVolume());
            }

            ryuIsDead = true;
            Filter filter = new Filter();
            filter.maskBits = shootForSurvival.GROUND_BIT|shootForSurvival.PLATFORM_BIT;
            for (Fixture fixture : b2body.getFixtureList())
                fixture.setFilterData(filter);
            b2body.applyLinearImpulse(new Vector2(-1f,2f), b2body.getWorldCenter(), true);
            hitCounter = 3;
        }
    }

    public boolean isDead(){
        return ryuIsDead;
    }

    public float getStateTimer(){
        return stateTimer;
    }

    public static int getHitCounter(){
        return hitCounter;
    }

    public static void setHitCounter(int resetHits){
        hitCounter = resetHits;
    }

    public void reverseVelocity(boolean x, boolean y){
        if(x){
            limit.x = -limit.x;
        }
        if(y) {
            limit.y = -limit.y;
        }
    }
}
