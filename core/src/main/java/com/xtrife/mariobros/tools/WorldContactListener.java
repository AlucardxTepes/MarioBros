package com.xtrife.mariobros.tools;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.xtrife.mariobros.Main;
import com.xtrife.mariobros.sprites.Enemy;
import com.xtrife.mariobros.sprites.InteractiveTileObject;

/**
 * Created by 9S on 2/24/2025 - 2:50 PM.
 */
public class WorldContactListener implements ContactListener {
    @Override
    public void beginContact(Contact contact) {
        Fixture fixA = contact.getFixtureA();
        Fixture fixB = contact.getFixtureB();

        int cDef = fixA.getFilterData().categoryBits | fixB.getFilterData().categoryBits;

        if (fixA.getUserData() == "head" || fixB.getUserData() == "head") {
            // assign head and object
            Fixture head = fixA.getUserData().equals("head") ? fixA : fixB;
            Fixture object = head == fixA ? fixB : fixA;

            // check if object is an interactive tile object
            if (object.getUserData() != null && InteractiveTileObject.class.isAssignableFrom(object.getUserData().getClass())) {
                ((InteractiveTileObject)object.getUserData()).onHeadHit();
            }
        }

        switch (cDef) {
            case Main.ENEMY_HEAD_BIT | Main.MARIO_BIT:
                // collision between enemy head and Mario
                if (fixA.getFilterData().categoryBits == Main.ENEMY_HEAD_BIT)
                    ((Enemy) fixA.getUserData()).hitOnHead();
                else
                    ((Enemy) fixB.getUserData()).hitOnHead();
                break;
            case Main.ENEMY_BIT | Main.OBJECT_BIT:
                // collision between enemy and pipe. Turnaround
                if (fixA.getFilterData().categoryBits == Main.ENEMY_BIT)
                    ((Enemy) fixA.getUserData()).reverseVelocity(true, false);
                else
                    ((Enemy) fixB.getUserData()).reverseVelocity(true, false);
                break;
            case Main.MARIO_BIT | Main.ENEMY_BIT:
                Gdx.app.log("MARIO", "DIED");
                break;
            case Main.ENEMY_BIT:
                ((Enemy) fixA.getUserData()).reverseVelocity(true, false);
                ((Enemy) fixB.getUserData()).reverseVelocity(true, false);

        }
    }

    @Override
    public void endContact(Contact contact) {

    }

    @Override
    public void preSolve(Contact contact, Manifold manifold) {

    }

    @Override
    public void postSolve(Contact contact, ContactImpulse contactImpulse) {

    }
}
