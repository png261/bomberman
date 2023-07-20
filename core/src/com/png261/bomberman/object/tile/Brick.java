package com.png261.bomberman.object.tile;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.png261.bomberman.Game;
import com.png261.bomberman.object.item.ItemBombUp;
import com.png261.bomberman.object.item.ItemFlameUp;
import com.png261.bomberman.object.item.ItemSpeedUp;
import com.png261.bomberman.physic.BitCollision;

public class Brick extends Tile {
	public Brick(Rectangle rect) {
		super(rect);
		setCollisionFilter(BitCollision.BRICK, BitCollision.orOperation(BitCollision.BOMBERMAN, BitCollision.BOMB,
				BitCollision.FLAME, BitCollision.ENEMY));
	}

	public void bonus() {
		emptyCell();

		// ItemSpeedUp item = new ItemSpeedUp();
		// Vector2 position = body.getPosition();
		// item.load(position);
		// Game.getInstance().getLevel().spawnObject(item);

		disappear();
	}
}
