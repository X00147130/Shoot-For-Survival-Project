package com.mygdx.sfs.Sprites.TileObjects;

import static com.mygdx.sfs.shootForSurvival.PPM;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.utils.Array;
import com.mygdx.sfs.Scenes.Screens.PlayScreen;
import com.mygdx.sfs.Sprites.Player;
import com.mygdx.sfs.shootForSurvival;

public class Door extends InteractiveTileObject {
    private shootForSurvival sfs;
    private Animation<TextureRegion> door;
    private boolean open;

    public Door(shootForSurvival game, PlayScreen screen, MapObject object){
        super(screen,object);
        this.sfs = game;

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
        screen.setLevelComplete(true);
        door.getKeyFrame(sfs.statetimer,false);
        if(Gdx.app.getType() == Application.ApplicationType.Desktop) {
            sfs.loadSound("audio/sounds/Mission Accomplished Fanfare 1.mp3");
            long id = sfs.sound.play();
            if (sfs.getSoundVolume() != 0)
                sfs.sound.setVolume(id, sfs.getSoundVolume());
            else {
                sfs.sound.setVolume(id, 0);
            }
        }

        if(Gdx.app.getType() == Application.ApplicationType.Android){
            sfs.manager.get("audio/sounds/Mission Accomplished Fanfare 1.mp3", Sound.class).play(sfs.getSoundVolume());
        }

        sfs.music.stop();
        }

        public void unlock(){
            open = true;
        }

}
