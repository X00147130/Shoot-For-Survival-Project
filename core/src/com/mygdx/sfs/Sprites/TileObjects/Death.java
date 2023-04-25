package com.mygdx.sfs.Sprites.TileObjects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.maps.MapObject;
import com.mygdx.sfs.Scenes.Screens.PlayScreen;
import com.mygdx.sfs.Sprites.Player;
import com.mygdx.sfs.shootForSurvival;

public class Death extends InteractiveTileObject{
    public Death(PlayScreen screen, MapObject object){
        super(screen,object);
        fixture.setUserData(this);
        setCategoryFilter(shootForSurvival.DEATH_BIT);
    }

    @Override
    public void onHit(Player player) {
        Gdx.app.log("DEATH", "Collision");
    }
}

