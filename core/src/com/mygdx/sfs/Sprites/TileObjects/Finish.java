package com.mygdx.sfs.Sprites.TileObjects;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.maps.MapObject;
import com.mygdx.sfs.Scenes.Screens.PlayScreen;
import com.mygdx.sfs.Sprites.Player;
import com.mygdx.sfs.shootForSurvival;

public class Finish extends InteractiveTileObject {
    private shootForSurvival sfs;

    public Finish(shootForSurvival game, PlayScreen screen, MapObject object){
        super(screen,object);
        this.sfs = game;
        fixture.setUserData(this);
        setCategoryFilter(shootForSurvival.FINISH_BIT);
    }

    @Override
    public void onHit(Player player) {
        Gdx.app.log("Finish", "Collision");
        screen.setLevelComplete(true);
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


}
