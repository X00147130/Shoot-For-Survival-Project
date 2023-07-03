package com.mygdx.sfs.Sprites.Items;

import static com.mygdx.sfs.shootForSurvival.PPM;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.utils.Array;
import com.mygdx.sfs.Scenes.Screens.PlayScreen;
import com.mygdx.sfs.Sprites.Player;
import com.mygdx.sfs.shootForSurvival;

public class HealthCrate extends Item{
    public shootForSurvival sfs;
    private Animation<TextureRegion> healthCrate;
    private boolean proxy = false;
    private float x = 0;
    private boolean isShot = false;

    public HealthCrate(shootForSurvival sfs, PlayScreen screen, float x, float y) {
        super(screen, x, y);
        Array<TextureRegion> frames = new Array<TextureRegion>();


        frames.add(sfs.getHealthAtlas().findRegion("Health4"));
        frames.add(sfs.getHealthAtlas().findRegion("Health5"));
        frames.add(sfs.getHealthAtlas().findRegion("Health6"));
        frames.add(sfs.getHealthAtlas().findRegion("Health7"));
        frames.add(sfs.getHealthAtlas().findRegion("Health8"));

        healthCrate = new Animation<TextureRegion>(0.2f, frames);
        if (Gdx.app.getType() == Application.ApplicationType.Android) {
            setBounds(0, 0, 26 / PPM, 35 / PPM);
        }
        frames.clear();
        this.sfs = sfs;
    }

    @Override
    public void defineItem() {
        BodyDef bdef = new BodyDef();
        bdef.position.set(getX(),getY());
        bdef.type = BodyDef.BodyType.DynamicBody;
        body = world.createBody(bdef);

        FixtureDef fdef = new FixtureDef();
        CircleShape shape = new CircleShape();
        shape.setRadius(8 / shootForSurvival.PPM);
        fdef.filter.categoryBits = shootForSurvival.ITEM_BIT;
        fdef.filter.maskBits = shootForSurvival.PLAYER_BIT |
                shootForSurvival.GROUND_BIT|
                shootForSurvival.BULLET_BIT;

        fdef.shape = shape;
        body.createFixture(fdef).setUserData(this);

    }

    @Override
    public void useItem(Player player) {
        if (isShot) {
            destroy();
            Player.setHitCounter(0);
            if (Gdx.app.getType() == Application.ApplicationType.Desktop) {
                sfs.loadSound("audio/sounds/health drink.mp3");
                long id = sfs.sound.play();
                if (sfs.getSoundVolume() != 0)
                    sfs.sound.setVolume(id, sfs.getSoundVolume());
                else {
                    sfs.sound.setVolume(id, 0);
                }
            }
            if (Gdx.app.getType() == Application.ApplicationType.Android) {
                sfs.manager.get("audio/sounds/health drink.mp3", Sound.class).play(sfs.getSoundVolume());
            }
        }

        else{
            if (Gdx.app.getType() == Application.ApplicationType.Desktop) {
                sfs.loadSound("audio/sounds/stomp.wav");
                long id = sfs.sound.play();
                if (sfs.getSoundVolume() != 0)
                    sfs.sound.setVolume(id, sfs.getSoundVolume());
                else {
                    sfs.sound.setVolume(id, 0);
                }
            }
            if (Gdx.app.getType() == Application.ApplicationType.Android) {
                sfs.manager.get("audio/sounds/stomp.wav", Sound.class).play(sfs.getSoundVolume());
            }
        }
    }


    public void open(){
        if(isShot){
            setRegion(sfs.getHealthAtlas().findRegion("Health8"));


            FixtureDef fdef2 = new FixtureDef();
            fdef2.filter.categoryBits = shootForSurvival.ITEM_BIT;
            fdef2.filter.maskBits = shootForSurvival.GROUND_BIT|
                    shootForSurvival.PLAYER_BIT;

            body.getFixtureList().get(0).setFilterData(fdef2.filter);
        }
    }

    @Override
    public void update(float dt) {
        super.update(dt);
        setPosition(body.getPosition().x - getWidth() / 2, body.getPosition().y - getHeight() / 2);
        body.setGravityScale(100);
        if(!isShot)
            setRegion(sfs.getHealthAtlas().findRegion("Health3"));
    }


    public void setShot(boolean shot) {
        isShot = shot;
    }
}
