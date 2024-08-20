package com.mygdx.sfs.Sprites.Items;

import static com.mygdx.sfs.shootForSurvival.PPM;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
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

        Array<TextureRegion> frames = new Array<TextureRegion>();
        for(int i = 1; i <= sfs.getDoorAtlas().getRegions().size; i++) {
                frames.add(sfs.getKeycardAtlas().findRegion("keycard" + i));
            }


        keycard = new Animation<TextureRegion>(0.2f, frames);
        if (Gdx.app.getType() == Application.ApplicationType.Android) {
            setBounds(0, 0, 26 / PPM, 35 / PPM);
        }

        setBounds(getX(),getY(),32 / sfs.PPM,32 / sfs.PPM);
        frames.clear();
        this.sfs = sfs;
    }

    @Override
    public void defineItem() {
        BodyDef bdef = new BodyDef();
        bdef.position.set(getX(), getY());
        bdef.type = BodyDef.BodyType.DynamicBody;
        body = world.createBody(bdef);

        FixtureDef fdef = new FixtureDef();
        CircleShape shape = new CircleShape();
        shape.setRadius(6 / shootForSurvival.PPM);
        fdef.filter.categoryBits = shootForSurvival.ITEM_BIT;
        fdef.filter.maskBits = shootForSurvival.PLAYER_BIT |
                shootForSurvival.GROUND_BIT |
                shootForSurvival.SCANNER_BIT;

        fdef.shape = shape;
        body.createFixture(fdef).setUserData(this);
    }

    @Override
    public void useItem(Player player) {
        destroy();
        Gdx.app.log("KEY", "Collected");
        count = 1;
        screen.setKeys(count);
        player.setKey(true);

        if(Gdx.app.getType() == Application.ApplicationType.Desktop) {
            sfs.loadSound("audio/sounds/678385__jocabundus__item-pickup-v2.wav");
            long id = sfs.sound.play();
            if (sfs.getSoundVolume() != 0)
                sfs.sound.setVolume(id, sfs.getSoundVolume());
            else {
                sfs.sound.setVolume(id, 0);
            }
        }
        if(Gdx.app.getType() == Application.ApplicationType.Android) {
            sfs.manager.get("audio/sounds/678385__jocabundus__item-pickup-v2.wav", Sound.class).play(sfs.getSoundVolume());
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
