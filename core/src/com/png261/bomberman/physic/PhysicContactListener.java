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
		ItemBeginContact(contact);
		FlameAndBrickBeginContact(contact);
		FlameAndBombermanBeginContact(contact);
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

	public void ItemBeginContact(Contact contact) {
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

	public void FlameAndBrickBeginContact(Contact contact) {
		Fixture fixtureA = contact.getFixtureA();
		Fixture fixtureB = contact.getFixtureB();

		short categoryA = fixtureA.getFilterData().categoryBits;
		short categoryB = fixtureB.getFilterData().categoryBits;

		if ((categoryA | categoryB) != (BitCollision.FLAME | BitCollision.BRICK)) {
			return;
		}

		Fixture brickFixture = fixtureA.getFilterData().categoryBits == BitCollision.BRICK ? fixtureA : fixtureB;
		Brick brick = (Brick) brickFixture.getUserData();
		brick.bonus();
	}

	public void FlameAndBombermanBeginContact(Contact contact) {
		Fixture fixtureA = contact.getFixtureA();
		Fixture fixtureB = contact.getFixtureB();

		short categoryA = fixtureA.getFilterData().categoryBits;
		short categoryB = fixtureB.getFilterData().categoryBits;

		if ((categoryA | categoryB) != (BitCollision.FLAME | BitCollision.BOMBERMAN)) {
			return;
		}

		Fixture bombermanFixture = fixtureA.getFilterData().categoryBits == BitCollision.BOMBERMAN ? fixtureA
				: fixtureB;
		Bomberman bomberman = (Bomberman) bombermanFixture.getUserData();
		bomberman.damage();
	}
}
