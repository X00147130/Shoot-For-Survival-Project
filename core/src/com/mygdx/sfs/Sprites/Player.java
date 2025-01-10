package com.mygdx.sfs.Sprites;

import static com.mygdx.sfs.shootForSurvival.PPM;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
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
    public enum State{ FALLING, JUMPING, DOUBLEJUMP, STANDING, RUNNING, DEAD, COMPLETE, INTERACT}
    public State currentState;
    public State previousState;
    private boolean animationTriggered = false;


    //Basic variables
    public World world;
    private PlayScreen screen;
    private shootForSurvival sfs;
    public Body b2body;
    private Boolean key = false;


    //Animation Variables
    private Animation <TextureRegion> playerStand;
    private Animation <TextureRegion> playerRun;
    private Animation <TextureRegion> playerJump;
    private Animation <TextureRegion> playerDjump;
    private Animation <TextureRegion> playerDead;
    private Animation <TextureRegion> playerComplete;
    private Animation <TextureRegion> playerInteract;
    private Animation <TextureRegion> playerStand2;
    private Animation <TextureRegion> playerRun2;
    private Animation <TextureRegion> playerJump2;
    private Animation <TextureRegion> playerDjump2;
    private Animation <TextureRegion> playerDead2;
    private Animation <TextureRegion> playerComplete2;
    private Animation <TextureRegion> playerInteract2;


    private boolean runningRight;
    private float stateTimer;
    private boolean rifle = false;
    private TextureAtlas upgradedRifle;

    private TextureAtlas pistolUpgrade;
    private int powerLVL;

    private int pistolLvl;


    //boolean tests
    private boolean playerIsDead;
    private boolean fellToDeath;
    private boolean hit = false;
    private boolean scannerTouched = false;


    //health variables
    private int health;
    private boolean healthCrate;
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

        upgradedRifle = sfs.getRifleChoice();

/*initialising health variables*/
        health = 100;
        damage = 50;
        hitCounter = 0;
        healthCrate = false;


/*Animation variables initialization*/
        currentState = State.STANDING;
        previousState = State.STANDING;
        stateTimer = 0;
        runningRight = true;
        powerLVL = sfs.getPowerLVL();
        pistolLvl = sfs.getPistolLvl();

        pistolUpgrade = sfs.getPlayersChoice();
        upgradedRifle = sfs.getRifleChoice();


/*Standing Animation*/
        Array<TextureRegion> frames = new Array<TextureRegion>();
        Array<TextureRegion> frames2 = new Array<TextureRegion>();

        frames.clear();
        frames2.clear();

        for(int i = 1; i < 5; i++) {
            frames.add(pistolUpgrade.findRegion("idle" + i));
            frames2.add(upgradedRifle.findRegion("idle" + i));
        }
        playerStand = new Animation<TextureRegion>(0.3f, frames, Animation.PlayMode.LOOP);
        playerStand2 = new Animation<TextureRegion>(0.3f, frames2, Animation.PlayMode.LOOP);


        setBounds(getX(),getY(),32 / sfs.PPM, 35 / sfs.PPM);

        frames.clear();
        frames2.clear();


/*Running Animation*/
        frames.clear();
        frames2.clear();

        for(int r = 1; r < 7; r++) {
            frames.add(pistolUpgrade.findRegion("run" + r));
            frames2.add(upgradedRifle.findRegion("run" + r));

        }
        playerRun = new Animation<TextureRegion>(0.2f, frames, Animation.PlayMode.LOOP);
        playerRun2 = new Animation<TextureRegion>(0.2f, frames2, Animation.PlayMode.LOOP);


        setBounds(getX(),getY(),32 / sfs.PPM, 35 / sfs.PPM);

        frames.clear();
        frames2.clear();


/*Jump Animation*/
        frames.clear();
        frames2.clear();

        for(int j = 1; j < 5; j++) {
            frames.add(pistolUpgrade.findRegion("jump" + j));
            frames2.add(upgradedRifle.findRegion("jump" + j));
        }
        playerJump = new Animation<TextureRegion>(0.3f, frames, Animation.PlayMode.NORMAL);
        playerJump2 = new Animation<TextureRegion>(0.3f, frames2, Animation.PlayMode.NORMAL);


        setBounds(getX(),getY(),32 / sfs.PPM, 35 / sfs.PPM);

        frames.clear();
        frames2.clear();


