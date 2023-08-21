package com.png261.bomberman.object;

import com.png261.bomberman.physic.BitCollision;

public class Door extends GameObject {
    @Override
    public void createBody() {
        createRectangleBody(params.x(), params.y(), params.width(), params.height());
        setSensor(true);
        setCollisionFilter(BitCollision.DOOR, BitCollision.BOMBERMAN);
    }

    @Override
    public void render() {
    }

    @Override
    public void load(LoaderParams params) {
        super.load(params);
        createBody();
    }

    @Override
    public void update(float delta) {
    }
}
