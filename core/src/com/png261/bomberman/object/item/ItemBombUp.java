package com.png261.bomberman.object.item;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Filter;
import com.png261.bomberman.object.person.bomberman.Bomberman;
import com.png261.bomberman.physic.BitCollision;

public class ItemBombUp extends Item {
	public ItemBombUp() {
		super();
	}

	@Override
	public void load(Vector2 position) {
		super.load(position);

		sprite = new Sprite(new Texture("bombup-sticker.png"));
		sprite.setBounds(position.x, position.y, 1, 1);
	}

	@Override
	public void bonus(Bomberman bomberman) {
		super.bonus(bomberman);
		bomberman.bombUp(1);
	}
}
