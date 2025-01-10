package com.mygdx.sfs.Sprites;

import static com.mygdx.sfs.shootForSurvival.PPM;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;
import com.mygdx.sfs.Scenes.Screens.PlayScreen;
import com.mygdx.sfs.Sprites.Enemies.Bosses.Scalper;
import com.mygdx.sfs.shootForSurvival;
import java.util.ArrayList;

public class Breadcrumbs {
/*ArrayList to store the breadcrumbs for the boss to follow*/
    private ArrayList<Vector2> crumbs = new ArrayList<Vector2>(4);

    private shootForSurvival sfs;
    private PlayScreen screen;
    private Body breadBody;

/*Players Position to create the breadcrumbs*/
    private Vector2 playerPos;

/*When to pop the arrayList to get rid of the oldest breadcrumb*/
    private boolean setToPop;
    protected World world;

    public Breadcrumbs(PlayScreen screen, shootForSurvival sfs){
        this.world = screen.getWorld();
        this.sfs = sfs;
        this.screen = screen;
        playerPos = new Vector2(screen.getPlayer().getX(), screen.getPlayer().getY() + (5 / sfs.PPM));
        crumbs.add(playerPos);
        setToPop = false;
        Define();

    }

    public void Define() {
        BodyDef bdef = new BodyDef();
        bdef.position.set(playerPos);
        bdef.type = BodyDef.BodyType.StaticBody;
        breadBody = sfs.world.createBody(bdef);

        FixtureDef fdef = new FixtureDef();
        CircleShape shape = new CircleShape();
        shape.setRadius(4 / PPM);
        fdef.filter.categoryBits = shootForSurvival.BREADCRUMBS_BIT;
        fdef.filter.maskBits = shootForSurvival.BOSS_BIT|
        shootForSurvival.GROUND_BIT;

        fdef.shape = shape;
        breadBody.createFixture(fdef).setUserData(this);
    }

    public void update(float dt) {
        if (setToPop) {
            System.out.println("SET TO POP == TRUE");
            delete();
        }
    }


    public void delete(){
        if (!world.isLocked()) {
            System.out.println("DELETING CRAP");
            for (int i =0 ; i<= breadBody.getFixtureList().size; i++) {
                breadBody.destroyFixture(breadBody.getFixtureList().get(i));
            }

            breadBody.setUserData(null);
            world.destroyBody(breadBody);
        }
    }


    public void setSetToPop(boolean setToPop) {
        this.setToPop = setToPop;
        System.out.println("POPPED");
    }

    public Vector2 getPosition(){
        return playerPos;
    }

    public ArrayList<Vector2> getCrumbs() {
        return crumbs;
    }
}
