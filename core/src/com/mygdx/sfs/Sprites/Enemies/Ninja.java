package com.mygdx.sfs.Sprites.Enemies;

import static com.mygdx.sfs.shootForSurvival.PPM;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.utils.Array;
import com.mygdx.sfs.Screens.PlayScreen;
import com.mygdx.sfs.Sprites.Items.Bullets;
import com.mygdx.sfs.shootForSurvival;

import java.util.ArrayList;

public class Ninja extends Enemy {
    //animation variables
    public enum State{RUNNING, DEAD }
    public State currentState;
    public State previousState;
    private boolean ninjaDead;

    private shootForSurvival sfs;

    private float stateTime;
    private Animation<TextureRegion> walkAnimation;
    private Animation<TextureRegion> dieAnimation;
    private Array<TextureRegion> frames;
    private boolean setToDestroy;
    private boolean destroyed;
    private boolean runningRight;

    //private ArrayList<Bullets> bullet;

    private int enemyHitCounter;

    public Ninja(shootForSurvival sfs, PlayScreen screen, float x, float y) {
        super(screen, x, y);
        this.sfs = sfs;

        //bullet.add(new Bullets(sfs, screen, b2body.getPosition().x, b2body.getPosition().y));

        //run animation
        frames = new Array<TextureRegion>();
        frames.add(screen.getCyborgAtlas().findRegion("Idle1"));


        walkAnimation = new Animation<TextureRegion>(0.1f, frames);
        setBounds(0,0,18/PPM, 20/PPM);
        frames.clear();

        //death animation
        frames.clear();

        frames.add(screen.getCyborgAtlas().findRegion("enemyDie1"));
        frames.add(screen.getCyborgAtlas().findRegion("enemyDie2"));
        frames.add(screen.getCyborgAtlas().findRegion("enemyDie3"));
        frames.add(screen.getCyborgAtlas().findRegion("enemyDie4"));
        frames.add(screen.getCyborgAtlas().findRegion("enemyDie5"));
        frames.add(screen.getCyborgAtlas().findRegion("enemyDie6"));
        frames.add(screen.getCyborgAtlas().findRegion("enemyDie7"));

        dieAnimation = new Animation <TextureRegion>(0.1f, frames);
        frames.clear();

        stateTime = 0;
        setBounds(getX(), getY(), 26 / PPM , 26 / PPM);
        setToDestroy = false;
        destroyed =false;
        enemyHitCounter = 0;
        ninjaDead = false;
        runningRight = true;
    }

    public State getState() {
        if(ninjaDead == true) {
            return State.DEAD;
        }
        else {
            return State.RUNNING;
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

        /*for(Bullets bullets: bullet) {
            if (!destroyed && (screen.getPlayer().b2body.getPosition().x < (b2body.getPosition().x - 9))) {
                bullet.add(new Bullets(sfs, screen, b2body.getPosition().x, b2body.getPosition().y));
                bullets.update(dt);
            }
        }*/
    }

    @Override
    protected void defineEnemy() {
        BodyDef bdef = new BodyDef();
        bdef.position.set(getX(),getY());
        bdef.type = BodyDef.BodyType.DynamicBody;
        b2body = world.createBody(bdef);

        FixtureDef fdef = new FixtureDef();
        CircleShape shape = new CircleShape();
        shape.setRadius(4 / PPM);
        fdef.filter.categoryBits = shootForSurvival.ENEMY_BIT;
        fdef.filter.maskBits = shootForSurvival.GROUND_BIT |
                shootForSurvival.FINISH_BIT |
                shootForSurvival.PLATFORM_BIT |
                shootForSurvival.ENEMY_BIT |
                shootForSurvival.BARRIER_BIT |
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

