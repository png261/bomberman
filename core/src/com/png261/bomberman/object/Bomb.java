package com.png261.bomberman.object;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Filter;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.png261.bomberman.Game;
import com.png261.bomberman.animation.AnimationHandle;
import com.png261.bomberman.level.Level;
import com.png261.bomberman.object.Object;
import com.png261.bomberman.object.person.bomberman.Bomberman;
import com.png261.bomberman.physic.BitCollision;
import com.png261.bomberman.utils.Unit;
import java.util.ArrayList;

public class Bomb extends Object {
	private final float BODY_DIAMETER = 0.95f;
	private final float FRAME_TIME = 0.6f;
	private final static TextureAtlas atlas = new TextureAtlas("bomb.atlas");

	private int flameLength = 3;
	private AnimationHandle animationHandle;
	private Sprite sprite;
	private float countDown = 1.5f;
	private Bomberman bombOwner;
	private float timeRemove;
	private boolean canMove = true;
	private boolean canKick = false;

	private enum State {
		BOMB_IDLE("bomb_idle"), BOMB_EXPLODE("bomb_explode");

		String value;

		private State(String value) {
			this.value = value;
		}

		public String getValue() {
			return value;
		}
	}

	public Bomb() {
		sprite = new Sprite();
		animationHandle = new AnimationHandle();
		animationHandle.addAnimation(State.BOMB_IDLE.getValue(),
				new Animation<TextureRegion>(FRAME_TIME, atlas.findRegions(State.BOMB_IDLE.getValue())));
		animationHandle.addAnimation(State.BOMB_EXPLODE.getValue(),
				new Animation<TextureRegion>(FRAME_TIME, atlas.findRegions(State.BOMB_EXPLODE.getValue())));
		animationHandle.setCurrentAnimation(State.BOMB_IDLE.getValue());
	}

	public void load(Vector2 position) {
		createCircleBody(position, BODY_DIAMETER / 2);
		setCollisionFilter(BitCollision.BOMB, BitCollision.orOperation(BitCollision.BOMBERMAN, BitCollision.WALL,
				BitCollision.BRICK, BitCollision.FLAME, BitCollision.ENEMY, BitCollision.BOMB));
	}

	@Override
	public void update(float delta) {
		updateSprite();

		if (Gdx.input.isKeyPressed(Input.Keys.P)) {
			explode();
		}

		if (animationHandle.isCurrentAnimation(State.BOMB_EXPLODE.getValue()) && animationHandle.isFinished()) {
			disappear();
		}
	}

	@Override
	public void render() {
		sprite.draw(Game.getInstance().getBatch());
	}

	private void updateSprite() {
		sprite.setBounds(Unit.box2DToScreen(body.getPosition().x, BODY_DIAMETER),
				Unit.box2DToScreen(body.getPosition().y, BODY_DIAMETER),
				Unit.pixelsToMeters(animationHandle.getCurrentFrame().getRegionWidth()),
				Unit.pixelsToMeters(animationHandle.getCurrentFrame().getRegionHeight()));

		sprite.setRegion(animationHandle.getCurrentFrame());
	}

	private void explode() {
		animationHandle.setCurrentAnimation(State.BOMB_EXPLODE.getValue());
		final Level level = Game.getInstance().getLevel();

		for (Flame.State direction : Flame.State.values()) {
			for (int i = 1; i <= flameLength; ++i) {
				Vector2 position = body.getPosition().add(Flame.State.getOffSet(direction).scl(i));
				Vector2 positionPixel = Unit.coordMetersToPixels(position.x, position.y);

				if (level.isWall(positionPixel)) {
					break;
				}

				level.spawnObject(new Flame(Unit.coordBox2DSnapToGrid(position), direction));

				if (level.isBrick(positionPixel)) {
					break;
				}
			}
		}

		Game.getInstance().getLevel()
				.spawnObject(new Flame(Unit.coordBox2DSnapToGrid(body.getPosition()), Flame.State.FLAME_UP));

		setSensor(true);
	}
}
