package com.png261.bomberman.object;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.png261.bomberman.Game;
import com.png261.bomberman.manager.AnimationManager;
import com.png261.bomberman.physic.BitCollision;
import com.png261.bomberman.utils.Unit;

public class Bomberman extends GameObject implements DamageableObject, ControllableObject {
    private enum State {
        IDLE_UP("idle_up"), IDLE_DOWN("idle_down"), IDLE_LEFT("idle_left"), IDLE_RIGHT("idle_right"),
        WALK_LEFT("walk_left"), WALK_RIGHT("walk_right"), WALK_UP("walk_up"), WALK_DOWN("walk_down"), DEAD("dead");

        private String value;

        private State(final String value) {
            this.value = value;
        }

        public String getValue() {
            return value;
        }
    }

    protected final float BODY_DIAMETER = 12;
    protected final float BODY_RADIUS = 6;

    protected final float FRAME_TIME = 0.6f;
    protected int health = 1;

    protected boolean isDead = false;
    protected float speed = 2.5f;
    protected AnimationManager animationManager;

    protected Sprite sprite;

    private int flameLength = 1;
    private int maxBomb = 1;
    private final Array<Bomb> bombs;
    private State direction = State.IDLE_DOWN;
    private LoaderParams params;

    private final TextureAtlas textureAtlas = new TextureAtlas(Gdx.files.internal("image/atlas/bomberman.atlas"));

    public Bomberman() {
        super();
        animationManager = new AnimationManager();
        sprite = new Sprite();
        bombs = new Array<>();
    }

    public void load(final LoaderParams params) {
        this.params = params;
        setupAnimation();
    }

    @Override
    public void createBody() {
        createCircleBody(params.position(), BODY_RADIUS);
        setCollisionFilter(BitCollision.BOMBERMAN, BitCollision.ALL);
    }

    public void idle() {
        if (isDead()) {
            return;
        }
        animationManager.setCurrentAnimation(direction.getValue());
        body.setLinearVelocity(0, 0);
    }

    @Override
    public void moveUp() {
        if (isDead()) {
            return;
        }
        this.body.setLinearVelocity(new Vector2(0, speed));
        animationManager.setCurrentAnimation(State.WALK_UP.getValue());
        direction = State.IDLE_UP;
    }

    @Override
    public void moveDown() {
        if (isDead()) {
            return;
        }
        this.body.setLinearVelocity(new Vector2(0, -speed));
        animationManager.setCurrentAnimation(State.WALK_DOWN.getValue());
        direction = State.IDLE_DOWN;
    }

    @Override
    public void moveRight() {
        if (isDead()) {
            return;
        }
        this.body.setLinearVelocity(new Vector2(speed, 0));
        animationManager.setCurrentAnimation(State.WALK_RIGHT.getValue());
        direction = State.IDLE_RIGHT;
    }

    @Override
    public void moveLeft() {
        if (isDead()) {
            return;
        }
        this.body.setLinearVelocity(new Vector2(-speed, 0));
        animationManager.setCurrentAnimation(State.WALK_LEFT.getValue());
        direction = State.IDLE_LEFT;
    }

    public void placeBomb() {
        if (bombs.size >= maxBomb) {
            return;
        }

        final Bomb bomb = new Bomb(flameLength);

        Vector2 position = body.getPosition();
        position = Unit.box2DToScreen(position, Unit.pixelToMeter(BODY_DIAMETER / 2));
        position = Unit.meterToPixel(Unit.box2DSnapToGrid(position));
        if (Game.getInstance().level().objectManager().hasBombAtPosition(position)) {
            return;
        }

        bomb.load(new LoaderParams(position));
        Game.getInstance().level().objectManager().add(bomb);
        bombs.add(bomb);
    }

    @Override
    public void update(final float delta) {
        updateSprite();
        if (isDead()) {
            if (animationManager.isCurrentAnimation(State.DEAD.getValue()) && animationManager.isFinished()) {
                disappear();
            }
            return;
        }
        handleBomb();
    }

    @Override
    public void render() {
        sprite.draw(Game.getInstance().batch());
    }

    public void handleBomb() {
        for (int i = 0; i < bombs.size; ++i) {
            if (!bombs.get(i).exist()) {
                bombs.removeIndex(i);
            }
        }
    }

    public void setupAnimation() {
        animationManager.addAnimation(State.WALK_DOWN.getValue(),
                new Animation<TextureRegion>(FRAME_TIME, textureAtlas.findRegions(State.WALK_DOWN.getValue())));

        animationManager.addAnimation(State.WALK_LEFT.getValue(),
                new Animation<TextureRegion>(FRAME_TIME, textureAtlas.findRegions(State.WALK_LEFT.getValue())));

        animationManager.addAnimation(State.WALK_RIGHT.getValue(),
                new Animation<TextureRegion>(FRAME_TIME, textureAtlas.findRegions(State.WALK_RIGHT.getValue())));

        animationManager.addAnimation(State.WALK_UP.getValue(),
                new Animation<TextureRegion>(FRAME_TIME, textureAtlas.findRegions(State.WALK_UP.getValue())));

        animationManager.addAnimation(State.IDLE_DOWN.getValue(),
                new Animation<TextureRegion>(FRAME_TIME, textureAtlas.findRegions(State.IDLE_DOWN.getValue())));

        animationManager.addAnimation(State.IDLE_LEFT.getValue(),
                new Animation<TextureRegion>(FRAME_TIME, textureAtlas.findRegions(State.IDLE_LEFT.getValue())));

        animationManager.addAnimation(State.IDLE_RIGHT.getValue(),
                new Animation<TextureRegion>(FRAME_TIME, textureAtlas.findRegions(State.IDLE_RIGHT.getValue())));

        animationManager.addAnimation(State.IDLE_UP.getValue(),
                new Animation<TextureRegion>(FRAME_TIME, textureAtlas.findRegions(State.IDLE_UP.getValue())));

        animationManager.addAnimation(State.DEAD.getValue(),
                new Animation<TextureRegion>(FRAME_TIME, textureAtlas.findRegions(State.DEAD.getValue())));

        animationManager.setCurrentAnimation(State.IDLE_DOWN.getValue());
    }

    @Override
    public void dead() {
        isDead = true;
        stopMovement();
        animationManager.setCurrentAnimation(State.DEAD.getValue());
    }

    public void speedUp(final float n) {
        speed += n;
    }

    public void flameUp(final float n) {
        flameLength += n;
    }

    public void bombUp(final float n) {
        maxBomb += n;
    }

    @Override
    public void dispose() {
        super.dispose();
    }

    @Override
    public boolean isDead() {
        return isDead;
    }

    @Override
    public void damage(final int damage) {
        health = health - damage;
        if (health <= 0) {
            dead();
        }
    }

    protected void updateSprite() {
        final float x = body.getPosition().x - Unit.pixelToMeter(BODY_RADIUS);
        final float y = body.getPosition().y - Unit.pixelToMeter(BODY_RADIUS);

        sprite.setBounds(x, y, Unit.pixelToMeter(animationManager.getCurrentFrame().getRegionWidth()),
                Unit.pixelToMeter(animationManager.getCurrentFrame().getRegionHeight()));
        sprite.setRegion(animationManager.getCurrentFrame());
    }
}
