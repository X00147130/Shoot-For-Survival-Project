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
        shape.setRadius(6 / shootForSurvival.PPM);
        fdef.filter.categoryBits = shootForSurvival.ITEM_BIT;
        fdef.filter.maskBits = shootForSurvival.PLAYER_BIT |
                shootForSurvival.GROUND_BIT |
                shootForSurvival.FINISH_BIT;

        fdef.shape = shape;
        body.createFixture(fdef).setUserData(this);

    }

    @Override
    public void useItem(Player player) {
        destroy();
        Player.setHitCounter(0);
        if(Gdx.app.getType() == Application.ApplicationType.Desktop) {
            sfs.loadSound("audio/sounds/healthDrink.wav");
            long id = sfs.sound.play();
            if (sfs.getSoundVolume() != 0)
                sfs.sound.setVolume(id, sfs.getSoundVolume());
            else {
                sfs.sound.setVolume(id, 0);
            }
        }
        if(Gdx.app.getType() == Application.ApplicationType.Android) {
            sfs.manager.get("audio/sounds/healthDrink.wav", Sound.class).play(sfs.getSoundVolume());
        }
    }

    @Override
    public void update(float dt) {
        super.update(dt);
        setPosition(body.getPosition().x - getWidth() / 2, body.getPosition().y - getHeight() / 2);
        setProxy();
    }

    public void setProxy() {
        setRegion(healthCrate.getKeyFrame(sfs.statetimer, false));
        if (healthCrate.isAnimationFinished(sfs.statetimer))
            setRegion(sfs.getHealthAtlas().findRegion("Health8"));

        else
            setRegion(sfs.getHealthAtlas().findRegion("Health3"));

    }
}
