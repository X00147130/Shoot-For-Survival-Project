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
import com.badlogic.gdx.utils.Array;
import com.mygdx.sfs.Scenes.Screens.PlayScreen;
import com.mygdx.sfs.Sprites.Player;
import com.mygdx.sfs.shootForSurvival;

public class Rifles extends Item{
    public shootForSurvival sfs;
    private Animation<TextureRegion> rifle;
    private int powerLVL = 0;
    private int pistolLvl;

    public Rifles(shootForSurvival sfs, PlayScreen screen, float x, float y) {
        super(screen, x, y);

        pistolLvl = sfs.getPistolLvl();

        Array<TextureRegion> frames = new Array<TextureRegion>();
        frames.add(sfs.getRifles().findRegion("1"));
        frames.add(sfs.getRifles().findRegion("1"));

        rifle = new Animation<TextureRegion>(0.2f, frames);
        if (Gdx.app.getType() == Application.ApplicationType.Android) {
            setBounds(0, 0, 22 / sfs.PPM, 19 / sfs.PPM);
        }else {
            setBounds(getX(), getY(), 22 / sfs.PPM, 12 / sfs.PPM);
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
                shootForSurvival.DOOR_BIT;

        fdef.shape = shape;
        body.createFixture(fdef).setUserData(this);

    }

    @Override
    public void useItem(Player player) {

        sfs.setPowerLVL(powerLVL);
        player.setRifle(true);
        Gdx.app.log("Rifle", "Collected");

        destroy();




        if(Gdx.app.getType() == Application.ApplicationType.Desktop) {
            sfs.loadSound("audio/sounds/gun pickup.mp3");
            long id = sfs.sound.play();
            if (sfs.getSoundVolume() != 0) {
                sfs.sound.setVolume(id, sfs.getSoundVolume());
            }
            else {
                sfs.sound.setVolume(id, 0);
            }
        }
        if(Gdx.app.getType() == Application.ApplicationType.Android) {
            sfs.manager.get("audio/sounds/gun pickup.mp3", Sound.class).play(sfs.getSoundVolume());
        }
    }

    @Override
    public void update(float dt) {
        super.update(dt);
        /*powerLVL = sfs.getPowerLVL();*/

        pistolLvl = sfs.getPistolLvl();

        switch(pistolLvl){
            case 1:
                sfs.setPowerLVL(1);
                powerLVL = 1;
                break;
            case 2:
                sfs.setPowerLVL(2);
                powerLVL = 2;
                break;
            case 3:
                sfs.setPowerLVL(3);
                powerLVL = 3;
                break;
            case 4:
                sfs.setPowerLVL(4);
                powerLVL = 4;
                break;
            case 5:
                sfs.setPowerLVL(5);
                powerLVL = 5;
                break;
            case 6:
                sfs.setPowerLVL(6);
                powerLVL = 6;
                break;
            case 7:
                sfs.setPowerLVL(7);
                powerLVL = 7;
                break;
            case 8:
                sfs.setPowerLVL(8);
                powerLVL = 8;
                break;
            case 9:
                sfs.setPowerLVL(9);
                powerLVL = 9;
                break;
            case 10:
                sfs.setPowerLVL(10);
                powerLVL = 10;
                break;
        }


        Array<TextureRegion> frames = new Array<TextureRegion>();
        for(int i = 1; i <= sfs.getRifles().getRegions().size; i++) {
            if (powerLVL == i) {
                frames.add(sfs.getRifles().findRegion("" + (i)));
                rifle = new Animation<TextureRegion>(0.2f, frames);
            }
        }

        setPosition(body.getPosition().x - getWidth() / 2, body.getPosition().y - getHeight() / 2);
        setRegion(rifle.getKeyFrame(sfs.statetimer,false));
    }
    @Override
    public void draw(Batch batch) {
        super.draw(batch);
    }

}

