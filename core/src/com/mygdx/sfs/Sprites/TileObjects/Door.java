package com.mygdx.sfs.Sprites.TileObjects;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.PolygonSpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.utils.Array;
import com.mygdx.sfs.Scenes.Screens.PlayScreen;
import com.mygdx.sfs.Sprites.Player;
import com.mygdx.sfs.shootForSurvival;

public class Door extends InteractiveTileObject {
    private shootForSurvival sfs;
    private Animation<TextureRegion> door;
    private TextureRegion closed;
    private boolean open;

    public Door(shootForSurvival game, PlayScreen screen, MapObject object){
        super(screen,object);
        this.sfs = game;


        closed = sfs.getDoorAtlas().findRegion("door1");

        Array<TextureRegion> frames = new Array<TextureRegion>();

        frames.add(sfs.getDoorAtlas().findRegion("door1"));
        frames.add(sfs.getDoorAtlas().findRegion("door2"));
        frames.add(sfs.getDoorAtlas().findRegion("door3"));
        frames.add(sfs.getDoorAtlas().findRegion("door4"));
        frames.add(sfs.getDoorAtlas().findRegion("door5"));



        door = new Animation<TextureRegion>(0.2f, frames);

        frames.clear();

        fixture.setUserData(this);
        setCategoryFilter(shootForSurvival.DOOR_BIT);
    }

    @Override
    public void onHit(Player player) {
        Gdx.app.log("Door", "Collision");
        if (open == true) {
            screen.setLevelComplete(true);

            if (Gdx.app.getType() == Application.ApplicationType.Desktop) {
                sfs.loadSound("audio/sounds/672801__silverillusionist__level-upmission-complete-resistance.wav");
                long id = sfs.sound.play();
                if (sfs.getSoundVolume() != 0)
                    sfs.sound.setVolume(id, sfs.getSoundVolume());
                else {
                    sfs.sound.setVolume(id, 0);
                }
            }

            if (Gdx.app.getType() == Application.ApplicationType.Android) {
                sfs.manager.get("audio/sounds/672801__silverillusionist__level-upmission-complete-resistance.wav", Sound.class).play(sfs.getSoundVolume());
            }
            sfs.music.stop();

        } else {
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

    public void unlock(){
        open = true;
    }

    public void draw() {
        if (open == false) {
            sfs.batch.begin();
            sfs.batch.draw(closed, bounds.x / sfs.PPM, bounds.y / sfs.PPM, closed.getRegionWidth() / sfs.PPM, closed.getRegionHeight() / sfs.PPM);
            sfs.batch.end();
        } else if (open) {
            sfs.batch.begin();
            sfs.batch.draw(door.getKeyFrame(sfs.statetimer, false), bounds.x / sfs.PPM, bounds.y / sfs.PPM, door.getKeyFrame(sfs.statetimer).getRegionWidth() / sfs.PPM, door.getKeyFrame(sfs.statetimer).getRegionHeight() / sfs.PPM);
            sfs.batch.end();
        }
    }
}
