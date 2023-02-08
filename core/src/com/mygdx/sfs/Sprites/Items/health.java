package com.mygdx.sfs.Sprites.Items;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.mygdx.sfs.Screens.PlayScreen;
import com.mygdx.sfs.Sprites.Player;
import com.mygdx.sfs.shootForSurvival;

public class health extends Item{
    public shootForSurvival ninjarun;

    public health(shootForSurvival ninjarun, PlayScreen screen, float x, float y) {
        super(screen, x, y);
        setRegion(new Texture("sprites/health_vial1.png"));
        this.ninjarun = ninjarun;
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
                shootForSurvival.PLATFORM_BIT |
                shootForSurvival.FINISH_BIT;

        fdef.shape = shape;
        body.createFixture(fdef).setUserData(this);

    }

    @Override
    public void useItem(Player player) {
        destroy();
        Player.setHitCounter(0);
        if(Gdx.app.getType() == Application.ApplicationType.Desktop) {
            ninjarun.loadSound("audio/sounds/healthDrink.wav");
            long id = ninjarun.sound.play();
            if (ninjarun.getSoundVolume() != 0)
                ninjarun.sound.setVolume(id, ninjarun.getSoundVolume());
            else {
                ninjarun.sound.setVolume(id, 0);
            }
        }
        if(Gdx.app.getType() == Application.ApplicationType.Android) {
            ninjarun.manager.get("audio/sounds/healthDrink.wav", Sound.class).play(ninjarun.getSoundVolume());
        }
    }

    @Override
    public void update(float dt) {
        super.update(dt);
        setPosition(body.getPosition().x - getWidth() / 2, body.getPosition().y - getHeight() / 2);
    }
}
