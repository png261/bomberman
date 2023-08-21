package com.png261.bomberman.object;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.png261.bomberman.Game;
import com.png261.bomberman.manager.AnimationManager;
import com.png261.bomberman.physic.BitCollision;
import com.png261.bomberman.physic.PhysicManager;
import com.png261.bomberman.utils.Unit;
import box2dLight.PointLight;

public class Flame extends GameObject {
    private final float FRAME_TIME = 0.3f;
    private final float BODY_DIAMETER = 10;
    private final TextureAtlas atlas = new TextureAtlas(Gdx.files.internal("image/atlas/flame.atlas"));

    private final Sprite sprite;
    private final AnimationManager animationManager;

    private PointLight pointLight;

    public Flame(final State direction) {
        sprite = new Sprite();
        animationManager = new AnimationManager();

        animationManager.addAnimation(State.DOWN.getValue(),
                new Animation<TextureRegion>(FRAME_TIME, atlas.findRegions(State.DOWN.getValue())));
        animationManager.addAnimation(State.UP.getValue(),
                new Animation<TextureRegion>(FRAME_TIME, atlas.findRegions(State.UP.getValue())));
        animationManager.addAnimation(State.LEFT.getValue(),
                new Animation<TextureRegion>(FRAME_TIME, atlas.findRegions(State.LEFT.getValue())));
        animationManager.addAnimation(State.RIGHT.getValue(),
                new Animation<TextureRegion>(FRAME_TIME, atlas.findRegions(State.RIGHT.getValue())));

        animationManager.setCurrentAnimation(direction.getValue(), false);
        pointLight = new PointLight(PhysicManager.getInstance().getRayHandler(), 50, new Color(0.5f, 0.5f, 0.5f, 1.0f),
                2f, 0, 0);
        pointLight.setSoft(true);
        pointLight.setSoftnessLength(2.0f);
    }

    @Override
    public void createBody() {
        createCircleBody(params.position(), BODY_DIAMETER / 2);
        setCollisionFilter(BitCollision.FLAME, BitCollision.ALL);
        setSensor(true);
        pointLight.attachToBody(getBody());
    }

    private void updateSprite() {
        final float x = body.getPosition().x - Unit.pixelToMeter(BODY_DIAMETER);
        final float y = body.getPosition().y - Unit.pixelToMeter(BODY_DIAMETER);

        sprite.setBounds(x, y, Unit.pixelToMeter(animationManager.getCurrentFrame().getRegionWidth()),
                Unit.pixelToMeter(animationManager.getCurrentFrame().getRegionHeight()));
        sprite.setRegion(animationManager.getCurrentFrame());
    }

    @Override
    public void update(final float delta) {
        updateSprite();
        if (animationManager.isFinished()) {
            pointLight.setDistance(0);
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
