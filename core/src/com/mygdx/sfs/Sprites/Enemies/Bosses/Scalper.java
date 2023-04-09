package com.mygdx.sfs.Sprites.Enemies.Bosses;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.utils.Array;
import com.mygdx.sfs.Screens.PlayScreen;
import com.mygdx.sfs.Sprites.Enemies.Enemy;
import com.mygdx.sfs.Sprites.Enemies.Ninja;
import com.mygdx.sfs.shootForSurvival;

public class Scalper extends Enemy {
    //animation variables
    public enum State{RUNNING, DEAD }
    public Ninja.State currentState;
    public Ninja.State previousState;
    private boolean ninjaDead;

    private shootForSurvival sfs;

    private float stateTime;
    private Animation<TextureRegion> walkAnimation;
    private Animation<TextureRegion> dieAnimation;
    private Array<TextureRegion> frames;
    private boolean setToDestroy;
    private boolean destroyed;
    private boolean runningRight;

    private int enemyHitCounter;

    public Scalper(shootForSurvival scalper, PlayScreen screen, float x, float y) {
        super(screen, x, y);
        this.sfs = scalper;

        //run animation
        frames = new Array<TextureRegion>();
        frames.add(scalper.getCyborgAtlas().findRegion("Run1"));
        frames.add(scalper.getCyborgAtlas().findRegion("Run2"));
        frames.add(scalper.getCyborgAtlas().findRegion("Run3"));
        frames.add(scalper.getCyborgAtlas().findRegion("Run4"));
        frames.add(scalper.getCyborgAtlas().findRegion("Run5"));
        frames.add(scalper.getCyborgAtlas().findRegion("Run6"));

        walkAnimation = new Animation <TextureRegion>(0.3f, frames);
        frames.clear();

        //death animation
        frames.clear();

        frames.add(scalper.getCyborgAtlas().findRegion("Die1"));
        frames.add(scalper.getCyborgAtlas().findRegion("Die2"));
        frames.add(scalper.getCyborgAtlas().findRegion("Die3"));
        frames.add(scalper.getCyborgAtlas().findRegion("Die4"));
        frames.add(scalper.getCyborgAtlas().findRegion("Die5"));
        frames.add(scalper.getCyborgAtlas().findRegion("Die6"));

        dieAnimation = new Animation <TextureRegion>(0.1f, frames);
        frames.clear();

        stateTime = 0;
        setBounds(getX(), getY(), 28 / shootForSurvival.PPM , 30 / shootForSurvival.PPM);
        setToDestroy = false;
        destroyed =false;
        enemyHitCounter = 0;
        ninjaDead = false;
        runningRight = true;
    }

    public Ninja.State getState() {
        if(ninjaDead == true) {
            return Ninja.State.DEAD;
        }
        else {
            return Ninja.State.RUNNING;
        }
    }

    public TextureRegion getFrame(float dt) {
        currentState = getState();

        TextureRegion region;

        switch (currentState) {
            case DEAD:
                region = dieAnimation.getKeyFrame(stateTime, false);
                break;

            case RUNNING:

            default:
                region = walkAnimation.getKeyFrame(stateTime,true);
                break;
        }
        if ((b2body.getLinearVelocity().x < 0 || !runningRight) && !region.isFlipX()) {
            region.flip(true, false);
            runningRight = false;

        } else if ((b2body.getLinearVelocity().x > 0 || runningRight) && region.isFlipX()) {
            region.flip(true, false);
            runningRight = true;
        }
        stateTime = currentState == previousState ? stateTime + dt : 0;
        previousState = currentState;
        return region;
    }


    public void update(float dt) {
        stateTime += dt;
        setRegion(getFrame(dt));
        if (setToDestroy && !destroyed) {
            ninjaDead = true;
            world.destroyBody(b2body);
            destroyed = true;
            stateTime=0;


        } else if (!destroyed) {
            b2body.setLinearVelocity(velocity);
            setPosition(b2body.getPosition().x - getWidth() /2 , b2body.getPosition().y - getHeight() /3 );
            setRegion(getFrame(dt));
        }
    }

    @Override
    protected void defineEnemy() {
        BodyDef bdef = new BodyDef();
        bdef.position.set(getX(),getY());
        bdef.type = BodyDef.BodyType.DynamicBody;
        b2body = world.createBody(bdef);

        FixtureDef fdef = new FixtureDef();
        CircleShape shape = new CircleShape();
        shape.setRadius(4 / shootForSurvival.PPM);
        fdef.filter.categoryBits = shootForSurvival.BOSS_BIT;
        fdef.filter.maskBits = shootForSurvival.GROUND_BIT |
                shootForSurvival.PLATFORM_BIT |
                shootForSurvival.BULLET_BIT|
                shootForSurvival.PLAYER_BIT;

        fdef.shape = shape;
        b2body.createFixture(fdef).setUserData(this);
    }

    public void draw(Batch batch){
        if(!destroyed || stateTime < 1)
            super.draw(batch);
    }

    @Override
    public void attacked() {
        setToDestroy = true;
        if(Gdx.app.getType() == Application.ApplicationType.Desktop) {
            sfs.loadSound("audio/sounds/stomp.wav");
            long id = sfs.sound.play();
            if (sfs.getSoundVolume() != 0) {
                sfs.sound.setVolume(id, sfs.getSoundVolume());
            } else {
                sfs.sound.setVolume(id, 0);
            }
        }

        if(Gdx.app.getType() == Application.ApplicationType.Android) {
            sfs.manager.get("audio/sounds/stomp.wav", Sound.class).play(sfs.getSoundVolume());
        }
    }
}
