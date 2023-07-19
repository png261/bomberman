package com.png261.bomberman.object;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Filter;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.png261.bomberman.Game;
import com.png261.bomberman.animation.AnimationHandle;
import com.png261.bomberman.object.Object;
import com.png261.bomberman.object.person.bomberman.Bomberman;
import com.png261.bomberman.physic.*;
import com.png261.bomberman.utils.Unit;
import java.util.ArrayList;

public class Bomb extends Object
{
    private final float BODY_DIAMETER = 0.95f;
    private final float FRAME_TIME = 0.6f;

    private int flameLength = 3;
    private AnimationHandle animationHandle;
    private Sprite sprite;
    public float countDown = 1.5f;
    private Bomberman bombOwner;
    public float timeRemove;
    public boolean canMove = true;
    public boolean canKick = false;

    private enum State {
        BOMB_IDLE("bomb_idle"),
        BOMB_EXPLODE("bomb_explode");

        String value;

        private State(String value) { this.value = value; }

        public String getValue() { return value; }
    }

    public Bomb()
    {
        TextureAtlas atlas = new TextureAtlas("bomb.atlas");
        animationHandle = new AnimationHandle();
        animationHandle.addAnimation(
            State.BOMB_IDLE.getValue(),
            new Animation<TextureRegion>(
                FRAME_TIME,
                atlas.findRegions(State.BOMB_IDLE.getValue())));
        animationHandle.addAnimation(
            State.BOMB_EXPLODE.getValue(),
            new Animation<TextureRegion>(
                FRAME_TIME,
                atlas.findRegions(State.BOMB_EXPLODE.getValue())));
        animationHandle.setCurrentAnimation(State.BOMB_IDLE.getValue());
        sprite = new Sprite(animationHandle.getCurrentFrame());
    }

    public void load(Vector2 position)
    {
        createCircleBody(new Circle(position, BODY_DIAMETER / 2), true);
        setCollisionFilter(
            BitCollision.BOMB,
            BitCollision.orOperation(
                BitCollision.BOMBERMAN,
                BitCollision.WALL,
                BitCollision.BRICK,
                BitCollision.FLAME,
                BitCollision.ENEMY,
                BitCollision.BOMB));
    }

    public void updateSprite()
    {
        sprite.setBounds(
            Unit.box2DToScreen(body.getPosition().x, BODY_DIAMETER),
            Unit.box2DToScreen(body.getPosition().y, BODY_DIAMETER),
            Unit.pixelsToMeters(animationHandle.getCurrentFrame().getRegionWidth()),
            Unit.pixelsToMeters(animationHandle.getCurrentFrame().getRegionHeight()));

        sprite.setRegion(animationHandle.getCurrentFrame());
    }

    private void explode()
    {
        animationHandle.setCurrentAnimation(State.BOMB_EXPLODE.getValue());

        for (Flame.State direction : Flame.State.values()) {
            for (int i = 0; i <= flameLength; ++i) {
                Vector2 position = Unit.coordBox2DSnapToGrid(
                    body.getPosition().add(Flame.State.getOffSet(direction).scl(i)));

                Vector2 nextPosition =
                    body.getPosition().add(Flame.State.getOffSet(direction).scl(i + 1));

                Vector2 nextPositionPixel =
                    Unit.coordMetersToPixels(nextPosition.x, nextPosition.y);


                if (i != 0) {
                    Game.getInstance().getLevel().spawnObject(new Flame(position, direction));
                }

                if (Game.getInstance().getLevel().isBrick(nextPositionPixel)) {
                    Game.getInstance().getLevel().spawnObject(
                        new Flame(Unit.coordBox2DSnapToGrid(nextPosition), direction));
                    break;
                }

                if (Game.getInstance().getLevel().isWall(nextPositionPixel)) {
                    break;
                }
            }
        }
        Game.getInstance().getLevel().spawnObject(
            new Flame(Unit.coordBox2DSnapToGrid(body.getPosition()), Flame.State.FLAME_UP));
    }
    @Override public void update(float delta)
    {
        updateSprite();
        if (Gdx.input.isKeyPressed(Input.Keys.P)) {
            explode();
        }
        if (animationHandle.isCurrentAnimation(State.BOMB_EXPLODE.getValue())
            && animationHandle.isFinished()) {
            disappear();
        }
    }
    @Override public void render() { sprite.draw(Game.getInstance().getBatch()); }
}
