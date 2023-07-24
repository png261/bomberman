package com.png261.bomberman.physic;

import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.png261.bomberman.object.item.Item;
import com.png261.bomberman.object.person.bomberman.Bomberman;
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
        handleFlameBombermanContact(contact);
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

    public void handleFlameBombermanContact(Contact contact) {
        Fixture fixtureA = contact.getFixtureA();
        Fixture fixtureB = contact.getFixtureB();

        short categoryA = fixtureA.getFilterData().categoryBits;
        short categoryB = fixtureB.getFilterData().categoryBits;

        if ((categoryA | categoryB) != (BitCollision.FLAME | BitCollision.BOMBERMAN)) {
            return;
        }

        Fixture bombermanFixture = categoryA == BitCollision.BOMBERMAN ? fixtureA
                : fixtureB;
        Bomberman bomberman = (Bomberman) bombermanFixture.getUserData();
        bomberman.damage(1);
    }
}
