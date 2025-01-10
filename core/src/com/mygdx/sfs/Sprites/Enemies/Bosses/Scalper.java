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
import com.mygdx.sfs.Sprites.Breadcrumbs;
import com.mygdx.sfs.Sprites.Enemies.Enemy;
import com.mygdx.sfs.shootForSurvival;

import java.util.ArrayList;

public class Scalper extends Enemy {
/*State Variables*/
    public enum State{WALK, ATTACK, JUMP, DEAD }
    public State currentState;
    public State previousState;

/*Animation Variables*/
    private boolean scalperDead;
    private boolean attacking;
    private boolean jumping;
    private Animation<TextureRegion> walkAnimation;
    private Animation<TextureRegion> attackAnimation;
    private Animation<TextureRegion> jumpAnimation;
    private Animation<TextureRegion> dieAnimation;
    private Array<TextureRegion> frames;
    private boolean runningRight;

/*Game Variable*/
    private shootForSurvival sfs;
    private float stateTime;

/*Death Variables*/
    private boolean setToDestroy;
    private boolean destroyed;
    private boolean hit = false;
    private int hitCounter;
    private int enemyHitCounter;
    private double hurt = 0;
    private double bulletDamage = 0;

/*Movement Varibales*/
    private float locationX = 0;
    private float locationY = 0;

    private Breadcrumbs breadcrumbs;


/*Constructor*/
    public Scalper(shootForSurvival scalper, PlayScreen screen, float x, float y) {
        super(screen, x, y);
/*Game Variable init*/
        this.sfs = scalper;

/*Walk Animation*/
        frames = new Array<TextureRegion>();

        for(int i = 1; i <= 6; i++) {
            frames.add(sfs.getScalperAtlas().findRegion("walk" + i));
        }

        walkAnimation = new Animation <TextureRegion>(0.3f, frames);
        frames.clear();

/*Death Animation*/
        frames.clear();

        for(int d = 1; d <= 6; d++) {
            frames.add(sfs.getScalperAtlas().findRegion("dead" + d));
        }

        dieAnimation = new Animation <TextureRegion>(0.1f, frames);
        frames.clear();

/*Attack Animation*/
        frames.clear();
        for(int a = 1; a <= 6; a++) {
            frames.add(sfs.getScalperAtlas().findRegion("attack" + a));
        }

        attackAnimation = new Animation <TextureRegion>(0.3f, frames);
        frames.clear();

/*Jump Animation*/
        frames.clear();

        for(int id = 1; id <= 4; id++) {
            frames.add(sfs.getScalperAtlas().findRegion("idle" + id));
        }

        jumpAnimation = new Animation <TextureRegion>(0.3f, frames);
        frames.clear();

/*Initalizing Variables*/
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

/*Returns the State of the Entity*/
    public State getState() {
        if(scalperDead) {
            destroy();
            return State.DEAD;
        }

        else if(attacking) {
            setRegion(attackAnimation.getKeyFrame(stateTime,false));
            attacking = false;
            return State.ATTACK;
        }
        else if(jumping){
            return State.JUMP;
        }
        else {
            return State.WALK;
        }
    }

/*Returns the Animation of the Entity*/
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
        if ((enemyBody.getLinearVelocity().x < 0 || !runningRight)) {
            region.flip(true, false);
            runningRight = false;

        } else if ((enemyBody.getLinearVelocity().x > 0 || runningRight)) {
            region.flip(true, false);
            runningRight = true;
        }
        stateTime = currentState == previousState ? stateTime + dt : 0;
        previousState = currentState;
        return region;
    }

/*Updates the Entity and the various methods applying to the Entity*/
    public void update(float dt) {
        stateTime += dt;
        setRegion(getFrame(dt));
        bulletDamage = screen.getBulletDamage();
        breadcrumbs = screen.getBread();

        if (setToDestroy && !destroyed) {
            scalperDead = true;
            destroyed = true;
            stateTime=0;
        }

        else if (!destroyed) {
            /*for(int i = 0; i <= breadcrumbs.getCrumbs().size(); i++){
                enemyBody.setLinearVelocity(breadcrumbs.getCrumbs().get(i));*/

                if(screen.getPlayer().b2body.getPosition().x < enemyBody.getPosition().x) {
                    locationX = (screen.getPlayer().getX() - 80 / sfs.PPM) - enemyBody.getPosition().x;
                }
                else if(screen.getPlayer().b2body.getPosition().x > enemyBody.getPosition().x){
                    locationX = (screen.getPlayer().getX() + 80 / sfs.PPM) - enemyBody.getPosition().x;
                }
                if(screen.getPlayer().b2body.getPosition().y < enemyBody.getPosition().y){
                    locationY = (screen.getPlayer().getY() / sfs.PPM) - enemyBody.getPosition().y;

                }
                else if(screen.getPlayer().b2body.getPosition().y > enemyBody.getPosition().y) {
                    locationY = (screen.getPlayer().getY() / sfs.PPM) - enemyBody.getPosition().y;

                }
                jumping(0,locationY);
                enemyBody.setLinearVelocity(locationX ,0);
                setPosition(enemyBody.getPosition().x - getWidth() / 2, enemyBody.getPosition().y - getHeight() / 2);
                }
            /*}*/
        }


        /*Creating the Scalper*/
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
                shootForSurvival.BREADCRUMBS_BIT|
                shootForSurvival.PLAYER_BIT;

        fdef.shape = shape;
        enemyBody.createFixture(fdef).setUserData(this);
        enemyBody.setGravityScale(1.1f);
    }

/*Drawing the Batch*/
    public void draw(Batch batch){
        if(!destroyed || stateTime < 1)
            super.draw(batch);
    }

/*Damage method, has calculations for the boss health and how much damage he takes*/
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

/*Sets if the entity is attacking*/
    public void setAttack(boolean attacking) {
        this.attacking = attacking;
    }

/*Sets if the Entity is jumping*/
    public void jumping(float locationx, float locationy) {
            enemyBody.applyLinearImpulse((new Vector2(-5, 20)), enemyBody.getWorldCenter(), true);
            jumping = true;
    }

/*Destroys the Entity*/
    public void destroy(){
        if(destroyed && !world.isLocked()) {
            world.destroyBody(enemyBody);
            enemyBody.setUserData(null);
        }
    }
}
