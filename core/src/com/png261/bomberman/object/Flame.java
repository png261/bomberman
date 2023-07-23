package com.png261.bomberman.object;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.png261.bomberman.Game;
import com.png261.bomberman.animation.AnimationHandle;
import com.png261.bomberman.physic.BitCollision;
import com.png261.bomberman.utils.Unit;

public class Flame extends GameObject {
    private final float FRAME_TIME = 0.3f;
    private final float BODY_DIAMETER = 10;
    private final TextureAtlas atlas = new TextureAtlas("flame.atlas");

    private Sprite sprite;
    private AnimationHandle animationHandle;

    public Flame(State direction) {
        sprite = new Sprite();
        animationHandle = new AnimationHandle();

        animationHandle.addAnimation(State.FLAME_DOWN.getValue(),
                new Animation<TextureRegion>(FRAME_TIME, atlas.findRegions(State.FLAME_DOWN.getValue())));
        animationHandle.addAnimation(State.FLAME_UP.getValue(),
                new Animation<TextureRegion>(FRAME_TIME, atlas.findRegions(State.FLAME_UP.getValue())));
        animationHandle.addAnimation(State.FLAME_LEFT.getValue(),
                new Animation<TextureRegion>(FRAME_TIME, atlas.findRegions(State.FLAME_LEFT.getValue())));
        animationHandle.addAnimation(State.FLAME_RIGHT.getValue(),
                new Animation<TextureRegion>(FRAME_TIME, atlas.findRegions(State.FLAME_RIGHT.getValue())));

        animationHandle.setCurrentAnimation(direction.getValue(), false);
    }

    @Override
    public void load(LoaderParams params) {
        createCircleBody(params.position(), BODY_DIAMETER / 2);

        setCollisionFilter(BitCollision.FLAME, BitCollision.orOperation(BitCollision.BOMBERMAN, BitCollision.WALL,
                BitCollision.BRICK, BitCollision.BOMB, BitCollision.ENEMY));

        setSensor(true);

        updateSprite();
    }

    private void updateSprite() {
        float x = body.getPosition().x - Unit.pixelToMeter(BODY_DIAMETER);
        float y = body.getPosition().y - Unit.pixelToMeter(BODY_DIAMETER);

        sprite.setBounds(x, y, Unit.pixelToMeter(animationHandle.getCurrentFrame().getRegionWidth()),
                Unit.pixelToMeter(animationHandle.getCurrentFrame().getRegionHeight()));
        sprite.setRegion(animationHandle.getCurrentFrame());
    }

    @Override
    public void update(float delta) {
        updateSprite();
        if (animationHandle.isFinished()) {
            disappear();
        }
    }

    @Override
    public void render() {
        sprite.draw(Game.getInstance().batch());
    }

    public static enum State {
        FLAME_UP("flame_y"), FLAME_DOWN("flame_y"), FLAME_LEFT("flame_x"), FLAME_RIGHT("flame_x");

        String stateName;

        private State(String stateName) {
            this.stateName = stateName;
        }

        public String getValue() {
            return stateName;
        }

        public static Vector2 getOffSet(State state) {
            switch (state) {
                case FLAME_UP:
                    return new Vector2(0, 1);
                case FLAME_DOWN:
                    return new Vector2(0, -1);
                case FLAME_LEFT:
                    return new Vector2(-1, 0);
                case FLAME_RIGHT:
                    return new Vector2(1, 0);
                default:
                    return new Vector2(0, 0);
            }
        }
    }

    @Override
    public void dispose() {
        super.dispose();
        sprite.getTexture().dispose();
    }
}
