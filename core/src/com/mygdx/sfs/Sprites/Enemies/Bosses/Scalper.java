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
    public enum State{WALK, ATTACK, JUMP, DEAD }
    public State currentState;
    public State previousState;
    private boolean scalperDead;
    private boolean attacking;
    private boolean jumping;

    private shootForSurvival sfs;

    private float stateTime;
    private Animation<TextureRegion> walkAnimation;
    private Animation<TextureRegion> attackAnimation;
    private Animation<TextureRegion> jumpAnimation;
    private Animation<TextureRegion> dieAnimation;
    private Array<TextureRegion> frames;
    private boolean setToDestroy;
    private boolean destroyed;
    private boolean runningRight;
    private boolean hit = false;
    private int hitCounter;

    private float locationX = 0;
    private float locationY = 0;

    private int enemyHitCounter;
    private double hurt = 0;
    private double bulletDamage = 0;

    public Scalper(shootForSurvival scalper, PlayScreen screen, float x, float y) {
        super(screen, x, y);
        this.sfs = scalper;

        //walk animation
        frames = new Array<TextureRegion>();
        frames.add(sfs.getScalperAtlas().findRegion("walk1"));
        frames.add(sfs.getScalperAtlas().findRegion("walk2"));
        frames.add(sfs.getScalperAtlas().findRegion("walk3"));
        frames.add(sfs.getScalperAtlas().findRegion("walk4"));
        frames.add(sfs.getScalperAtlas().findRegion("walk5"));
        frames.add(sfs.getScalperAtlas().findRegion("walk6"));

        walkAnimation = new Animation <TextureRegion>(0.3f, frames);
        frames.clear();

        //death animation
        frames.clear();

        frames.add(sfs.getScalperAtlas().findRegion("dead1"));
        frames.add(sfs.getScalperAtlas().findRegion("dead2"));
        frames.add(sfs.getScalperAtlas().findRegion("dead3"));
        frames.add(sfs.getScalperAtlas().findRegion("dead4"));
        frames.add(sfs.getScalperAtlas().findRegion("dead5"));
        frames.add(sfs.getScalperAtlas().findRegion("dead6"));

        dieAnimation = new Animation <TextureRegion>(0.1f, frames);
        frames.clear();

        //attack animation
        frames.clear();

        frames.add(sfs.getScalperAtlas().findRegion("attack1"));
        frames.add(sfs.getScalperAtlas().findRegion("attack2"));
        frames.add(sfs.getScalperAtlas().findRegion("attack3"));
        frames.add(sfs.getScalperAtlas().findRegion("attack4"));
        frames.add(sfs.getScalperAtlas().findRegion("attack5"));
        frames.add(sfs.getScalperAtlas().findRegion("attack6"));

        attackAnimation = new Animation <TextureRegion>(0.3f, frames);
        frames.clear();

        //Jump animation
        frames.clear();

        frames.add(sfs.getScalperAtlas().findRegion("idle1"));
        frames.add(sfs.getScalperAtlas().findRegion("idle2"));
        frames.add(sfs.getScalperAtlas().findRegion("idle3"));
        frames.add(sfs.getScalperAtlas().findRegion("idle4"));

        jumpAnimation = new Animation <TextureRegion>(0.3f, frames);
        frames.clear();


        stateTime = 0;
        setBounds(getX(), getY(), 28 / shootForSurvival.PPM , 30 / shootForSurvival.PPM);
        setToDestroy = false;
        destroyed =false;
        jumping = false;
        enemyHitCounter = 0;
        scalperDead = false;
        runningRight = true;
        bulletDamage = screen.getBulletDamage();
    }

    public State getState() {
        if(scalperDead) {
            return State.DEAD;
        }

        else if(attacking) {
            return State.ATTACK;
        }
        else if(jumping){
            return State.JUMP;
        }
        else {
            return State.WALK;
        }
    }

    public TextureRegion getFrame(float dt) {
        currentState = getState();

        TextureRegion region;

        switch (currentState) {
            case DEAD:
                region = dieAnimation.getKeyFrame(stateTime, false);
                break;

            case ATTACK:
                region = attackAnimation.getKeyFrame(stateTime, false);

            default:
                region = walkAnimation.getKeyFrame(stateTime,true);
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
        bulletDamage = screen.getBulletDamage();

        if (setToDestroy && !destroyed) {
            scalperDead = true;
            destroyed = true;
            destroy();
            stateTime=0;
        }

        else if (!destroyed) {
            if(screen.getPlayer().b2body.getPosition().x < enemyBody.getPosition().x) {
                locationX = (screen.getPlayer().getX() - 80 / sfs.PPM) - enemyBody.getPosition().x;
            }
            else if(screen.getPlayer().b2body.getPosition().x > enemyBody.getPosition().x){
                locationX = (screen.getPlayer().getX() + 80 / sfs.PPM) - enemyBody.getPosition().x;
            }
            if(screen.getPlayer().b2body.getPosition().y < enemyBody.getPosition().y){
                locationY = (screen.getPlayer().getY() - 80 / sfs.PPM) - enemyBody.getPosition().y;
                enemyBody.setGravityScale(10f);
            }
            else if(screen.getPlayer().b2body.getPosition().y > enemyBody.getPosition().y) {
                locationY = (screen.getPlayer().getY() + 80 / sfs.PPM) - enemyBody.getPosition().y;
                enemyBody.setGravityScale(0);
            }
            enemyBody.setLinearVelocity(locationX ,locationY);
            setPosition(enemyBody.getPosition().x - getWidth() / 2, enemyBody.getPosition().y - getHeight() / 2);
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
        shape.setRadius(7 / shootForSurvival.PPM);
        fdef.filter.categoryBits = shootForSurvival.BOSS_BIT;
        fdef.filter.maskBits = shootForSurvival.GROUND_BIT |
                shootForSurvival.BULLET_BIT|
                shootForSurvival.BARRIER_BIT|
                shootForSurvival.DOOR_BIT|
                shootForSurvival.SKY_BIT|
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
        hurt += bulletDamage;
        if (hurt < 29) {//Scalper is pushed back
            hit = true;
            if(screen.getPlayer().b2body.getPosition().x < enemyBody.getPosition().x)
                enemyBody.applyLinearImpulse(new Vector2(-5f, 5f), enemyBody.getWorldCenter(), true);

            else if(screen.getPlayer().b2body.getPosition().x > enemyBody.getPosition().x)
                enemyBody.applyLinearImpulse(new Vector2(5f, 5f), enemyBody.getWorldCenter(), true);


            else
                enemyBody.applyLinearImpulse(new Vector2(-5f,5f), enemyBody.getWorldCenter(),true);


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
            hitCounter = 30;
        }
    }

    public void setAttack(boolean attacking) {
        this.attacking = attacking;
    }

    public void attack(){
        if(attacking) {
            currentState = State.ATTACK;
        }
    }

    public void jumping() {
            enemyBody.applyLinearImpulse((new Vector2(-5, 20)), enemyBody.getWorldCenter(), true);
            jumping = true;
    }
    public void destroy(){

        if(destroyed && !world.isLocked()) {
            world.destroyBody(enemyBody);
            enemyBody.setUserData(null);
        }
    }
}
