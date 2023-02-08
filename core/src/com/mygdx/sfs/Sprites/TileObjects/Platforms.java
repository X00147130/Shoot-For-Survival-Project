package com.mygdx.sfs.Sprites.TileObjects;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.maps.MapObject;
import com.mygdx.sfs.Screens.PlayScreen;
import com.mygdx.sfs.Sprites.Player;
import com.mygdx.sfs.shootForSurvival;

public class Platforms extends InteractiveTileObject {
    public Platforms(PlayScreen screen, MapObject object){
        super(screen, object);
        fixture.setUserData(this);
        setCategoryFilter(shootForSurvival.PLATFORM_BIT);
    }

    @Override
    public void onHit(Player player) {
        shootForSurvival.manager.get("audio/sounds/fireworks.mp3", Sound.class).play();
    }
}
