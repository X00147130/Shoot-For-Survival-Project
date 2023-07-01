package com.mygdx.sfs.Sprites.Enemies.Grunts;

import static com.mygdx.sfs.shootForSurvival.PPM;

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

public class Hammer extends Enemy {
    //animation variables
    public enum State{ RUNNING, HURT, ATTACK, DEAD }
    public State currentState;
    public State previousState;
    private boolean hammerDead;

    private shootForSurvival sfs;

    private float stateTime;
    private Animation<TextureRegion> runAnimation;
    private Animation<TextureRegion> dieAnimation;
    private Animation<TextureRegion> attackAnimation;
    private Animation<TextureRegion> hurtAnimation;
    private Array<TextureRegion> frames;
    private boolean setToDestroy;
    private boolean destroyed;
    private boolean hit = false;
    private int hitCounter;
    private boolean runningRight;
    private boolean attack = false;

    /*private int enemyHitCounter;*/

    public Hammer(shootForSurvival sfs, PlayScreen screen, float x, float y) {
        super(screen, x, y);
        this.sfs = sfs;

        //Run animation
        frames = new Array<TextureRegion>();
        frames.add(sfs.getHammerAtlas().findRegion("Run1"));
        frames.add(sfs.getHammerAtlas().findRegion("Run2"));
        frames.add(sfs.getHammerAtlas().findRegion("Run3"));
        frames.add(sfs.getHammerAtlas().findRegion("Run4"));
        frames.add(sfs.getHammerAtlas().findRegion("Run5"));
        frames.add(sfs.getHammerAtlas().findRegion("Run6"));


        runAnimation = new Animation<TextureRegion>(1f, frames);
        setBounds(0,0,18/PPM, 20/PPM);
        frames.clear();

        //Death animation
        frames.clear();

        frames.add(sfs.getHammerAtlas().findRegion("Death1"));
        frames.add(sfs.getHammerAtlas().findRegion("Death2"));
        frames.add(sfs.getHammerAtlas().findRegion("Death3"));
        frames.add(sfs.getHammerAtlas().findRegion("Death4"));


        dieAnimation = new Animation <TextureRegion>(0.3f, frames);
        frames.clear();

        //Attack Animation
        frames.clear();

        frames.add(sfs.getHammerAtlas().findRegion("Attack1"));
        frames.add(sfs.getHammerAtlas().findRegion("Attack2"));
        frames.add(sfs.getHammerAtlas().findRegion("Attack3"));
        frames.add(sfs.getHammerAtlas().findRegion("Attack4"));
        frames.add(sfs.getHammerAtlas().findRegion("Attack5"));
        frames.add(sfs.getHammerAtlas().findRegion("Attack6"));
        frames.add(sfs.getHammerAtlas().findRegion("Attack7"));
        frames.add(sfs.getHammerAtlas().findRegion("Attack8"));

        attackAnimation = new Animation <TextureRegion>(1f, frames);
        frames.clear();

        //Hurt Animation
        frames.clear();

        frames.add(sfs.getHammerAtlas().findRegion("Hurt1"));
        frames.add(sfs.getHammerAtlas().findRegion("Hurt2"));

        hurtAnimation = new Animation <TextureRegion>(1f, frames);
        frames.clear();


        stateTime = 0;
        setBounds(getX(), getY(), 30 / PPM , 30 / PPM);
        setToDestroy = false;
        destroyed =false;
        /*enemyHitCounter = 0;*/
        hammerDead = false;
        runningRight = true;
    }

    public State getState() {
        if(hammerDead)
            return State.DEAD;

        else if(hit == true && hammerDead == false)
            return State.HURT;

        else if(attack == true && hammerDead == false)
            return State.ATTACK;

        else if(!hammerDead)
            return State.RUNNING;

        else
            return State.DEAD;

    }

    public TextureRegion getFrame(float dt) {
        currentState = getState();

        TextureRegion region;

        switch (currentState) {
            case DEAD:
                region = dieAnimation.getKeyFrame(stateTime, false);
                break;

            case HURT:
                region = hurtAnimation.getKeyFrame(stateTime, false);
                break;

            case ATTACK:
                region = attackAnimation.getKeyFrame(stateTime, true);
                break;

            case RUNNING:

            default:
                region = runAnimation.getKeyFrame(stateTime,true);
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
            hammerDead = true;
            world.destroyBody(b2body);
            destroyed = true;
            stateTime=0;


        } else if (!destroyed) {
            hammerDead = false;
            if(!attack)
                b2body.setLinearVelocity(velocity);
            else if (attack) {
                b2body.setLinearVelocity(0, 0);
                attack = false;
            }

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
        shape.setRadius(9 / PPM);
        fdef.filter.categoryBits = shootForSurvival.HAMMER_BIT;
        fdef.filter.maskBits = shootForSurvival.GROUND_BIT |
                shootForSurvival.DOOR_BIT |
                shootForSurvival.ENEMY_BIT |
                shootForSurvival.BARRIER_BIT |
                shootForSurvival.BULLET_BIT|
                shootForSurvival.WALL_BIT|
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
        if(hitCounter < 3){    //Hammer is pushed back
            hit = true;
            if(b2body.getLinearVelocity().x > 0)
                b2body.applyLinearImpulse(new Vector2(-1f,1f),b2body.getWorldCenter(),true);

            else if(b2body.getLinearVelocity().x < 0)
                b2body.applyLinearImpulse(new Vector2(1f,1f),b2body.getWorldCenter(),true);

            else{
                b2body.applyLinearImpulse(new Vector2(-1f,1f),b2body.getWorldCenter(),true);
            }

            if(Gdx.app.getType() == Application.ApplicationType.Desktop) {
                sfs.loadSound("audio/sounds/394499__mobeyee__hurting-the-robot.wav");
                long id = sfs.sound.play();
                if (sfs.getSoundVolume() != 0) {
                    sfs.sound.setVolume(id, sfs.getSoundVolume());
                } else {
                    sfs.sound.setVolume(id, 0);
                }
            }
            if(Gdx.app.getType() == Application.ApplicationType.Android) {
                sfs.manager.get("audio/sounds/394499__mobeyee__hurting-the-robot.wav", Sound.class).play(sfs.getSoundVolume());
            }

            hitCounter++;
        }
        else {

            setToDestroy = true;
            if (Gdx.app.getType() == Application.ApplicationType.Desktop) {
                sfs.loadSound("audio/sounds/523553__matrixxx__tv_shutdown.wav");
                long id = sfs.sound.play();
                if (sfs.getSoundVolume() != 0) {
                    sfs.sound.setVolume(id, sfs.getSoundVolume());
                } else {
                    sfs.sound.setVolume(id, 0);
                }
            }

            if (Gdx.app.getType() == Application.ApplicationType.Android) {
                sfs.manager.get("audio/sounds/523553__matrixxx__tv_shutdown.wav", Sound.class).play(sfs.getSoundVolume());
            }
            hitCounter = 4;
        }
        hit = false;
    }

    public void setAttack(boolean attack) {
        this.attack = attack;
    }
}
