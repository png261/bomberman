package com.png261.bomberman.object;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Vector2;
import com.png261.bomberman.Game;
import com.png261.bomberman.manager.AnimationManager;
import com.png261.bomberman.level.Level;
import com.png261.bomberman.physic.BitCollision;
import com.png261.bomberman.utils.Unit;

public class Bomb extends GameObject {
    private final float BODY_DIAMETER = 16;
    private final float BODY_RADIUS = 8;
    private final float FRAME_TIME = 0.6f;
    private TextureAtlas atlas;

    private float timeCountDown = 2f;
    private boolean isExploded = false;

    private final int flameLength;
    private final AnimationManager animationManager;
    private final Sprite sprite;
    private LoaderParams params;
    private Circle bounds;

    private enum State {
        IDLE("idle"), EXPLODE("explode");

        String value;

        private State(final String value) {
            this.value = value;
        }

        public String getValue() {
            return value;
        }
    }

    public Bomb(final int flameLength) {
        this.flameLength = flameLength;

        sprite = new Sprite();
        animationManager = new AnimationManager();
    }

    @Override
    public void load(final LoaderParams params) {
        this.params = params;

        bounds = new Circle();
        bounds.setPosition(params.position());
        bounds.setRadius(BODY_RADIUS);

        atlas = new TextureAtlas(Gdx.files.internal("image/atlas/bomb.atlas"));
        animationManager.addAnimation(State.IDLE.getValue(),
                new Animation<TextureRegion>(FRAME_TIME, atlas.findRegions(State.IDLE.getValue())));
        animationManager.addAnimation(State.EXPLODE.getValue(),
                new Animation<TextureRegion>(FRAME_TIME, atlas.findRegions(State.EXPLODE.getValue())));
        animationManager.setCurrentAnimation(State.IDLE.getValue());
    }

    @Override
    public void createBody() {
        createCircleBody(params.position(), BODY_RADIUS);
        setCollisionFilter(BitCollision.BOMB, BitCollision.ALL);
        setSensor(true);

    }

    @Override
    public void update(final float delta) {
        updateSprite();

        if (timeCountDown <= 0 && isExploded == false) {
            explode();
        }

        timeCountDown -= delta;

        if (animationManager.isCurrentAnimation(State.EXPLODE.getValue()) && animationManager.isFinished()) {
            disappear();
        }
    }

    @Override
    public void render() {
        sprite.draw(Game.getInstance().batch());
    }

    private void updateSprite() {
        final float x = body.getPosition().x - Unit.pixelToMeter(BODY_RADIUS);
        final float y = body.getPosition().y - Unit.pixelToMeter(BODY_RADIUS);

        sprite.setBounds(x, y, Unit.pixelToMeter(animationManager.getCurrentFrame().getRegionWidth()),
                Unit.pixelToMeter(animationManager.getCurrentFrame().getRegionHeight()));
        sprite.setRegion(animationManager.getCurrentFrame());
    }

    private void explode() {
        animationManager.setCurrentAnimation(State.EXPLODE.getValue());
        createFlame();
        setSensor(true);
        isExploded = true;
    }

    private void createFlame() {
        final Level level = Game.getInstance().level();
        final Flame middleFlame = new Flame(Flame.State.UP);
        middleFlame.load(new LoaderParams(Unit.box2DToScreen(Unit.meterToPixel(body.getPosition()),
                BODY_DIAMETER / 4)));
        level.objectManager().add(middleFlame);

        for (final Flame.State direction : Flame.State.values()) {
            for (int i = 1; i <= flameLength; ++i) {
                Vector2 position = body.getPosition();
                position.add(Flame.State.getOffSet(direction).scl(i));
                position = Unit.box2DSnapToGrid(Unit.box2DToScreen(Unit.meterToPixel(position),
                        BODY_DIAMETER / 4));

                if (level.objectManager().hasWallAtPosition(position)) {
                    break;
                }

                final Flame flame = new Flame(direction);
                flame.load(new LoaderParams(position));
                level.objectManager().add(flame);

                if (level.objectManager().hasBrickAtPosition(position)) {
                    break;
                }
            }
        }
    }

    public boolean contains(final Vector2 position) {
        return bounds.contains(position);
    }

}
