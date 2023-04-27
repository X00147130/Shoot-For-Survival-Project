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
import com.mygdx.sfs.Scenes.Screens.PlayScreen;
import com.mygdx.sfs.shootForSurvival;

public class Player extends Sprite {
    //State Variables for animation purposes
    public enum State{ FALLING, JUMPING, DOUBLEJUMP, STANDING, RUNNING, DEAD, COMPLETE}
    public State currentState;
    public State previousState;


    //Basic variables
    public World world;
    private PlayScreen screen;
    private shootForSurvival sfs;
    public Body b2body;


    //Animation Variables
    private Animation <TextureRegion> playerStand;
    private Animation <TextureRegion> playerRun;
    private Animation <TextureRegion> playerJump;
    private Animation <TextureRegion> playerDjump;
    private Animation <TextureRegion> playerDead;
    private Animation <TextureRegion> playerComplete;
    private boolean runningRight;
    private float stateTimer;


    //boolean tests
    private boolean playerIsDead;
    private boolean hit = false;


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
    private boolean dash = false;

    public Player(PlayScreen screen, shootForSurvival sfs) {
        this.world = screen.getWorld();
        definePlayer();
        this.sfs = sfs;

        limit = new Vector2(0, 0);

        this.screen = screen;

/*initialising health variables*/
        health = 100;
        damage = 50;
        hitCounter = 0;


/*Animation variables initialization*/
        currentState = State.STANDING;
        previousState = State.STANDING;
        stateTimer = 0;
        runningRight = true;

/*Standing Animation*/

        Array<TextureRegion> frames = new Array<TextureRegion>();
        frames.clear();

        frames.add(sfs.getPlayersChoice().findRegion("idle1"));
        frames.add(sfs.getPlayersChoice().findRegion("idle2"));
        frames.add(sfs.getPlayersChoice().findRegion("idle3"));
        frames.add(sfs.getPlayersChoice().findRegion("idle4"));

        playerStand = new Animation<TextureRegion>(0.3f, frames, Animation.PlayMode.LOOP);

        frames.clear();
/*Running Animation*/
        frames.clear();

        frames.add(sfs.getPlayersChoice().findRegion("run1"));
        frames.add(sfs.getPlayersChoice().findRegion("run2"));
        frames.add(sfs.getPlayersChoice().findRegion("run3"));
        frames.add(sfs.getPlayersChoice().findRegion("run4"));
        frames.add(sfs.getPlayersChoice().findRegion("run5"));
        frames.add(sfs.getPlayersChoice().findRegion("run6"));

        playerRun = new Animation<TextureRegion>(0.2f, frames);
        setBounds(0, 0, 18 / PPM, 20 / PPM);

        frames.clear();

/*Jump Animation*/
        frames.clear();

        frames.add(sfs.getPlayersChoice().findRegion("jump1"));
        frames.add(sfs.getPlayersChoice().findRegion("jump2"));
        frames.add(sfs.getPlayersChoice().findRegion("jump3"));
        frames.add(sfs.getPlayersChoice().findRegion("jump4"));

        playerJump = new Animation<TextureRegion>(2f, frames);

        frames.clear();


/*Players Double Jump animation*/
        frames.clear();

        frames.add(sfs.getPlayersChoice().findRegion("Djump1"));
        frames.add(sfs.getPlayersChoice().findRegion("Djump2"));
        frames.add(sfs.getPlayersChoice().findRegion("Djump3"));
        frames.add(sfs.getPlayersChoice().findRegion("Djump4"));
        frames.add(sfs.getPlayersChoice().findRegion("Djump5"));
        frames.add(sfs.getPlayersChoice().findRegion("Djump6"));

        playerDjump = new Animation<TextureRegion>(1f, frames);

        frames.clear();
/*Player death animation*/

        frames.add(sfs.getPlayersChoice().findRegion("Die1"));
        frames.add(sfs.getPlayersChoice().findRegion("Die2"));
        frames.add(sfs.getPlayersChoice().findRegion("Die3"));
        frames.add(sfs.getPlayersChoice().findRegion("Die4"));
        frames.add(sfs.getPlayersChoice().findRegion("Die5"));
        frames.add(sfs.getPlayersChoice().findRegion("Die6"));

        playerDead = new Animation<TextureRegion>(0.3f, frames);

        frames.clear();


/*Level Complete Animation*/
        /*frames.add(sfs.getPlayersChoice().findRegion("Happy1"));*/
        frames.add(sfs.getPlayersChoice().findRegion("Happy2"));
        frames.add(sfs.getPlayersChoice().findRegion("Happy3"));
        frames.add(sfs.getPlayersChoice().findRegion("Happy4"));
        frames.add(sfs.getPlayersChoice().findRegion("Happy5"));
        frames.add(sfs.getPlayersChoice().findRegion("Happy6"));
        playerComplete = new Animation<TextureRegion>(0.2f, frames);

        frames.clear();

        setBounds(getX(),getY(),30 / sfs.PPM, 30 / sfs.PPM);

    }

