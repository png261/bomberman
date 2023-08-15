package com.png261.bomberman.object;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.png261.bomberman.Game;
import com.png261.bomberman.manager.AnimationManager;
import com.png261.bomberman.physic.BitCollision;
import com.png261.bomberman.utils.Unit;

public class Flame extends GameObject {
    private final float FRAME_TIME = 0.3f;
    private final float BODY_DIAMETER = 10;
    private final TextureAtlas atlas = new TextureAtlas(Gdx.files.internal("image/atlas/flame.atlas"));

    private final Sprite sprite;
    private final AnimationManager animationHandle;
    private LoaderParams params;

    public Flame(final State direction) {
        sprite = new Sprite();
        animationHandle = new AnimationManager();

        animationHandle.addAnimation(State.DOWN.getValue(),
                new Animation<TextureRegion>(FRAME_TIME, atlas.findRegions(State.DOWN.getValue())));
        animationHandle.addAnimation(State.UP.getValue(),
                new Animation<TextureRegion>(FRAME_TIME, atlas.findRegions(State.UP.getValue())));
        animationHandle.addAnimation(State.LEFT.getValue(),
                new Animation<TextureRegion>(FRAME_TIME, atlas.findRegions(State.LEFT.getValue())));
        animationHandle.addAnimation(State.RIGHT.getValue(),
                new Animation<TextureRegion>(FRAME_TIME, atlas.findRegions(State.RIGHT.getValue())));

        animationHandle.setCurrentAnimation(direction.getValue(), false);
    }

    @Override
    public void load(final LoaderParams params) {
        this.params = params;
    }

    @Override
    public void createBody() {
        createCircleBody(params.position(), BODY_DIAMETER / 2);
        setCollisionFilter(BitCollision.FLAME, BitCollision.ALL);
        setSensor(true);
    }

    private void updateSprite() {
        final float x = body.getPosition().x - Unit.pixelToMeter(BODY_DIAMETER);
        final float y = body.getPosition().y - Unit.pixelToMeter(BODY_DIAMETER);

        sprite.setBounds(x, y, Unit.pixelToMeter(animationHandle.getCurrentFrame().getRegionWidth()),
                Unit.pixelToMeter(animationHandle.getCurrentFrame().getRegionHeight()));
        sprite.setRegion(animationHandle.getCurrentFrame());
    }

    @Override
    public void update(final float delta) {
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
        UP("y"), DOWN("y"), LEFT("x"), RIGHT("x");

        String stateName;

        private State(final String stateName) {
            this.stateName = stateName;
        }

        public String getValue() {
            return stateName;
        }

        public static Vector2 getOffSet(final State state) {
            switch (state) {
                case UP:
                    return new Vector2(0, 1);
                case DOWN:
                    return new Vector2(0, -1);
                case LEFT:
                    return new Vector2(-1, 0);
                case RIGHT:
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
