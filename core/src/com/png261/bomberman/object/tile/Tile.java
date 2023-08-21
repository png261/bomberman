package com.png261.bomberman.object.tile;

import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.png261.bomberman.Game;
import com.png261.bomberman.object.GameObject;
import com.png261.bomberman.object.LoaderParams;

public abstract class Tile extends GameObject {
    private final Rectangle bounds;
    protected LoaderParams params;

    public Tile() {
        super();
        this.bounds = new Rectangle();
    }

    @Override
    public void load(final LoaderParams params) {
        this.params = params;
        bounds.setPosition(params.position());
        bounds.setWidth(params.width());
        bounds.setHeight(params.height());
    }

    @Override
    public void createBody() {
        createRectangleBody(params.x(), params.y(), params.width(), params.height());
        setBodyToStatic();
    }

    @Override
    public void update(final float delta) {
    }

    @Override
    public void render() {
    }

    public Rectangle getBounds() {
        return bounds;
    }

    public TiledMapTileLayer.Cell getCell() {
        final TiledMapTileLayer layer = (TiledMapTileLayer) Game.getInstance().level().map().getLayers().get("Tile");
        return layer.getCell((int) body.getPosition().x, (int) body.getPosition().y);
    }

    public void emptyCell() {
        final TiledMapTileLayer.Cell cell = getCell();
        if (cell != null) {
            getCell().setTile(null);
        }
    }

    public boolean contains(final Vector2 position) {
        return getBounds().contains(position.x, position.y);
    }
}
