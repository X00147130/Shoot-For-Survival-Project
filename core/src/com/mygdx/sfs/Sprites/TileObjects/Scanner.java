package com.mygdx.sfs.Sprites.TileObjects;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.maps.MapObject;
import com.mygdx.sfs.Scenes.Screens.PlayScreen;
import com.mygdx.sfs.Sprites.Player;
import com.mygdx.sfs.shootForSurvival;

public class Scanner extends InteractiveTileObject {
    private shootForSurvival sfs;
    private boolean destroyed = false;
    private boolean todestroy = false;
    private boolean scannerJustTouched = false;

    public Scanner(shootForSurvival game, PlayScreen screen, MapObject object) {
        super(screen, object);
        this.sfs = game;
        fixture.setUserData(this);
        setCategoryFilter(shootForSurvival.SCANNER_BIT);
    }

    @Override
    public void onHit(Player player) {
        Gdx.app.log("Scanner", "Collision");
        if (player.getKey()) {
            scannerJustTouched = true;
            todestroy = true;

            if (Gdx.app.getType() == Application.ApplicationType.Desktop) {
                sfs.loadSound("audio/sounds/364688__alegemaate__electronic-door-opening.wav");
                long id = sfs.sound.play();
                if (sfs.getSoundVolume() != 0) {
                    sfs.sound.setVolume(id, sfs.getSoundVolume());
                }
                else {
                    sfs.sound.setVolume(id, 0);
                }
            }

            if (Gdx.app.getType() == Application.ApplicationType.Android) {
                sfs.manager.get("audio/sounds/364688__alegemaate__electronic-door-opening.wav", Sound.class).play(sfs.getSoundVolume());
            }
        }

        else {
            todestroy = false;
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


    public void destroyBody(){
        if (todestroy){
            if(!world.isLocked())
                world.destroyBody(body);
            destroyed = true;

        }
    }

    public boolean isDestroyed() {
        return destroyed;
    }

    public boolean isScannerJustTouched() {
        return scannerJustTouched;
    }
}
