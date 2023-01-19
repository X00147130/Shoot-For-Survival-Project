package com.mygdx.sfs.Sprites.TileObjects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.maps.MapObject;
import com.mygdx.sfs.Screens.PlayScreen;
import com.mygdx.sfs.Sprites.Ryu;
import com.mygdx.sfs.shootForSurvival;

public class Barrier extends InteractiveTileObject {

    public Barrier(PlayScreen screen, MapObject object){
        super(screen,object);
        fixture.setUserData(this);
        setCategoryFilter(shootForSurvival.BARRIER_BIT);
    }

    @Override
    public void onHit(Ryu ryu) {
        Gdx.app.log("Finish", "Collision");
    }
}
