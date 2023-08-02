package com.mygdx.sfs.Sprites.Items;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.mygdx.sfs.Scenes.Screens.PlayScreen;
import com.mygdx.sfs.Sprites.Player;
import com.mygdx.sfs.shootForSurvival;

public class HealthCrate extends Item{
    public shootForSurvival sfs;

    public HealthCrate(shootForSurvival sfs, PlayScreen screen, float x, float y) {
        super(screen, x, y);
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
                shootForSurvival.GROUND_BIT;

        fdef.shape = shape;
        body.createFixture(fdef).setUserData(this);

    }

    @Override
    public void useItem(Player player) {
            destroy();
            world.clearForces();
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



    @Override
    public void update(float dt) {
        super.update(dt);
        setPosition(body.getPosition().x - getWidth() / 2, body.getPosition().y - getHeight() / 2);
        body.setGravityScale(100);
        setRegion(sfs.getHealthAtlas().findRegion("Health3"));
    }

}
