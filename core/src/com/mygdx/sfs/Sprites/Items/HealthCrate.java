package com.mygdx.sfs.Sprites.Items;

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
import com.mygdx.sfs.Scenes.Screens.PlayScreen;
import com.mygdx.sfs.Sprites.Player;
import com.mygdx.sfs.shootForSurvival;

public class HealthCrate extends Item{
    public shootForSurvival sfs;
    private TextureRegion closed;
    private Animation<TextureRegion> health;
    private boolean healthJustTouched = false;

    public HealthCrate(shootForSurvival sfs, PlayScreen screen, float x, float y) {
        super(screen, x, y);
        this.sfs = sfs;
        this.sfs.setArea(screen.getArea());
        closed = this.sfs.getHealthAtlas().findRegion("Health3");

        Array<TextureRegion> frames = new Array<TextureRegion>();

        for(int i = 1; i <= sfs.getHealthAtlas().getRegions().size; i++) {
            frames.add(sfs.getHealthAtlas().findRegion("Health" + i));
        }
        health = new Animation<TextureRegion>(0.5f, frames, Animation.PlayMode.NORMAL);
        if (Gdx.app.getType() == Application.ApplicationType.Android) {
            setBounds(getX(), getY(), 20 / shootForSurvival.PPM, 20 / shootForSurvival.PPM);
        }
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
        fdef.filter.categoryBits = shootForSurvival.HEALTH_BIT;
        fdef.filter.maskBits = shootForSurvival.PLAYER_BIT |
                shootForSurvival.GROUND_BIT;

        fdef.shape = shape;
        body.createFixture(fdef).setUserData(this);

    }

    @Override
    public void useItem(Player player) {
        Gdx.app.log("Health", "Collision");
        destroy();
        world.clearForces();
        Player.setHitCounter(0);
    }



    @Override
    public void update(float dt) {
        super.update(dt);
        setPosition(body.getPosition().x - getWidth() / 2, body.getPosition().y - getHeight() / 2);
        if(screen.getPlayer().b2body.getPosition().x > body.getPosition().x - 0.25 && screen.getPlayer().b2body.getPosition().x < body.getPosition().x + 0.25 &&  screen.getPlayer().b2body.getPosition().y > body.getPosition().y - 0.25)
            setRegion(health.getKeyFrame(super.itemStateTimer,false));

        else{
            setRegion(closed);
        }

        if(healthJustTouched){
            onHit(screen.getPlayer());
        }
    }

    @Override
    public void draw(Batch batch) {
        super.draw(batch);
    }

    public void onHit(Player player) {
        if (healthJustTouched) {
            healthJustTouched = false;
            if (Gdx.app.getType() == Application.ApplicationType.Desktop) {
                sfs.loadSound("audio/sounds/health drink.mp3");
                long id = sfs.sound.play();
                if (sfs.getSoundVolume() != 0) {
                    sfs.sound.setVolume(id, sfs.getSoundVolume());
                } else {
                    sfs.sound.setVolume(id, 0);
                }
            }

            if (Gdx.app.getType() == Application.ApplicationType.Android) {
                sfs.manager.get("audio/sounds/health drink.mp3", Sound.class).play(sfs.getSoundVolume());
            }
            useItem(player);
        }
    }

    public void setHealthJustTouched(boolean healthJustTouched) {
        this.healthJustTouched = healthJustTouched;
    }
}
