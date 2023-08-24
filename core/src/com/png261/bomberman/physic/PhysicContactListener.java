package com.png261.bomberman.physic;

import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.png261.bomberman.manager.GameStateManager;
import com.png261.bomberman.object.Bomb;
import com.png261.bomberman.object.Bomberman;
import com.png261.bomberman.object.DamageableObject;
import com.png261.bomberman.object.item.Item;
import com.png261.bomberman.object.tile.Brick;
import com.png261.bomberman.states.SinglePlayerState;

public class PhysicContactListener implements ContactListener {
    @Override
    public void beginContact(final Contact contact) {
        handleItemBeginContact(contact);
        handleFlameBeginContact(contact);
        handleEnemyBeginConntact(contact);
        handleDoorBeginContact(contact);
    }

    @Override
    public void endContact(final Contact contact) {
        handleBombEndContact(contact);
    }

    @Override
    public void preSolve(final Contact contact, final Manifold oldManifold) {
    }

    @Override
    public void postSolve(final Contact contact, final ContactImpulse impulse) {
    }

    public void handleBombEndContact(final Contact contact) {
        final Fixture fixA = contact.getFixtureA();
        final Fixture fixB = contact.getFixtureB();
        if (fixA.getFilterData().categoryBits == BitCollision.BOMB
                || fixB.getFilterData().categoryBits == BitCollision.BOMB) {
            final Fixture bombFix = fixA
                    .getFilterData().categoryBits == BitCollision.BOMB
                            ? fixA
                            : fixB;
            final Bomb bomb = (Bomb) bombFix.getUserData();
            bomb.setSensor(false);
        }
    }

    public void handleItemBeginContact(final Contact contact) {
        final Fixture fixtureA = contact.getFixtureA();
        final Fixture fixtureB = contact.getFixtureB();

        final short categoryA = fixtureA.getFilterData().categoryBits;
        final short categoryB = fixtureB.getFilterData().categoryBits;

        if ((categoryA | categoryB) != (BitCollision.ITEM | BitCollision.BOMBERMAN)) {
            return;
        }

        final Fixture itemFixture = fixtureA.getFilterData().categoryBits == BitCollision.ITEM ? fixtureA : fixtureB;
        final Fixture bombermanFixture = fixtureA.getFilterData().categoryBits == BitCollision.BOMBERMAN ? fixtureA
                : fixtureB;

        final Item item = (Item) itemFixture.getUserData();
        final Bomberman bomberman = (Bomberman) bombermanFixture.getUserData();

        item.bonus(bomberman);
    }

    public void handleDoorBeginContact(final Contact contact) {
        final Fixture fixtureA = contact.getFixtureA();
        final Fixture fixtureB = contact.getFixtureB();

        final short categoryA = fixtureA.getFilterData().categoryBits;
        final short categoryB = fixtureB.getFilterData().categoryBits;

        if ((categoryA | categoryB) != (BitCollision.DOOR | BitCollision.BOMBERMAN)) {
            return;
        }
        final SinglePlayerState singlePlayerState = (SinglePlayerState) GameStateManager.getInstance().currentState();
        if (singlePlayerState != null) {
            singlePlayerState.nextLevel();
        }
    }

    public void handleFlameBeginContact(final Contact contact) {
        handleFlameBrickContact(contact);
        handleFlameDamageableObjectContact(contact);
    }

    public void handleFlameBrickContact(final Contact contact) {
        final Fixture fixtureA = contact.getFixtureA();
        final Fixture fixtureB = contact.getFixtureB();

        final short categoryA = fixtureA.getFilterData().categoryBits;
        final short categoryB = fixtureB.getFilterData().categoryBits;

        if ((categoryA | categoryB) != (BitCollision.FLAME | BitCollision.BRICK)) {
            return;
        }

        final Fixture brickFixture = categoryA == BitCollision.BRICK ? fixtureA : fixtureB;
        final Brick brick = (Brick) brickFixture.getUserData();
        brick.broken();
    }

    public void handleFlameDamageableObjectContact(final Contact contact) {
        final Fixture fixtureA = contact.getFixtureA();
        final Fixture fixtureB = contact.getFixtureB();

        final short categoryA = fixtureA.getFilterData().categoryBits;
        final short categoryB = fixtureB.getFilterData().categoryBits;

        if (((categoryA | categoryB) != (BitCollision.FLAME | BitCollision.BOMBERMAN))
                && ((categoryA | categoryB) != (BitCollision.FLAME | BitCollision.ENEMY))) {
            return;
        }

        final Fixture objectFixture = categoryB == BitCollision.FLAME ? fixtureA
                : fixtureB;
        final DamageableObject object = (DamageableObject) objectFixture.getUserData();
        object.damage(1);
    }

    public void handleEnemyBeginConntact(final Contact contact) {
        final Fixture fixtureA = contact.getFixtureA();
        final Fixture fixtureB = contact.getFixtureB();

        final short categoryA = fixtureA.getFilterData().categoryBits;
        final short categoryB = fixtureB.getFilterData().categoryBits;

        if (((categoryA | categoryB) != (BitCollision.ENEMY | BitCollision.BOMBERMAN))) {
            return;
        }

        final Fixture bombermanFixture = categoryA == BitCollision.BOMBERMAN ? fixtureA
                : fixtureB;
        final Bomberman bomberman = (Bomberman) bombermanFixture.getUserData();
        bomberman.damage(1);
    }
}
