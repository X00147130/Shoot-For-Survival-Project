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

        //bullet.add(new Bullets(sfs, screen, b2body.getPosition().x, b2body.getPosition().y));


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
        setBounds(getX(), getY(), 26 / PPM , 26 / PPM);
        setToDestroy = false;
        destroyed =false;
        enemyHitCounter = 0;
        workerDead = false;
        runningRight = true;
    }

    public State getState() {
        if(workerDead == true)
            return State.DEAD;

        else if(workerDead == false)
            return State.RUNNING;

        else if(hit == true && workerDead == false)
            return State.HURT;

        else if(attack == true && workerDead == false)
            return State.ATTACK;

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
            workerDead = true;
            world.destroyBody(b2body);
            destroyed = true;
            stateTime=0;


        } else if (!destroyed) {
            if(attack == false)
                b2body.setLinearVelocity(velocity);
            else if (attack == true)
                b2body.setLinearVelocity(0,0);
                attack = false;

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
    public void shot() {
        if(hitCounter < 2){    //Grunt is pushed back
            hit = true;
            if(b2body.getLinearVelocity().x > 0)
                b2body.applyLinearImpulse(new Vector2(-1f,1f),b2body.getWorldCenter(),true);

            else if(b2body.getLinearVelocity().x < 0)
                b2body.applyLinearImpulse(new Vector2(1f,1f),b2body.getWorldCenter(),true);

            else{
                b2body.applyLinearImpulse(new Vector2(-1f,1f),b2body.getWorldCenter(),true);
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
        }else {

            setToDestroy = true;
            if (Gdx.app.getType() == Application.ApplicationType.Desktop) {
                sfs.loadSound("audio/sounds/stomp.wav");
                long id = sfs.sound.play();
                if (sfs.getSoundVolume() != 0) {
                    sfs.sound.setVolume(id, sfs.getSoundVolume());
                } else {
                    sfs.sound.setVolume(id, 0);
                }
            }

            if (Gdx.app.getType() == Application.ApplicationType.Android) {
                sfs.manager.get("audio/sounds/stomp.wav", Sound.class).play(sfs.getSoundVolume());
            }
            hitCounter = 3;
        }
    }

    public void setAttack(boolean attack) {
        this.attack = attack;
    }
}

