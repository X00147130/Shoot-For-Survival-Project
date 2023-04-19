package com.mygdx.sfs.Sprites.TileObjects;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.maps.MapObject;
import com.mygdx.sfs.Scenes.Screens.PlayScreen;
import com.mygdx.sfs.Sprites.Player;
import com.mygdx.sfs.shootForSurvival;

public class Finish extends InteractiveTileObject {
    private shootForSurvival ninja;
    public Finish(shootForSurvival ninja, PlayScreen screen, MapObject object){
        super(screen,object);
        this.ninja = ninja;
        fixture.setUserData(this);
        setCategoryFilter(shootForSurvival.FINISH_BIT);
    }

    @Override
    public void onHit(Player player) {
        Gdx.app.log("Finish", "Collision");
        screen.setLevelComplete(true);
        if(Gdx.app.getType() == Application.ApplicationType.Desktop) {
            ninja.loadSound("audio/sounds/Mission Accomplished Fanfare 1.mp3");
            long id = ninja.sound.play();
            if (ninja.getSoundVolume() != 0)
                ninja.sound.setVolume(id, ninja.getSoundVolume());
            else {
                ninja.sound.setVolume(id, 0);
            }
        }

        if(Gdx.app.getType() == Application.ApplicationType.Android){
            ninja.manager.get("audio/sounds/Mission Accomplished Fanfare 1.mp3", Sound.class).play(ninja.getSoundVolume());
        }

        ninja.music.stop();
        }



}
