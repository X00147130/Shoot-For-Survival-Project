package com.mygdx.sfs.Sprites.TileObjects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.maps.MapObject;
import com.mygdx.sfs.Screens.PlayScreen;
import com.mygdx.sfs.Sprites.Player;
import com.mygdx.sfs.shootForSurvival;

public class Sky extends InteractiveTileObject{

    public Sky(PlayScreen screen, MapObject object) {
        super(screen, object);
        fixture.setUserData(this);
        setCategoryFilter(shootForSurvival.SKY_BIT);
    }

    @Override
    public void onHit(Player player) {
        Gdx.app.log("sky","limit hit");
        player.reverseVelocity(false,true);
    }

}
