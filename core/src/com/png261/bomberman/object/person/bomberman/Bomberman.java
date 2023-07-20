package com.png261.bomberman.object.person.bomberman;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.png261.bomberman.Game;
import com.png261.bomberman.animation.AnimationHandle;
import com.png261.bomberman.object.Bomb;
import com.png261.bomberman.object.person.Person;
import com.png261.bomberman.physic.BitCollision;
import com.png261.bomberman.utils.Unit;

public class Bomberman extends Person
{
    private int flameLength = 1;
    private int maxBomb = 1;
    private State direction = State.IDLE_DOWN;
    private static final TextureAtlas textureAtlas = new TextureAtlas("bomberman.atlas");
    private boolean canPlaceBomb = true;

    private enum State {
        IDLE_UP("idle_up"),
        IDLE_DOWN("idle_down"),
        IDLE_LEFT("idle_left"),
        IDLE_RIGHT("idle_right"),
        WALK_LEFT("walk_left"),
        WALK_RIGHT("walk_right"),
        WALK_UP("walk_up"),
        WALK_DOWN("walk_down"),
        DEAD("dead");

        private String value;

        private State(String value) { this.value = value; }

        public String getValue() { return value; }
    }


    public Bomberman() { super(); }

    public void load(Vector2 position)
    {
        super.load(position);

        setCollisionFilter(
            BitCollision.BOMBERMAN,
            BitCollision.orOperation(
                BitCollision.WALL,
                BitCollision.BRICK,
                BitCollision.BOMB,
                BitCollision.FLAME,
                BitCollision.ENEMY,
                BitCollision.ITEM));

        setupAnimation();
    }

    public void handleEvents()
    {
        if (isDead()) {
            return;
        }

        if (Gdx.input.isKeyPressed(Input.Keys.SPACE)) {
            // placeBomb();
        } else if (Gdx.input.isKeyPressed(Input.Keys.W)) {
            moveUp();
            animationHandle.setCurrentAnimation(State.WALK_UP.getValue());
            direction = State.IDLE_UP;
        } else if (Gdx.input.isKeyPressed(Input.Keys.S)) {
            moveDown();
            animationHandle.setCurrentAnimation(State.WALK_DOWN.getValue());
            direction = State.IDLE_DOWN;
        } else if (Gdx.input.isKeyPressed(Input.Keys.A)) {
            moveLeft();
            animationHandle.setCurrentAnimation(State.WALK_LEFT.getValue());
            direction = State.IDLE_LEFT;
        } else if (Gdx.input.isKeyPressed(Input.Keys.D)) {
            moveRight();
            animationHandle.setCurrentAnimation(State.WALK_RIGHT.getValue());
            direction = State.IDLE_RIGHT;
        } else {
            animationHandle.setCurrentAnimation(direction.getValue());
            body.setLinearVelocity(0, 0);
        }
    }

    public void placeBomb()
    {
        if (!canPlaceBomb) {
            return;
        }

        canPlaceBomb = false;
        Bomb bomb = new Bomb();
        bomb.load(body.getPosition());
        Game.getInstance().getLevel().spawnObject(bomb);
    }

    @Override public void update(float delta)
    {
        super.update(delta);
        handleEvents();
    }

    public void setupAnimation()
    {
        animationHandle.addAnimation(
            State.WALK_DOWN.getValue(),
            new Animation<TextureRegion>(
                FRAME_TIME,
                textureAtlas.findRegions(State.WALK_DOWN.getValue())));

        animationHandle.addAnimation(
            State.WALK_LEFT.getValue(),
            new Animation<TextureRegion>(
                FRAME_TIME,
                textureAtlas.findRegions(State.WALK_LEFT.getValue())));

        animationHandle.addAnimation(
            State.WALK_RIGHT.getValue(),
            new Animation<TextureRegion>(
                FRAME_TIME,
                textureAtlas.findRegions(State.WALK_RIGHT.getValue())));

        animationHandle.addAnimation(
            State.WALK_UP.getValue(),
            new Animation<TextureRegion>(
                FRAME_TIME,
                textureAtlas.findRegions(State.WALK_UP.getValue())));

        animationHandle.addAnimation(
            State.IDLE_DOWN.getValue(),
            new Animation<TextureRegion>(
                FRAME_TIME,
                textureAtlas.findRegions(State.IDLE_DOWN.getValue())));

        animationHandle.addAnimation(
            State.IDLE_LEFT.getValue(),
            new Animation<TextureRegion>(
                FRAME_TIME,
                textureAtlas.findRegions(State.IDLE_LEFT.getValue())));

        animationHandle.addAnimation(
            State.IDLE_RIGHT.getValue(),
            new Animation<TextureRegion>(
                FRAME_TIME,
                textureAtlas.findRegions(State.IDLE_RIGHT.getValue())));

        animationHandle.addAnimation(
            State.IDLE_UP.getValue(),
            new Animation<TextureRegion>(
                FRAME_TIME,
                textureAtlas.findRegions(State.IDLE_UP.getValue())));

        animationHandle.addAnimation(
            State.DEAD.getValue(),
            new Animation<TextureRegion>(
                FRAME_TIME + 1,
                textureAtlas.findRegions(State.DEAD.getValue())));

        animationHandle.setCurrentAnimation(State.IDLE_DOWN.getValue());
    }

    @Override public void dead()
    {
        super.dead();
        animationHandle.setCurrentAnimation(State.DEAD.getValue());
    }

    public void speedUp(float n) { speed += n; }
    public void flameUp(float n) { flameLength += n; }
    public void bombUp(float n) { maxBomb += n; }
}
