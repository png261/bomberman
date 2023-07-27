package com.png261.bomberman.object.person.bomberman;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.png261.bomberman.Game;
import com.png261.bomberman.utils.Unit;
import com.png261.bomberman.object.Bomb;
import com.png261.bomberman.object.LoaderParams;
import com.png261.bomberman.object.person.Person;
import com.png261.bomberman.physic.BitCollision;

public class Bomberman extends Person {
    private enum State {
        IDLE_UP("idle_up"), IDLE_DOWN("idle_down"), IDLE_LEFT("idle_left"), IDLE_RIGHT("idle_right"),
        WALK_LEFT("walk_left"), WALK_RIGHT("walk_right"), WALK_UP("walk_up"), WALK_DOWN("walk_down"), DEAD("dead");

        private String value;

        private State(String value) {
            this.value = value;
        }

        public String getValue() {
            return value;
        }
    }

    private int flameLength = 1;
    private int maxBomb = 1;
    private Array<Bomb> bombs;
    private State direction = State.IDLE_DOWN;

    private TextureAtlas textureAtlas = new TextureAtlas(Gdx.files.internal("bomberman.atlas"));

    public Bomberman() {
        super();
        bombs = new Array<>();
    }

    public void load(LoaderParams params) {
        super.load(params);

        setCollisionFilter(BitCollision.BOMBERMAN, BitCollision.orOperation(BitCollision.WALL, BitCollision.BRICK,
                BitCollision.BOMB, BitCollision.FLAME, BitCollision.ENEMY, BitCollision.ITEM));

        setupAnimation();
    }

    public void idle() {
        if (isDead()) {
            return;
        }
        animationHandle.setCurrentAnimation(direction.getValue());
        body.setLinearVelocity(0, 0);
    }

    @Override
    public void moveUp() {
        if (isDead()) {
            return;
        }
        super.moveUp();
        animationHandle.setCurrentAnimation(State.WALK_UP.getValue());
        direction = State.IDLE_UP;
    }

    @Override
    public void moveDown() {
        if (isDead()) {
            return;
        }
        super.moveDown();
        animationHandle.setCurrentAnimation(State.WALK_DOWN.getValue());
        direction = State.IDLE_DOWN;
    }

    @Override
    public void moveRight() {
        if (isDead()) {
            return;
        }
        super.moveRight();
        animationHandle.setCurrentAnimation(State.WALK_RIGHT.getValue());
        direction = State.IDLE_RIGHT;
    }

    @Override
    public void moveLeft() {
        if (isDead()) {
            return;
        }
        super.moveLeft();
        animationHandle.setCurrentAnimation(State.WALK_LEFT.getValue());
        direction = State.IDLE_LEFT;
    }

    public void placeBomb() {
        if (bombs.size >= maxBomb) {
            return;
        }
        System.out.println("place bomb");

        System.out.println("before new");
        Bomb bomb = new Bomb(flameLength);
        System.out.println("after new");

        Vector2 position = body.getPosition();
        position = Unit.box2DToScreen(position, Unit.pixelToMeter(BODY_DIAMETER / 2));
        position = Unit.meterToPixel(Unit.box2DSnapToGrid(position));
        System.out.println("before bomb load");
        bomb.load(new LoaderParams(position));
        System.out.println("before leve add");
        Game.getInstance().level().addObject(bomb);
        System.out.println("after level add");
        bombs.add(bomb);
        System.out.println("after place bomb");
    }

    @Override
    public void update(float delta) {
        super.update(delta);
        handleBomb();
    }

    public void handleBomb() {
        for (int i = 0; i < bombs.size; ++i) {
            if (!bombs.get(i).exist()) {
                bombs.removeIndex(i);
            }
        }
    }

    public void setupAnimation() {
        animationHandle.addAnimation(State.WALK_DOWN.getValue(),
                new Animation<TextureRegion>(FRAME_TIME, textureAtlas.findRegions(State.WALK_DOWN.getValue())));

        animationHandle.addAnimation(State.WALK_LEFT.getValue(),
                new Animation<TextureRegion>(FRAME_TIME, textureAtlas.findRegions(State.WALK_LEFT.getValue())));

        animationHandle.addAnimation(State.WALK_RIGHT.getValue(),
                new Animation<TextureRegion>(FRAME_TIME, textureAtlas.findRegions(State.WALK_RIGHT.getValue())));

        animationHandle.addAnimation(State.WALK_UP.getValue(),
                new Animation<TextureRegion>(FRAME_TIME, textureAtlas.findRegions(State.WALK_UP.getValue())));

        animationHandle.addAnimation(State.IDLE_DOWN.getValue(),
                new Animation<TextureRegion>(FRAME_TIME, textureAtlas.findRegions(State.IDLE_DOWN.getValue())));

        animationHandle.addAnimation(State.IDLE_LEFT.getValue(),
                new Animation<TextureRegion>(FRAME_TIME, textureAtlas.findRegions(State.IDLE_LEFT.getValue())));

        animationHandle.addAnimation(State.IDLE_RIGHT.getValue(),
                new Animation<TextureRegion>(FRAME_TIME, textureAtlas.findRegions(State.IDLE_RIGHT.getValue())));

        animationHandle.addAnimation(State.IDLE_UP.getValue(),
                new Animation<TextureRegion>(FRAME_TIME, textureAtlas.findRegions(State.IDLE_UP.getValue())));

        animationHandle.addAnimation(State.DEAD.getValue(),
                new Animation<TextureRegion>(FRAME_TIME + 1, textureAtlas.findRegions(State.DEAD.getValue())));

        animationHandle.setCurrentAnimation(State.IDLE_DOWN.getValue());
    }

    @Override
    public void dead() {
        super.dead();
        stopMovement();
        animationHandle.setCurrentAnimation(State.DEAD.getValue());
    }

    public void speedUp(float n) {
        speed += n;
    }

    public void flameUp(float n) {
        flameLength += n;
    }

    public void bombUp(float n) {
        maxBomb += n;
    }
}
