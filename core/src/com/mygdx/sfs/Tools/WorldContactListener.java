package com.mygdx.sfs.Tools;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.mygdx.sfs.Sprites.Breadcrumbs;
import com.mygdx.sfs.Sprites.Enemies.Bosses.Scalper;
import com.mygdx.sfs.Sprites.Enemies.Enemy;
import com.mygdx.sfs.Sprites.Enemies.Grunts.Grunt;
import com.mygdx.sfs.Sprites.Items.Bullets;
import com.mygdx.sfs.Sprites.Items.HealthCrate;
import com.mygdx.sfs.Sprites.Items.Item;
import com.mygdx.sfs.Sprites.Player;
import com.mygdx.sfs.Sprites.TileObjects.InteractiveTileObject;
import com.mygdx.sfs.shootForSurvival;

public class WorldContactListener implements ContactListener {
    @Override
    public void beginContact(Contact contact) {
        Fixture fixA = contact.getFixtureA();
        Fixture fixB = contact.getFixtureB();

        int cDef = fixA.getFilterData().categoryBits | fixB.getFilterData().categoryBits;

        switch (cDef) {
            case shootForSurvival.PLAYER_BIT | shootForSurvival.DOOR_BIT:
                if (fixA.getFilterData().categoryBits == shootForSurvival.PLAYER_BIT)
                    ((InteractiveTileObject) fixB.getUserData()).onHit((Player) fixA.getUserData());
                else
                    ((InteractiveTileObject) fixA.getUserData()).onHit((Player) fixB.getUserData());
                break;


            case shootForSurvival.PLAYER_BIT | shootForSurvival.GROUND_BIT:
                if (fixA.getFilterData().categoryBits == shootForSurvival.PLAYER_BIT) {
                    ((Player) fixA.getUserData()).jumpReset();
                }
                else
                    ((Player) fixB.getUserData()).jumpReset();
                break;


            case shootForSurvival.PLAYER_BIT | shootForSurvival.WALL_BIT:
                if (fixA.getFilterData().categoryBits == shootForSurvival.PLAYER_BIT) {
                    ((Player) fixA.getUserData()).wall();
                }
                else
                    ((Player) fixB.getUserData()).wall();
                break;


            case shootForSurvival.ENEMY_BIT | shootForSurvival.BULLET_BIT:
                if (fixA.getFilterData().categoryBits == shootForSurvival.BULLET_BIT) {
                    ((Bullets) fixA.getUserData()).destroy();
                    ((Grunt) fixB.getUserData()).shot();
                }
                else {
                    ((Bullets) fixB.getUserData()).destroy();
                    ((Grunt) fixA.getUserData()).shot();
                }
                break;


            case shootForSurvival.GROUND_BIT | shootForSurvival.BULLET_BIT:

            case shootForSurvival.WALL_BIT | shootForSurvival.BULLET_BIT:
                if (fixA.getFilterData().categoryBits == shootForSurvival.BULLET_BIT) {
                    ((Bullets) fixA.getUserData()).destroy();
                }
                else {
                    ((Bullets) fixB.getUserData()).destroy();
                }
                break;


            case shootForSurvival.ENEMY_BIT | shootForSurvival.BARRIER_BIT:

            case shootForSurvival.ENEMY_BIT | shootForSurvival.WALL_BIT:
                if (fixA.getFilterData().categoryBits == shootForSurvival.ENEMY_BIT)
                    ((Enemy) fixA.getUserData()).reverseVelocity(true, false);
                else
                    ((Enemy) fixB.getUserData()).reverseVelocity(true, false);
                break;


            case shootForSurvival.PLAYER_BIT | shootForSurvival.ENEMY_BIT:
                if (fixA.getFilterData().categoryBits == shootForSurvival.PLAYER_BIT) {
                    ((Player) fixA.getUserData()).hit();
                    ((Grunt) fixB.getUserData()).setAttack(true);

                }
                else {
                    ((Player) fixB.getUserData()).hit();
                    ((Grunt) fixA.getUserData()).setAttack(true);
                }
                break;


            case shootForSurvival.ENEMY_BIT:
                ((Enemy) fixA.getUserData()).reverseVelocity(true, false);
                ((Enemy) fixB.getUserData()).reverseVelocity(true, false);
                break;



            case shootForSurvival.BOSS_BIT | shootForSurvival.BULLET_BIT:
                if (fixA.getFilterData().categoryBits == shootForSurvival.BULLET_BIT) {
                    ((Scalper) fixB.getUserData()).shot();
                    ((Bullets) fixA.getUserData()).destroy();
                }
                else {
                    ((Scalper) fixA.getUserData()).shot();
                    ((Bullets) fixB.getUserData()).destroy();
                }
                break;

            case shootForSurvival.BOSS_BIT | shootForSurvival.GROUND_BIT:
                if(fixA.getFilterData().categoryBits == shootForSurvival.BOSS_BIT)
                    ((Scalper) fixA.getUserData()).currentState = Scalper.State.WALK;
                else
                    ((Scalper) fixB.getUserData()).currentState = Scalper.State.WALK;
                break;

            case shootForSurvival.BOSS_BIT | shootForSurvival.WALL_BIT:
                if (fixA.getFilterData().categoryBits == shootForSurvival.BOSS_BIT)
                    ((Scalper) fixA.getUserData()).enemyBody.applyLinearImpulse(0f,10f, ((Scalper) fixA.getUserData()).getX(), ((Scalper) fixA.getUserData()).getY(),true);
                else
                    ((Scalper) fixB.getUserData()).enemyBody.applyLinearImpulse(0f,10f, ((Scalper) fixB.getUserData()).getX(), ((Scalper) fixB.getUserData()).getY(),true);
                break;


            case shootForSurvival.PLAYER_BIT | shootForSurvival.BOSS_BIT:
                if (fixA.getFilterData().categoryBits == shootForSurvival.PLAYER_BIT) {
                    ((Player) fixA.getUserData()).hit();
                    ((Scalper) fixB.getUserData()).setAttack(true);

                }
                else {
                    ((Player) fixB.getUserData()).hit();
                    ((Scalper) fixA.getUserData()).setAttack(true);
                }
                break;


            case shootForSurvival.BREADCRUMBS_BIT | shootForSurvival.BOSS_BIT:
                System.out.println("HERE");
                if (fixA.getFilterData().categoryBits == shootForSurvival.BREADCRUMBS_BIT) {
                    ((Breadcrumbs) fixA.getUserData()).setSetToPop(true);
                    Gdx.app.log("Boss", "Breadcrumb");
                }
                else {
                    ((Breadcrumbs) fixB.getUserData()).setSetToPop(true);
                    Gdx.app.log("Boss", "Breadcrumb");
                }
                break;


            case shootForSurvival.ITEM_BIT | shootForSurvival.PLAYER_BIT:
                if (fixA.getFilterData().categoryBits == shootForSurvival.ITEM_BIT) {
                    ((Item) fixA.getUserData()).useItem((Player) fixB.getUserData());

                }
                else {
                    ((Item) fixB.getUserData()).useItem((Player) fixA.getUserData());
                }
                break;

            case shootForSurvival.HEALTH_BIT | shootForSurvival.PLAYER_BIT:
                if (fixA.getFilterData().categoryBits == shootForSurvival.HEALTH_BIT) {
                    ((HealthCrate) fixA.getUserData()).setHealthJustTouched(true);

                }
                else {
                    ((HealthCrate) fixB.getUserData()).setHealthJustTouched(true);
                }
                break;


            case shootForSurvival.SCANNER_BIT | shootForSurvival.PLAYER_BIT:
                if (fixA.getFilterData().categoryBits == shootForSurvival.SCANNER_BIT) {
                    ((InteractiveTileObject) fixA.getUserData()).onHit((Player) fixB.getUserData());
                    }
                else {
                    ((InteractiveTileObject) fixB.getUserData()).onHit((Player) fixA.getUserData());
                }
                break;


            case shootForSurvival.SKY_BIT | shootForSurvival.PLAYER_BIT:
                if (fixA.getFilterData().categoryBits == shootForSurvival.SKY_BIT)
                    ((InteractiveTileObject) fixA.getUserData()).onHit((Player) fixB.getUserData());
                else
                    ((InteractiveTileObject) fixB.getUserData()).onHit((Player) fixA.getUserData());
                break;

            case shootForSurvival.DEATH_BIT | shootForSurvival.PLAYER_BIT:
                if (fixA.getFilterData().categoryBits == shootForSurvival.PLAYER_BIT) {
                    ((Player) fixA.getUserData()).setFellToDeath(true);
                    ((Player) fixA.getUserData()).fellToDeath();
                }
                else {
                    ((Player) fixB.getUserData()).setFellToDeath(true);
                    ((Player) fixB.getUserData()).fellToDeath();
                }
                break;
        }
    }

    @Override
    public void endContact(Contact contact) {

    }

    @Override
    public void preSolve(Contact contact, Manifold oldManifold) {

    }

    @Override
    public void postSolve(Contact contact, ContactImpulse impulse) {

    }
}
