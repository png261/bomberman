package com.png261.bomberman.object;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.png261.bomberman.Game;
import com.png261.bomberman.animation.AnimationHandle;
import com.png261.bomberman.level.Level;
import com.png261.bomberman.physic.BitCollision;
import com.png261.bomberman.utils.Unit;

public class Bomb extends GameObject {
    private final float BODY_DIAMETER = 16;
    private final float FRAME_TIME = 0.6f;
    private final TextureAtlas atlas = new TextureAtlas("bomb.atlas");

    private int flameLength = 3;
    private AnimationHandle animationHandle;
    private Sprite sprite;

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

    @Override
    public void load(LoaderParams params) {
        createCircleBody(params.position(), BODY_DIAMETER / 2);
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
        sprite.draw(Game.getInstance().batch());
    }

    private void updateSprite() {
        sprite.setBounds(Unit.box2DToScreen(body.getPosition().x, Unit.pixelsToMeters(BODY_DIAMETER)),
                Unit.box2DToScreen(body.getPosition().y, Unit.pixelsToMeters(BODY_DIAMETER)),
                Unit.pixelsToMeters(animationHandle.getCurrentFrame().getRegionWidth()),
                Unit.pixelsToMeters(animationHandle.getCurrentFrame().getRegionHeight()));

        sprite.setRegion(animationHandle.getCurrentFrame());
    }

    private void explode() {
        animationHandle.setCurrentAnimation(State.BOMB_EXPLODE.getValue());
        final Level level = Game.getInstance().level();

        for (Flame.State direction : Flame.State.values()) {
            for (int i = 1; i <= flameLength; ++i) {
                Vector2 position = body.getPosition().add(Flame.State.getOffSet(direction).scl(i));
                Vector2 positionPixel = Unit.metersToPixels(position.x, position.y);

                if (level.isPositionOnWall(positionPixel)) {
                    break;
                }

                Flame flame = new Flame(direction);
                flame.load(new LoaderParams(Unit.box2DSnapToGrid(position), 16, 16));
                level.spawnObject(flame);

                if (level.isPositionOnBrick(positionPixel)) {
                    break;
                }
            }
        }

        Flame flame = new Flame(Flame.State.FLAME_UP);
        flame.load(new LoaderParams(Unit.box2DSnapToGrid(body.getPosition()), 16, 16));
        level.spawnObject(flame);

        setSensor(true);
    }
}
