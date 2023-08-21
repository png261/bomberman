package com.png261.bomberman.utils;

import com.badlogic.gdx.math.Vector2;

public final class Unit {
    public static final float PPM = 16;
    public static final float PPT = 16;

    private Unit() {
    }

    public static Vector2 box2DSnapToGrid(final Vector2 position) {
        return new Vector2(snapMeterToGrid(position.x), snapMeterToGrid(position.y));
    }

    public static int snapMeterToGrid(final float meter) {
        return (int) (meterToPixel(meter) / PPT);
    }

    public static Vector2 screenToBox2D(final float pixelX, final float pixelY, final float radius) {
        return new Vector2(pixelX + radius, pixelY + radius);
    }

    public static Vector2 screenToBox2D(final float pixelX, final float pixelY, final float width, final float height) {
        return new Vector2(screenToBox2D(pixelX, width), screenToBox2D(pixelY, height));
    }

    public static float screenToBox2D(final float px, final float scale) {
        return px + scale / 2;
    }

    public static Vector2 box2DToScreen(final Vector2 position, final float radius) {
        return box2DToScreen(position.x, position.y, radius);
    }

    public static Vector2 box2DToScreen(final Vector2 position, final float width, final float height) {
        return box2DToScreen(position.x, position.y, width, height);
    }

    public static Vector2 box2DToScreen(final float mX, final float mY, final float radius) {
        return new Vector2(mX - radius, mY - radius);
    }

    public static Vector2 box2DToScreen(final float mX, final float mY, final float width, final float height) {
        return new Vector2(box2DToScreen(mX, width), box2DToScreen(mY, height));
    }

    public static float box2DToScreen(final float meter, final float scale) {
        return meter - scale / 2;
    }

    public static Vector2 pixelToMeter(final Vector2 v) {
        return pixelToMeter(v.x, v.y);
    }

    public static Vector2 pixelToMeter(final float x, final float y) {
        return new Vector2(pixelToMeter(x), pixelToMeter(y));
    }

    public static float pixelToMeter(final int pixel) {
        return pixel / PPM;
    }

    public static float pixelToMeter(final float pixel) {
        return pixelToMeter(Math.round(pixel));
    }

    public static Vector2 meterToPixel(final float x, final float y) {
        return new Vector2(meterToPixel(x), meterToPixel(y));
    }

    public static Vector2 meterToPixel(final Vector2 v) {
        return new Vector2(meterToPixel(v.x), meterToPixel(v.y));
    }

    public static float meterToPixel(final float meter) {
        return meter * PPM;
    }

    public static float distance(final Vector2 v1, final Vector2 v2) {
        return v1.dst(v2);
    }
}
