package com.mygdx.sfs.Tools;


import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.mygdx.sfs.Sprites.Enemies.Enemy;
import com.mygdx.sfs.Sprites.Enemies.Ninja;
import com.mygdx.sfs.Sprites.Items.Item;
import com.mygdx.sfs.Sprites.Ryu;
import com.mygdx.sfs.Sprites.TileObjects.InteractiveTileObject;
import com.mygdx.sfs.shootForSurvival;

public class WorldContactListener implements ContactListener {
    @Override
    public void beginContact(Contact contact) {
        Fixture fixA = contact.getFixtureA();
        Fixture fixB = contact.getFixtureB();

        int cDef = fixA.getFilterData().categoryBits | fixB.getFilterData().categoryBits;

        switch (cDef) {
            case shootForSurvival.RYU_BIT | shootForSurvival.FINISH_BIT:
                if (fixA.getFilterData().categoryBits == shootForSurvival.RYU_BIT)
                    ((InteractiveTileObject) fixB.getUserData()).onHit((Ryu) fixA.getUserData());
                else
                    ((InteractiveTileObject) fixA.getUserData()).onHit((Ryu) fixB.getUserData());
                break;

            case shootForSurvival.RYU_BIT | shootForSurvival.GROUND_BIT:
                if (fixA.getFilterData().categoryBits == shootForSurvival.RYU_BIT)
                    ((Ryu) fixA.getUserData()).jumpReset();
                else
                    ((Ryu) fixB.getUserData()).jumpReset();
                break;

            case shootForSurvival.RYU_BIT | shootForSurvival.PLATFORM_BIT:
                if (fixA.getFilterData().categoryBits == shootForSurvival.RYU_BIT)
                    ((Ryu) fixA.getUserData()).jumpReset();
                else
                    ((Ryu) fixB.getUserData()).jumpReset();
                break;

            case shootForSurvival.ENEMY_BIT | shootForSurvival.ATTACK_BIT:
                if (fixA.getFilterData().categoryBits == shootForSurvival.ATTACK_BIT)
                    ((Ninja) fixB.getUserData()).attacked();
                else
                    ((Ninja) fixA.getUserData()).attacked();
                break;

            case shootForSurvival.ENEMY_BIT | shootForSurvival.BARRIER_BIT:
                if (fixA.getFilterData().categoryBits == shootForSurvival.ENEMY_BIT)
                    ((Enemy) fixA.getUserData()).reverseVelocity(true, false);
                else
                    ((Enemy) fixB.getUserData()).reverseVelocity(true, false);
                break;

            case shootForSurvival.RYU_BIT | shootForSurvival.ENEMY_BIT:
                if (fixA.getFilterData().categoryBits == shootForSurvival.RYU_BIT)
                    ((Ryu) fixA.getUserData()).hit();
                else
                    ((Ryu) fixB.getUserData()).hit();
                break;

            case shootForSurvival.ENEMY_BIT | shootForSurvival.ENEMY_BIT:
                ((Enemy) fixA.getUserData()).reverseVelocity(true, false);
                ((Enemy) fixB.getUserData()).reverseVelocity(true, false);
                break;


            case shootForSurvival.ITEM_BIT | shootForSurvival.RYU_BIT:
                if (fixA.getFilterData().categoryBits == shootForSurvival.ITEM_BIT)
                    ((Item) fixA.getUserData()).useItem((Ryu) fixB.getUserData());
                else
                    ((Item) fixB.getUserData()).useItem((Ryu) fixA.getUserData());
                break;

            case shootForSurvival.MONEY_BIT | shootForSurvival.RYU_BIT:
                if (fixA.getFilterData().categoryBits == shootForSurvival.MONEY_BIT)
                    ((Item) fixA.getUserData()).useItem((Ryu) fixB.getUserData());
                else
                    ((Item) fixB.getUserData()).useItem((Ryu) fixA.getUserData());
                break;

            case shootForSurvival.SKY_BIT | shootForSurvival.RYU_BIT:
                if (fixA.getFilterData().categoryBits == shootForSurvival.SKY_BIT)
                    ((InteractiveTileObject) fixA.getUserData()).onHit((Ryu) fixB.getUserData());
                else
                    ((InteractiveTileObject) fixB.getUserData()).onHit((Ryu) fixA.getUserData());
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