/*Players Double Jump animation*/
        frames.clear();
        frames2.clear();


        for(int dj = 1; dj < 7; dj++) {
            frames.add(pistolUpgrade.findRegion("Djump" + dj));
            frames2.add(upgradedRifle.findRegion("Djump" + dj));
        }
        playerDjump = new Animation<TextureRegion>(0.3f, frames, Animation.PlayMode.NORMAL);
        playerDjump2 = new Animation<TextureRegion>(0.3f, frames2, Animation.PlayMode.NORMAL);


        setBounds(getX(),getY(),32 / sfs.PPM, 35 / sfs.PPM);

        frames.clear();
        frames2.clear();


/*Player death animation*/

        for(int d = 1; d < 7; d++) {
            frames.add(pistolUpgrade.findRegion("die" + d));
            frames2.add(upgradedRifle.findRegion("die" + d));
        }
        playerDead = new Animation<TextureRegion>(0.3f, frames, Animation.PlayMode.NORMAL);
        playerDead2 = new Animation<TextureRegion>(0.3f, frames2, Animation.PlayMode.NORMAL);


        setBounds(getX(),getY(),32 / sfs.PPM, 35 / sfs.PPM);

        frames.clear();
        frames2.clear();


/*Level Complete Animation*/
        frames.clear();
        frames2.clear();

        for(int e = 1; e < 5; e++) {
            frames.add(pistolUpgrade.findRegion("Enter" + e));
            frames2.add(upgradedRifle.findRegion("Enter" + e));
        }
        playerComplete = new Animation<TextureRegion>(0.2f, frames, Animation.PlayMode.NORMAL);
        playerComplete2 = new Animation<TextureRegion>(0.2f, frames2, Animation.PlayMode.NORMAL);


        setBounds(getX(),getY(),32 / sfs.PPM, 35 / sfs.PPM);

        frames.clear();
        frames2.clear();


