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
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.utils.Array;
import com.mygdx.sfs.Scenes.Screens.PlayScreen;
import com.mygdx.sfs.Sprites.Enemies.Enemy;
import com.mygdx.sfs.shootForSurvival;

public class Worker extends Enemy {
    //animation variables
    public enum State{ RUNNING, HURT, ATTACK, DEAD }
    public State currentState;
    public State previousState;
    private boolean workerDead;

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

    //private ArrayList<Bullets> bullet;

    private int enemyHitCounter;

    public Worker(shootForSurvival sfs, PlayScreen screen, float x, float y) {
        super(screen, x, y);
        this.sfs = sfs;
        previousState = State.RUNNING;
        currentState = State.RUNNING;

        //Run animation
        frames = new Array<TextureRegion>();
        frames.add(sfs.getWorker1Atlas().findRegion("Run1"));
        frames.add(sfs.getWorker1Atlas().findRegion("Run2"));
        frames.add(sfs.getWorker1Atlas().findRegion("Run3"));
        frames.add(sfs.getWorker1Atlas().findRegion("Run4"));
        frames.add(sfs.getWorker1Atlas().findRegion("Run5"));
        frames.add(sfs.getWorker1Atlas().findRegion("Run6"));


        runAnimation = new Animation<TextureRegion>(1f, frames);
        setBounds(0,0,18/PPM, 20/PPM);
        frames.clear();

        //Death animation
        frames.clear();

        frames.add(sfs.getWorker1Atlas().findRegion("Dead1"));
        frames.add(sfs.getWorker1Atlas().findRegion("Dead2"));
        frames.add(sfs.getWorker1Atlas().findRegion("Dead3"));
        frames.add(sfs.getWorker1Atlas().findRegion("Dead4"));
        frames.add(sfs.getWorker1Atlas().findRegion("Dead5"));
        frames.add(sfs.getWorker1Atlas().findRegion("Dead6"));

        dieAnimation = new Animation <TextureRegion>(0.3f, frames);
        frames.clear();

        //Attack Animation
        frames.clear();

        frames.add(sfs.getWorker1Atlas().findRegion("Attack1"));
        frames.add(sfs.getWorker1Atlas().findRegion("Attack2"));
        frames.add(sfs.getWorker1Atlas().findRegion("Attack3"));
        frames.add(sfs.getWorker1Atlas().findRegion("Attack4"));
        frames.add(sfs.getWorker1Atlas().findRegion("Attack5"));
        frames.add(sfs.getWorker1Atlas().findRegion("Attack6"));

        attackAnimation = new Animation <TextureRegion>(1f, frames);
        frames.clear();

        //Hurt Animation
        frames.clear();

        frames.add(sfs.getWorker1Atlas().findRegion("Hurt1"));
        frames.add(sfs.getWorker1Atlas().findRegion("Hurt2"));

        hurtAnimation = new Animation <TextureRegion>(1f, frames);
        frames.clear();


        stateTime = 0;
        setBounds(getX(), getY(), 30 / PPM , 30 / PPM);
        setToDestroy = false;
        destroyed =false;
        enemyHitCounter = 0;
        workerDead = false;
        runningRight = true;
    }

    public State getState() {
        if(hit && !workerDead)
            return State.HURT;

        else if(attack && !workerDead)
            return State.ATTACK;

        else if(!workerDead)
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

        if ((enemyBody.getLinearVelocity().x < 0 || !runningRight) && !region.isFlipX()) {
            region.flip(true, false);
            runningRight = false;

        } else if ((enemyBody.getLinearVelocity().x > 0 || runningRight) && region.isFlipX()) {
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
            workerDead = true;
            world.destroyBody(enemyBody);
            destroyed = true;
            stateTime=0;


        } else if (!destroyed) {
            if(attack == false)
                enemyBody.setLinearVelocity(velocity);
            else if (attack == true) {
                enemyBody.setLinearVelocity(0, 0);
                attack = false;
            }

            setPosition(enemyBody.getPosition().x - getWidth() /2 , enemyBody.getPosition().y - getHeight() /3 );
            setRegion(getFrame(dt));
        }

    }

    @Override
    protected void defineEnemy() {
        BodyDef bdef = new BodyDef();
        bdef.position.set(getX(),getY());
        bdef.type = BodyDef.BodyType.DynamicBody;
        enemyBody = world.createBody(bdef);

        FixtureDef fdef = new FixtureDef();
        CircleShape shape = new CircleShape();
        shape.setRadius(7 / PPM);
        fdef.filter.categoryBits = shootForSurvival.ENEMY_BIT;
        fdef.filter.maskBits = shootForSurvival.GROUND_BIT |
                shootForSurvival.DOOR_BIT |
                shootForSurvival.ENEMY_BIT |
                shootForSurvival.BARRIER_BIT |
                shootForSurvival.BULLET_BIT|
                shootForSurvival.WALL_BIT|
                shootForSurvival.PLAYER_BIT;

        fdef.shape = shape;
        enemyBody.createFixture(fdef).setUserData(this);
    }

    public void draw(Batch batch){
        if(!destroyed || stateTime < 1)
            super.draw(batch);
    }

    @Override
    public void shot() {
        if(hitCounter < 1){    //Worker is pushed back
            hit = true;
            if(enemyBody.getLinearVelocity().x > 0)
                enemyBody.applyLinearImpulse(new Vector2(-1f,1f), enemyBody.getWorldCenter(),true);

            else if(enemyBody.getLinearVelocity().x < 0)
                enemyBody.applyLinearImpulse(new Vector2(1f,1f), enemyBody.getWorldCenter(),true);

            else{
                enemyBody.applyLinearImpulse(new Vector2(-1f,1f), enemyBody.getWorldCenter(),true);
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
        else {
            setToDestroy = true;
            destroy();
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
            hitCounter = 2;
        }
        hit = false;
    }


    public void setAttack(boolean attack) {
        this.attack = attack;
    }

    public void destroy(){

        if(destroyed) {
            enemyBody.destroyFixture(enemyBody.getFixtureList().get(0));
            if (!world.isLocked())
                world.destroyBody(enemyBody);
        }
    }
}