    public void update(float dt){
        if(Gdx.app.getType() == Application.ApplicationType.Android)
            setPosition(b2body.getPosition().x - getWidth() /2, b2body.getPosition().y - getHeight() /2);
        setPosition(b2body.getPosition().x - getWidth() /2, b2body.getPosition().y - getHeight() /3);
        setRegion(getFrame(dt));
        isDead();
        if(getY() < 0){
            sfs.music.stop();
            playerIsDead = true;
            b2body.applyLinearImpulse(new Vector2(0, 20f), b2body.getWorldCenter(), true);
        }
    }

    public TextureRegion getFrame(float dt){
        currentState = getState();

        TextureRegion region;


        switch(currentState){
           case DEAD:
                region = playerDead.getKeyFrame(stateTimer, false);
            break;

            case JUMPING:
                region =  playerJump.getKeyFrame(stateTimer, false);
                break;

            case DOUBLEJUMP:
                region =  playerDjump.getKeyFrame(stateTimer, false);
                break;

            case RUNNING:
                region = playerRun.getKeyFrame(stateTimer, true);
               break;


            case COMPLETE:
                region = playerComplete.getKeyFrame(stateTimer, true);
                break;

            case FALLING:

            case STANDING:

            default:
                region = playerStand.getKeyFrame(stateTimer, true);
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
        if(playerIsDead)
            return State.DEAD;

        else if ((b2body.getLinearVelocity().y > 0 && sfs.doubleJumped == true))
            return State.DOUBLEJUMP;

        else if (b2body.getLinearVelocity().y > 0 || (b2body.getLinearVelocity().y < 0))
            return State.JUMPING;

        else if(b2body.getLinearVelocity().y < 0)
            return State.FALLING;

        else if (b2body.getLinearVelocity().x != 0)
            return State.RUNNING;

        else if(screen.complete == true)
            return State.COMPLETE;

        else
            return State.STANDING;
    }


    public void definePlayer(){
        BodyDef bdef = new BodyDef();
        bdef.position.set(20/PPM,600/PPM);
        bdef.type = BodyDef.BodyType.DynamicBody;
        b2body = world.createBody(bdef);

        FixtureDef fdef = new FixtureDef();
        CircleShape shape = new CircleShape();
        shape.setRadius(7 / PPM);
        fdef.filter.categoryBits = shootForSurvival.PLAYER_BIT;
        fdef.filter.maskBits = shootForSurvival.GROUND_BIT |
                shootForSurvival.FINISH_BIT |
                shootForSurvival.ENEMY_BIT|
                shootForSurvival.MONEY_BIT|
                shootForSurvival.SKY_BIT|
                shootForSurvival.ITEM_BIT|
                shootForSurvival.KEY_BIT|
                shootForSurvival.BOSS_BIT|
                shootForSurvival.DEATH_BIT;

        fdef.shape = shape;
        fdef.restitution = 0f;
        fdef.friction = 2f;
        b2body.setGravityScale(1.1f);
        b2body.createFixture(fdef).setUserData(this);


    //Player Head(Used for colliding with bricks
        EdgeShape head = new EdgeShape();
        head.set(new Vector2(-2 / PPM, 6 / PPM), new Vector2(2 / PPM, 6 / PPM));
        fdef.filter.categoryBits=shootForSurvival.PLAYER_HEAD_BIT;
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
        else{
            b2body.applyLinearImpulse(new Vector2(-3f,0),new Vector2(0.1f,0), true);
        }
        b2body.setAwake(true);
    }


    //getting hit method
    public void hit(){

        if(hitCounter < 2){    //Player is pushed back and says ow
            hit = true;
            if(b2body.getLinearVelocity().x > 0)
                b2body.applyLinearImpulse(new Vector2(-3f,2f),b2body.getWorldCenter(),true);

            else if(b2body.getLinearVelocity().x < 0)
                b2body.applyLinearImpulse(new Vector2(3f,2f),b2body.getWorldCenter(),true);

            else{
                b2body.applyLinearImpulse(new Vector2(-3f,2f),b2body.getWorldCenter(),true);
            }

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
        else{   //Player death
            sfs.music.stop();
            if(Gdx.app.getType() == Application.ApplicationType.Desktop) {
                sfs.loadSound("audio/sounds/death.wav");
                long id = sfs.sound.play();
                if (sfs.getSoundVolume() != 0) {
                    sfs.sound.setVolume(id, sfs.getSoundVolume());
                } else {
                    sfs.sound.setVolume(id, 0);
                }
            }
            if(Gdx.app.getType() == Application.ApplicationType.Android) {
                sfs.manager.get("audio/sounds/death.wav", Sound.class).play(sfs.getSoundVolume());
            }

            playerIsDead = true;
            Filter filter = new Filter();
            filter.maskBits = shootForSurvival.GROUND_BIT|
            shootForSurvival.DEATH_BIT;
            for (Fixture fixture : b2body.getFixtureList())
                fixture.setFilterData(filter);
            b2body.applyLinearImpulse(new Vector2(-1f,2f), b2body.getWorldCenter(), true);
            hitCounter = 3;
        }
    }

    public boolean isDead(){
        return playerIsDead;
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

    public void setDash(boolean dash) {
        this.dash = dash;
    }

    public boolean isDash() {
        return this.dash;
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
