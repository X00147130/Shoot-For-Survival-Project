package com.mygdx.sfs.Sprites.Items;

import static com.mygdx.sfs.shootForSurvival.PPM;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.utils.Array;
import com.mygdx.sfs.Scenes.Screens.PlayScreen;
import com.mygdx.sfs.Sprites.Player;
import com.mygdx.sfs.shootForSurvival;



public class KeyCard extends Item {
    private static int count = 0;
    public shootForSurvival sfs;
    private Animation<TextureRegion> keycard;


    public KeyCard(shootForSurvival sfs, PlayScreen screen, float  x, float y) {
        super(screen, x, y);
        this.sfs = sfs;

        Array<TextureRegion> frames = new Array<TextureRegion>();

        frames.add(sfs.getKeycardAtlas().findRegion("keycard1"));
        frames.add(sfs.getKeycardAtlas().findRegion("keycard2"));
        frames.add(sfs.getKeycardAtlas().findRegion("keycard3"));
        frames.add(sfs.getKeycardAtlas().findRegion("keycard4"));
        frames.add(sfs.getKeycardAtlas().findRegion("keycard5"));
        frames.add(sfs.getKeycardAtlas().findRegion("keycard6"));
        frames.add(sfs.getKeycardAtlas().findRegion("keycard7"));
        frames.add(sfs.getKeycardAtlas().findRegion("keycard8"));


        keycard = new Animation<TextureRegion>(0.2f, frames);
        if (Gdx.app.getType() == Application.ApplicationType.Android) {
            setBounds(0, 0, 26 / PPM, 35 / PPM);
        }
        frames.clear();
    }

    @Override
    public void defineItem() {
        BodyDef bdef = new BodyDef();
        bdef.position.set(getX(), getY());
        bdef.type = BodyDef.BodyType.DynamicBody;
        body = world.createBody(bdef);

        FixtureDef fdef = new FixtureDef();
        PolygonShape shape = new PolygonShape();
        shape.setRadius(5 / shootForSurvival.PPM);
        fdef.filter.categoryBits = shootForSurvival.KEY_BIT;
        fdef.filter.maskBits = shootForSurvival.PLAYER_BIT |
                shootForSurvival.GROUND_BIT |
                shootForSurvival.SCREEN_BIT;

        fdef.shape = shape;
        body.createFixture(fdef).setUserData(this);
    }

    @Override
    public void useItem(Player player) {
        destroy();
        Gdx.app.log("KEY", "Collected");

        if(Gdx.app.getType() == Application.ApplicationType.Desktop) {
            sfs.loadSound("audio/sounds/coin.mp3");
            long id = sfs.sound.play();
            if (sfs.getSoundVolume() != 0)
                sfs.sound.setVolume(id, sfs.getSoundVolume());
            else {
                sfs.sound.setVolume(id, 0);
            }
        }
        if(Gdx.app.getType() == Application.ApplicationType.Android) {
            sfs.manager.get("audio/sounds/coin.mp3", Sound.class).play(sfs.getSoundVolume());
        }
    }

    @Override
    public void update(float dt) {
        super.update(dt);
        setPosition(body.getPosition().x - getWidth() / 2, body.getPosition().y - getHeight() /2);
        setRegion(keycard.getKeyFrame(sfs.statetimer,true));
        if(screen.isComplete() || (screen.getPlayer().currentState == Player.State.DEAD && screen.getPlayer().getStateTimer() > 3)){
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