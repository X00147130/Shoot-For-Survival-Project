package com.mygdx.sfs.Sprites.Items;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.mygdx.sfs.Screens.PlayScreen;
import com.mygdx.sfs.Sprites.Ryu;
import com.mygdx.sfs.shootForSurvival;

public class Coins extends Item {
    private static int count = 0;
    public shootForSurvival ninjarun;


    public Coins(shootForSurvival sfs, PlayScreen screen, float  x, float y) {
        super(screen, x, y);
        setRegion(new Texture("coin.png"));// clipart used
        this.ninjarun = sfs;
    }

    @Override
    public void defineItem() {
        BodyDef bdef = new BodyDef();
        bdef.position.set(getX(), getY());
        bdef.type = BodyDef.BodyType.DynamicBody;
        body = world.createBody(bdef);

        FixtureDef fdef = new FixtureDef();
        CircleShape shape = new CircleShape();
        shape.setRadius(3 / shootForSurvival.PPM);
        fdef.filter.categoryBits = shootForSurvival.MONEY_BIT;
        fdef.filter.maskBits = shootForSurvival.RYU_BIT |
                shootForSurvival.GROUND_BIT |
                shootForSurvival.PLATFORM_BIT |
                shootForSurvival.FINISH_BIT;

        fdef.shape = shape;
        body.createFixture(fdef).setUserData(this);
    }

    @Override
    public void useItem(Ryu ryu) {
        destroy();
        count += 100;
        screen.setCoins(count);
        Gdx.app.log("Coin", "destroyed");
        if(Gdx.app.getType() == Application.ApplicationType.Desktop) {
            ninjarun.loadSound("audio/sounds/coin.mp3");
            long id = ninjarun.sound.play();
            if (ninjarun.getSoundVolume() != 0)
                ninjarun.sound.setVolume(id, ninjarun.getSoundVolume());
            else {
                ninjarun.sound.setVolume(id, 0);
            }
        }
        if(Gdx.app.getType() == Application.ApplicationType.Android) {
            ninjarun.manager.get("audio/sounds/coin.mp3", Sound.class).play(ninjarun.getSoundVolume());
        }

    }
    @Override
    public void update(float dt) {
        super.update(dt);
        setPosition(body.getPosition().x - getWidth() / 2, body.getPosition().y - getHeight() /2);
        if(screen.isComplete() || (screen.getPlayer().currentState == Ryu.State.DEAD && screen.getPlayer().getStateTimer() > 3)){
            count = 0;
        }

    }

    @Override
    public void draw(Batch batch) {
        super.draw(batch);
    }

    public void dispose(){
        screen.dispose();
    }
}
