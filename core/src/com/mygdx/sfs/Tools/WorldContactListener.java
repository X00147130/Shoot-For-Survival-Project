package com.mygdx.sfs.Tools;


import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.mygdx.sfs.Sprites.Enemies.Enemy;
import com.mygdx.sfs.Sprites.Enemies.Grunts.Worker;
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
                    ((Worker) fixB.getUserData()).shot();
                    ((Bullets) fixA.getUserData()).dispose();
                }
                else {
                    ((Bullets) fixB.getUserData()).destroy();
                    ((Worker) fixA.getUserData()).shot();
                    ((Bullets) fixB.getUserData()).dispose();
                }
                break;

            case shootForSurvival.GROUND_BIT | shootForSurvival.BULLET_BIT:
                if (fixA.getFilterData().categoryBits == shootForSurvival.BULLET_BIT) {
                    ((Bullets) fixA.getUserData()).destroy();
                    ((Bullets) fixA.getUserData()).dispose();
                }
                else {
                    ((Bullets) fixB.getUserData()).destroy();
                    ((Bullets) fixB.getUserData()).dispose();
                }
                break;

            case shootForSurvival.ENEMY_BIT | shootForSurvival.BARRIER_BIT:
                if (fixA.getFilterData().categoryBits == shootForSurvival.ENEMY_BIT)
                    ((Enemy) fixA.getUserData()).reverseVelocity(true, false);
                else
                    ((Enemy) fixB.getUserData()).reverseVelocity(true, false);
                break;

            case shootForSurvival.ENEMY_BIT | shootForSurvival.WALL_BIT:
                if (fixA.getFilterData().categoryBits == shootForSurvival.WALL_BIT)
                    ((Enemy) fixB.getUserData()).reverseVelocity(true, false);
                else
                    ((Enemy) fixA.getUserData()).reverseVelocity(true, false);
                break;


            case shootForSurvival.PLAYER_BIT | shootForSurvival.ENEMY_BIT:
                if (fixA.getFilterData().categoryBits == shootForSurvival.PLAYER_BIT) {
                    ((Player) fixA.getUserData()).hit();
                    ((Worker) fixB.getUserData()).setAttack(true);
                }
                else {
                    ((Player) fixB.getUserData()).hit();
                    ((Worker) fixA.getUserData()).setAttack(true);
                }
                break;

            case shootForSurvival.ENEMY_BIT | shootForSurvival.ENEMY_BIT:
                ((Enemy) fixA.getUserData()).reverseVelocity(true, false);
                ((Enemy) fixB.getUserData()).reverseVelocity(true, false);
                break;


            case shootForSurvival.ITEM_BIT | shootForSurvival.PLAYER_BIT:
                if (fixA.getFilterData().categoryBits == shootForSurvival.ITEM_BIT) {
                    ((Item) fixA.getUserData()).useItem((Player) fixB.getUserData());

                }
                else {
                    ((Item) fixB.getUserData()).useItem((Player) fixA.getUserData());
                }
                break;

            case shootForSurvival.KEY_BIT | shootForSurvival.PLAYER_BIT:
                if (fixA.getFilterData().categoryBits == shootForSurvival.KEY_BIT) {
                    ((Item) fixA.getUserData()).useItem((Player) fixB.getUserData());

                }
                else {
                    ((Item) fixB.getUserData()).useItem((Player) fixA.getUserData());
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



            case shootForSurvival.MONEY_BIT | shootForSurvival.PLAYER_BIT:
                if (fixA.getFilterData().categoryBits == shootForSurvival.MONEY_BIT)
                    ((Item) fixA.getUserData()).useItem((Player) fixB.getUserData());
                else
                    ((Item) fixB.getUserData()).useItem((Player) fixA.getUserData());
                break;


            case shootForSurvival.SKY_BIT | shootForSurvival.PLAYER_BIT:
                if (fixA.getFilterData().categoryBits == shootForSurvival.SKY_BIT)
                    ((InteractiveTileObject) fixA.getUserData()).onHit((Player) fixB.getUserData());
                else
                    ((InteractiveTileObject) fixB.getUserData()).onHit((Player) fixA.getUserData());
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
