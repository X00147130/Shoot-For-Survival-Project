package com.mygdx.sfs.Sprites.Enemies.Bosses;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.utils.Array;
import com.mygdx.sfs.Scenes.Screens.PlayScreen;
import com.mygdx.sfs.Sprites.Enemies.Enemy;
import com.mygdx.sfs.shootForSurvival;

public class Scalper extends Enemy {
    //animation variables
    public enum State{RUNNING, ATTACK, DEAD }
    public State currentState;
    public State previousState;
    private boolean scalperDead;
    private boolean attacking;

    private shootForSurvival sfs;

    private float stateTime;
    private Animation<TextureRegion> walkAnimation;
    private Animation<TextureRegion> attackAnimation;
    private Animation<TextureRegion> dieAnimation;
    private Array<TextureRegion> frames;
    private boolean setToDestroy;
    private boolean destroyed;
    private boolean runningRight;
    private boolean hit = false;
    private int hitCounter;

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

        //attack animation
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
        scalperDead = false;
        runningRight = true;
    }

    public State getState() {
        if(scalperDead == true) {
            return State.DEAD;
        }

        else if(attacking == true) {
            return State.ATTACK;
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
            scalperDead = true;
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
    public void shot() {
        if(hitCounter < 9){    //Grunt is pushed back
            hit = true;
            if(b2body.getLinearVelocity().x > 0)
                b2body.applyLinearImpulse(new Vector2(-1f,1f),b2body.getWorldCenter(),true);

            else if(b2body.getLinearVelocity().x < 0)
                b2body.applyLinearImpulse(new Vector2(1f,1f),b2body.getWorldCenter(),true);

            else{
                b2body.applyLinearImpulse(new Vector2(-1f,1f),b2body.getWorldCenter(),true);
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
        }else {

            setToDestroy = true;
            if (Gdx.app.getType() == Application.ApplicationType.Desktop) {
                sfs.loadSound("audio/sounds/sexynakedbunny-ouch.mp3");
                long id = sfs.sound.play();
                if (sfs.getSoundVolume() != 0) {
                    sfs.sound.setVolume(id, sfs.getSoundVolume());
                } else {
                    sfs.sound.setVolume(id, 0);
                }
            }

            if (Gdx.app.getType() == Application.ApplicationType.Android) {
                sfs.manager.get("audio/sounds/sexynakedbunny-ouch.mp3", Sound.class).play(sfs.getSoundVolume());
            }
            hitCounter = 10;
        }
    }
}
