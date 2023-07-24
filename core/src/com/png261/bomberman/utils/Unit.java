package com.png261.bomberman.utils;

import com.badlogic.gdx.math.Vector2;

public final class Unit {
    public static final float PPM = 16;
    public static final float PPT = 16;

    private Unit() {
    }

    public static Vector2 box2DSnapToGrid(Vector2 position) {
        return new Vector2(snapMeterToGrid(position.x), snapMeterToGrid(position.y));
    }

    public static int snapMeterToGrid(float meter) {
        return (int) (meterToPixel(meter) / PPT);
    }

    public static Vector2 screenToBox2D(float pixelX, float pixelY, float radius) {
        return new Vector2(pixelX + radius, pixelY + radius);
    }

    public static Vector2 screenToBox2D(float pixelX, float pixelY, float width, float height) {
        return new Vector2(screenToBox2D(pixelX, width), screenToBox2D(pixelY, height));
    }

    public static float screenToBox2D(float px, float scale) {
        return px + scale / 2;
    }

    public static Vector2 box2DToScreen(Vector2 position, float radius) {
        return box2DToScreen(position.x, position.y, radius);
    }

    public static Vector2 box2DToScreen(Vector2 position, float width, float height) {
        return box2DToScreen(position.x, position.y, width, height);
    }

    public static Vector2 box2DToScreen(float mX, float mY, float radius) {
        return new Vector2(mX - radius, mY - radius);
    }

    public static Vector2 box2DToScreen(float mX, float mY, float width, float height) {
        return new Vector2(box2DToScreen(mX, width), box2DToScreen(mY, height));
    }

    public static float box2DToScreen(float meter, float scale) {
        return meter - scale / 2;
    }

    public static Vector2 pixelToMeter(Vector2 v) {
        return pixelToMeter(v.x, v.y);
    }

    public static Vector2 pixelToMeter(float x, float y) {
        return new Vector2(pixelToMeter(x), pixelToMeter(y));
    }

    public static float pixelToMeter(int pixel) {
        return pixel / PPM;
    }

    public static float pixelToMeter(float pixel) {
        return pixelToMeter(Math.round(pixel));
    }

    public static Vector2 meterToPixel(float x, float y) {
        return new Vector2(meterToPixel(x), meterToPixel(y));
    }

    public static Vector2 meterToPixel(Vector2 v) {
        return new Vector2(meterToPixel(v.x), meterToPixel(v.y));
    }

    public static float meterToPixel(float meter) {
        return meter * PPM;
    }

    public static float distance(Vector2 v1, Vector2 v2) {
        return v1.dst(v2);
    }
}