/*Interact Animation*/
        frames.clear();
        frames2.clear();

        for(int in = 1; in < 7; in++) {
            frames.add(pistolUpgrade.findRegion("Interact" + in));
            frames2.add(upgradedRifle.findRegion("Interact" + in));
        }
        playerInteract = new Animation<TextureRegion>(0.2f, frames, Animation.PlayMode.NORMAL);
        playerInteract2 = new Animation<TextureRegion>(0.2f, frames, Animation.PlayMode.NORMAL);


        setBounds(getX(),getY(),32 / sfs.PPM, 35 / sfs.PPM);

        frames.clear();
        frames2.clear();

    }

    public void update(float dt) {
        if (Gdx.app.getType() == Application.ApplicationType.Android)
            setPosition(b2body.getPosition().x - getWidth() / 2, b2body.getPosition().y - getHeight() / 2);

        //Pistol Read in
        if (pistolLvl == 1) {
            pistolUpgrade = sfs.getPlayersChoice();
            renderUpgrade();
        }

        else if (pistolLvl == 2) {
            pistolUpgrade = sfs.getPlayersChoice2();
            renderUpgrade();
        }
        else if (pistolLvl == 3) {
            pistolUpgrade = sfs.getPlayersChoice3();
            renderUpgrade();
        }
        else if (pistolLvl == 4) {
            pistolUpgrade = sfs.getPlayersChoice4();
            renderUpgrade();
        }
        else if (pistolLvl == 5) {
            pistolUpgrade = sfs.getPlayersChoice5();
            renderUpgrade();
        }
        else if (pistolLvl == 6) {
            pistolUpgrade = sfs.getPlayersChoice6();
            renderUpgrade();
        }
        else if (pistolLvl == 7) {
           pistolUpgrade = sfs.getPlayersChoice7();
            renderUpgrade();
        }
        else if (pistolLvl == 8) {
            pistolUpgrade = sfs.getPlayersChoice8();
            renderUpgrade();
        }
        else if (pistolLvl == 9) {
           pistolUpgrade = sfs.getPlayersChoice9();
            renderUpgrade();
        }
        else if (pistolLvl == 10) {
            pistolUpgrade = sfs.getPlayersChoice10();
            renderUpgrade();
        }


//Rifle Read in
        if (powerLVL == 1) {
            upgradedRifle = sfs.getRifleChoice();
            renderUpgrade();
        }

        else if (powerLVL == 2) {
            upgradedRifle = sfs.getRifleChoice2();
            renderUpgrade();
        }
        else if (powerLVL == 3) {
            upgradedRifle = sfs.getRifleChoice3();
            renderUpgrade();
        }
        else if (powerLVL == 4) {
            upgradedRifle = sfs.getRifleChoice4();
            renderUpgrade();
        }
        else if (powerLVL == 5) {
            upgradedRifle = sfs.getRifleChoice5();
            renderUpgrade();
        }
        else if (powerLVL == 6) {
            upgradedRifle = sfs.getRifleChoice6();
            renderUpgrade();
        }
        else if (powerLVL == 7) {
            upgradedRifle = sfs.getRifleChoice7();
            renderUpgrade();
        }
        else if (powerLVL == 8) {
            upgradedRifle = sfs.getRifleChoice8();
            renderUpgrade();
        }
        else if (powerLVL == 9) {
            upgradedRifle = sfs.getRifleChoice9();
            renderUpgrade();
        }
        else if (powerLVL == 10) {
            upgradedRifle = sfs.getRifleChoice10();
            renderUpgrade();
        }

        else if (powerLVL == 11) {
            sfs.setPowerLVL(1);
            upgradedRifle =sfs.getRifleChoice();
            renderUpgrade();
        }




        setPosition(b2body.getPosition().x - getWidth() /2, b2body.getPosition().y - getHeight() /3);
        setRegion(getFrame(dt));
        isDead();

        powerLVL = sfs.getPowerLVL();

        if(getY() < 0){
            sfs.music.stop();
            playerIsDead = true;
            b2body.applyLinearImpulse(new Vector2(0, 20f), b2body.getWorldCenter(), true);
        }

    }


    public TextureRegion getFrame(float dt) {
        currentState = getState();

        TextureRegion region = new TextureRegion();

        if (!rifle) {
            switch (currentState) {
                case DEAD:
                    region = playerDead.getKeyFrame(stateTimer, false);
                    break;

                case JUMPING:
                    region = playerJump.getKeyFrame(stateTimer, false);
                    break;

                case DOUBLEJUMP:
                    region = playerDjump.getKeyFrame(stateTimer, false);
                    break;

                case RUNNING:
                    region = playerRun.getKeyFrame(stateTimer, true);
                    break;

                case COMPLETE:
                    region = playerComplete.getKeyFrame(stateTimer, false);
                    break;

                case INTERACT:
                    if(animationTriggered) {
                        key = false;
                        stateTimer = 0;
                        region = playerInteract.getKeyFrame(stateTimer, false);
                        if(playerInteract.isAnimationFinished(stateTimer))
                            animationTriggered = false;
                    }
                    break;

                case FALLING:

                case STANDING:

                default:
                    region = playerStand.getKeyFrame(stateTimer, true);
                    break;
            }
        }


        else if (rifle) {
            switch (currentState) {
                case DEAD:
                    region = playerDead2.getKeyFrame(stateTimer, false);
                    break;

                case JUMPING:
                    region = playerJump2.getKeyFrame(stateTimer, false);
                    break;

                case DOUBLEJUMP:
                    region = playerDjump2.getKeyFrame(stateTimer, false);
                    break;

                case RUNNING:
                    region = playerRun2.getKeyFrame(stateTimer, true);
                    break;

                case COMPLETE:
                    region = playerComplete2.getKeyFrame(stateTimer, false);
                    break;

                case INTERACT:
                    if(animationTriggered) {
                        key = false;
                        stateTimer = 0;
                        region = playerInteract.getKeyFrame(stateTimer, false);
                        if(playerInteract2.isAnimationFinished(stateTimer))
                            animationTriggered = false;
                    }
                    break;

                case FALLING:

                case STANDING:

                default:
                    region = playerStand2.getKeyFrame(stateTimer, true);
                    break;
            }
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

    public State getState() {
        State state;
        if (playerIsDead)
            return State.DEAD;

        else if ((b2body.getLinearVelocity().y > 0 && sfs.doubleJumped) && stateTimer < 1.8f)
            return State.DOUBLEJUMP;

        else if (b2body.getLinearVelocity().y > 0 || (b2body.getLinearVelocity().y < 0) && stateTimer < 1.2f)
            return State.JUMPING;

        else if (b2body.getLinearVelocity().y < 0)
            return State.FALLING;

        else if (b2body.getLinearVelocity().x != 0)
            return State.RUNNING;

        else if (screen.complete)
            return State.COMPLETE;

        else if (getKey() && screen.isInteract()) {
            Gdx.app.log("Interact", "Animation");
            if(stateTimer < 1.3f && previousState != State.INTERACT) {
                state = State.INTERACT;

            }else{
                state = State.STANDING;
            }
            return state;
        }

        else {
            return State.STANDING;
        }
    }


    public void renderUpgrade(){

        //Pistol Upgrade & Rifle upgrade

        Array<TextureRegion> frames = new Array<TextureRegion>();
        Array<TextureRegion> frames2 = new Array<TextureRegion>();

        for(int i = 1; i < 5; i++) {
            frames.add(pistolUpgrade.findRegion("idle" + i));
            frames2.add(upgradedRifle.findRegion("idle" + i));
        }

        playerStand = new Animation<TextureRegion>(0.3f, frames, Animation.PlayMode.LOOP);
        playerStand2 = new Animation<TextureRegion>(0.3f, frames2, Animation.PlayMode.LOOP);
        setBounds(getX(),getY(),32 / sfs.PPM, 35 / sfs.PPM);

        frames.clear();
        frames2.clear();


        /*Running Animation*/
        frames.clear();
        frames2.clear();

        for(int r = 1; r < 7; r++) {
            frames.add(pistolUpgrade.findRegion("run" + r));
            frames2.add(upgradedRifle.findRegion("run" + r));
        }

        playerRun = new Animation<TextureRegion>(0.2f, frames, Animation.PlayMode.LOOP);
        playerRun2 = new Animation<TextureRegion>(0.2f, frames2, Animation.PlayMode.LOOP);
        setBounds(getX(),getY(),32 / sfs.PPM, 35 / sfs.PPM);


        frames.clear();
        frames2.clear();

        /*Jump Animation*/
        frames.clear();
        frames2.clear();

        for(int j = 1; j < 5; j++) {
            frames.add(pistolUpgrade.findRegion("jump" + j));
            frames2.add(upgradedRifle.findRegion("jump" + j));
        }

        playerJump = new Animation<TextureRegion>(2f, frames, Animation.PlayMode.NORMAL);
        playerJump2 = new Animation<TextureRegion>(2f, frames2, Animation.PlayMode.NORMAL);
        setBounds(getX(),getY(),32 / sfs.PPM, 35 / sfs.PPM);

        frames.clear();
        frames2.clear();

        /*Players Double Jump animation*/
        frames.clear();
        frames2.clear();

        for(int dj = 1; dj < 7; dj++) {
            frames.add(pistolUpgrade.findRegion("Djump" + dj));
            frames2.add(upgradedRifle.findRegion("Djump" + dj));
        }

        playerDjump = new Animation<TextureRegion>(1f, frames, Animation.PlayMode.NORMAL);
        playerDjump2 = new Animation<TextureRegion>(1f, frames2, Animation.PlayMode.NORMAL);
        setBounds(getX(),getY(),32 / sfs.PPM, 35 / sfs.PPM);

        frames.clear();
        frames2.clear();

        /*Player death animation*/
        frames.clear();
        frames2.clear();

        for(int d = 1; d < 7; d++) {
            frames.add(pistolUpgrade.findRegion("die" + d));
            frames2.add(upgradedRifle.findRegion("die" + d));
        }

        playerDead = new Animation<TextureRegion>(0.3f, frames, Animation.PlayMode.NORMAL);
        playerDead2 = new Animation<TextureRegion>(0.3f, frames2, Animation.PlayMode.NORMAL);
        setBounds(getX(),getY(),32 / sfs.PPM, 35 / sfs.PPM);

        frames.clear();
        frames2.clear();

        /*Level Complete Animation*/
        frames.clear();
        frames2.clear();

        for(int e = 1; e < 7; e++) {
            frames.add(pistolUpgrade.findRegion("Enter" + e));
            frames2.add(upgradedRifle.findRegion("Enter" + e));
        }
        playerComplete = new Animation<TextureRegion>(0.2f, frames, Animation.PlayMode.NORMAL);
        playerComplete2 = new Animation<TextureRegion>(0.2f, frames2, Animation.PlayMode.NORMAL);
        setBounds(getX(),getY(),32 / sfs.PPM, 35 / sfs.PPM);

        frames.clear();
        frames2.clear();

        /*Interact Animation*/
        frames.clear();
        frames2.clear();

        for(int in = 1; in < 7; in++) {
            frames.add(pistolUpgrade.findRegion("Interact" + in));
            frames2.add(upgradedRifle.findRegion("Interact" + in));
        }
        playerInteract = new Animation<TextureRegion>(0.2f, frames, Animation.PlayMode.NORMAL);
        playerInteract2 = new Animation<TextureRegion>(0.2f, frames2, Animation.PlayMode.NORMAL);
        setBounds(getX(),getY(),32 / sfs.PPM, 35 / sfs.PPM);

        frames.clear();
        frames2.clear();

        setBounds(getX(),getY(),32 / sfs.PPM, 35 / sfs.PPM);
    }


    public void definePlayer(){
        BodyDef bdef = new BodyDef();
        bdef.position.set(20/PPM,615/PPM);
        bdef.type = BodyDef.BodyType.DynamicBody;
        b2body = world.createBody(bdef);

        FixtureDef fdef = new FixtureDef();
        CircleShape shape = new CircleShape();
        shape.setRadius(7 / PPM);
        fdef.filter.categoryBits = shootForSurvival.PLAYER_BIT;
        fdef.filter.maskBits = shootForSurvival.GROUND_BIT |
                shootForSurvival.BOSS_BIT |
                shootForSurvival.ENEMY_BIT|
                shootForSurvival.ITEM_BIT|
                shootForSurvival.WALL_BIT|
                shootForSurvival.DOOR_BIT |
                shootForSurvival.SCANNER_BIT|
                shootForSurvival.BOSS_BIT|
                shootForSurvival.SKY_BIT|
                shootForSurvival.HEALTH_BIT|
                shootForSurvival.DEATH_BIT;

        fdef.shape = shape;
        fdef.restitution = 0f;
        fdef.friction = 2f;
        b2body.setGravityScale(1.1f);
        b2body.createFixture(fdef).setUserData(this);
    }

    /*Jump Counter System*/
    public void jumpReset(){
        sfs.jumpCounter = 0;
        sfs.doubleJumped = false;
        Gdx.app.log("Jumps", "Reset");
    }

    public void wall(){
        if(!isFlipX()){
            b2body.applyLinearImpulse(new Vector2(-0.8f,0),new Vector2(0.1f,0), true);
        }
        else{
            b2body.applyLinearImpulse(new Vector2(0.8f,0),new Vector2(-0.1f,0), true);
        }
        b2body.setAwake(true);
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
                sfs.loadSound("audio/sounds/getting-hit.mp3");
                long id = sfs.sound.play();
                if (sfs.getSoundVolume() != 0) {
                    sfs.sound.setVolume(id, sfs.getSoundVolume());
                } else {
                    sfs.sound.setVolume(id, 0);
                }
            }
            if(Gdx.app.getType() == Application.ApplicationType.Android) {
                sfs.manager.get("audio/sounds/getting-hit.mp3", Sound.class).play(sfs.getSoundVolume());
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

            sfs.setPowerLVL(powerLVL);

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

    public void fellToDeath(){
        if(fellToDeath){
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

            sfs.setPowerLVL(powerLVL);

            playerIsDead = true;
            Filter filter = new Filter();
            filter.maskBits = shootForSurvival.GROUND_BIT|
                    shootForSurvival.DEATH_BIT;
            for (Fixture fixture : b2body.getFixtureList())
                fixture.setFilterData(filter);
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

    public boolean isHealthCrate() {
        return healthCrate;
    }

    public void setHealthCrate(boolean healthCrate) {
        this.healthCrate = healthCrate;
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

    public boolean isRifle() {
        return rifle;
    }

    public void setRifle(boolean rifle) {
        this.rifle = rifle;
    }

    public Boolean getKey() {
        return key;
    }

    public void setKey(Boolean key) {
        this.key = key;
    }

    public void setFellToDeath(boolean fellToDeath) {
        this.fellToDeath = fellToDeath;
    }

    public void setScannerTouched(boolean scannerTouched){
        this.scannerTouched = scannerTouched;
        animationTriggered = true;
    }
}
