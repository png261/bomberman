package com.png261.bomberman.object;

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
    private final float BODY_RADIUS = 8;
    private final float FRAME_TIME = 0.6f;
    private final TextureAtlas atlas = new TextureAtlas("bomb.atlas");
    private float timeCountDown = 2f;

    private int flameLength;
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

    public Bomb(int flameLength) {
        this.flameLength = flameLength;

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
        createCircleBody(params.position(), BODY_RADIUS);
        setCollisionFilter(BitCollision.BOMB, BitCollision.orOperation(BitCollision.BOMBERMAN, BitCollision.WALL,
                BitCollision.BRICK, BitCollision.FLAME, BitCollision.ENEMY, BitCollision.BOMB));
        setSensor(true);
    }

    @Override
    public void update(float delta) {
        updateSprite();

        timeCountDown -= delta;

        if (timeCountDown <= 0) {
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
        float x = body.getPosition().x - Unit.pixelToMeter(BODY_RADIUS);
        float y = body.getPosition().y - Unit.pixelToMeter(BODY_RADIUS);

        sprite.setBounds(x, y, Unit.pixelToMeter(animationHandle.getCurrentFrame().getRegionWidth()),
                Unit.pixelToMeter(animationHandle.getCurrentFrame().getRegionHeight()));
        sprite.setRegion(animationHandle.getCurrentFrame());
    }

    private void explode() {
        animationHandle.setCurrentAnimation(State.BOMB_EXPLODE.getValue());
        createFlame();
        setSensor(true);
    }

    private void createFlame() {
        final Level level = Game.getInstance().level();
        Flame middleFlame = new Flame(Flame.State.FLAME_UP);
        middleFlame.load(new LoaderParams(Unit.box2DToScreen(Unit.meterToPixel(body.getPosition()),
                BODY_DIAMETER / 4)));
        level.addObject(middleFlame);

        for (Flame.State direction : Flame.State.values()) {
            for (int i = 1; i <= flameLength; ++i) {
                Vector2 position = body.getPosition();
                position.add(Flame.State.getOffSet(direction).scl(i));
                position = Unit.box2DSnapToGrid(Unit.box2DToScreen(Unit.meterToPixel(position),
                        BODY_DIAMETER / 4));

                if (level.isPositionOnWall(position)) {
                    break;
                }

                Flame flame = new Flame(direction);
                flame.load(new LoaderParams(position));
                level.addObject(flame);

                if (level.isPositionOnBrick(position)) {
                    break;
                }
            }
        }
    }

}
