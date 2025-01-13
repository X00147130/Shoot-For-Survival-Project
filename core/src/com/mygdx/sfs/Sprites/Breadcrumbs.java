package com.mygdx.sfs.Sprites;

import static com.mygdx.sfs.shootForSurvival.PPM;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.mygdx.sfs.Scenes.Screens.PlayScreen;
import com.mygdx.sfs.shootForSurvival;

public class Breadcrumbs {
    private shootForSurvival sfs;
    private PlayScreen screen;
    private Body breadBody;
    private Vector2 position;
    private boolean markedForDeletion;
    protected World world;

    public Breadcrumbs(PlayScreen screen, shootForSurvival sfs){
        this.world = screen.getWorld();
        this.sfs = sfs;
        this.screen = screen;
        this.position = new Vector2(screen.getPlayer().getX(), screen.getPlayer().getY() + (5 / sfs.PPM));
        this.markedForDeletion = false;
        defineBreadcrumb();
    }

    private void defineBreadcrumb() {
        BodyDef bdef = new BodyDef();
        bdef.position.set(position);
        bdef.type = BodyDef.BodyType.StaticBody;
        breadBody = world.createBody(bdef);

        FixtureDef fdef = new FixtureDef();
        CircleShape shape = new CircleShape();
        shape.setRadius(4 / PPM);
        fdef.filter.categoryBits = shootForSurvival.BREADCRUMBS_BIT;
        fdef.filter.maskBits = shootForSurvival.BOSS_BIT | shootForSurvival.GROUND_BIT;

        fdef.shape = shape;
        Fixture fixture = breadBody.createFixture(fdef);
        fixture.setUserData(this); // Important for collision handling
        shape.dispose();
    }

    public void markForDeletion() {
        this.markedForDeletion = true;
    }

    public boolean isMarkedForDeletion() {
        return markedForDeletion;
    }

    public void delete() {
        if (!world.isLocked()) {
            System.out.println("Deleting breadcrumb at position: " + position);
            // Remove all fixtures
            while (breadBody.getFixtureList().size > 0) {
                breadBody.destroyFixture(breadBody.getFixtureList().get(0));
            }
            breadBody.setUserData(null);
            world.destroyBody(breadBody);
        }
    }

    public Vector2 getPosition(){
        return position;
    }
}
