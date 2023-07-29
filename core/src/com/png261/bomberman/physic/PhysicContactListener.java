package com.png261.bomberman.physic;

import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.png261.bomberman.object.DamageableObject;
import com.png261.bomberman.object.GameObject;
import com.png261.bomberman.object.item.Item;
import com.png261.bomberman.object.bomberman.Bomberman;
import com.png261.bomberman.object.tile.Brick;

public class PhysicContactListener implements ContactListener {
    @Override
    public void beginContact(Contact contact) {
        handleItemContact(contact);
        handleFlameContact(contact);
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

    public void handleItemContact(Contact contact) {
        Fixture fixtureA = contact.getFixtureA();
        Fixture fixtureB = contact.getFixtureB();

        short categoryA = fixtureA.getFilterData().categoryBits;
        short categoryB = fixtureB.getFilterData().categoryBits;

        if ((categoryA | categoryB) != (BitCollision.ITEM | BitCollision.BOMBERMAN)) {
            return;
        }

        Fixture itemFixture = fixtureA.getFilterData().categoryBits == BitCollision.ITEM ? fixtureA : fixtureB;
        Fixture bombermanFixture = fixtureA.getFilterData().categoryBits == BitCollision.BOMBERMAN ? fixtureA
                : fixtureB;

        Item item = (Item) itemFixture.getUserData();
        Bomberman bomberman = (Bomberman) bombermanFixture.getUserData();

        item.bonus(bomberman);
    }

    public void handleFlameContact(Contact contact) {
        handleFlameBrickContact(contact);
        handleFlameDamageableObjectContact(contact);
    }

    public void handleFlameBrickContact(Contact contact) {
        Fixture fixtureA = contact.getFixtureA();
        Fixture fixtureB = contact.getFixtureB();

        short categoryA = fixtureA.getFilterData().categoryBits;
        short categoryB = fixtureB.getFilterData().categoryBits;

        if ((categoryA | categoryB) != (BitCollision.FLAME | BitCollision.BRICK)) {
            return;
        }

        Fixture brickFixture = categoryA == BitCollision.BRICK ? fixtureA : fixtureB;
        Brick brick = (Brick) brickFixture.getUserData();
        brick.broken();
    }

    public void handleFlameDamageableObjectContact(Contact contact) {
        Fixture fixtureA = contact.getFixtureA();
        Fixture fixtureB = contact.getFixtureB();

        short categoryA = fixtureA.getFilterData().categoryBits;
        short categoryB = fixtureB.getFilterData().categoryBits;

        if (((categoryA | categoryB) != (BitCollision.FLAME | BitCollision.BOMBERMAN))
                && ((categoryA | categoryB) != (BitCollision.FLAME | BitCollision.ENEMY))) {
            return;
        }

        Fixture objectFixture = categoryB == BitCollision.FLAME ? fixtureA
                : fixtureB;
        DamageableObject object = (DamageableObject) objectFixture.getUserData();
        object.damage(1);
    }
}
