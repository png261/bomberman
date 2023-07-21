package com.png261.bomberman.utils;

import com.badlogic.gdx.math.Vector2;

public final class Unit {
    public static final float PPM = 16;
    public static final float PPT = 16;

    private Unit() {
    }

    public static Vector2 box2DSnapToGrid(Vector2 position) {
        return new Vector2(snapMetersToGrid(position.x), snapMetersToGrid(position.y));
    }

    public static int snapMetersToGrid(float meters) {
        return (int) (metersToPixels(meters) / PPT);
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

    public static Vector2 box2DToScreen(float mX, float mY, float radius) {
        return new Vector2(mX - radius, mY - radius);
    }

    public static Vector2 box2DToScreen(float mX, float mY, float width, float height) {
        return new Vector2(box2DToScreen(mX, width), box2DToScreen(mY, height));
    }

    public static float box2DToScreen(float meters, float scale) {
        return meters - scale / 2;
    }

    public static Vector2 pixelsToMeters(float pixelX, float pixelY) {
        return new Vector2(pixelsToMeters(pixelX), pixelsToMeters(pixelY));
    }

    public static Vector2 pixelsToMeters(Vector2 v) {
        return new Vector2(pixelsToMeters(v.x), pixelsToMeters(v.y));
    }

    public static float pixelsToMeters(int pixel) {
        return pixel / PPM;
    }

    public static float pixelsToMeters(float pixel) {
        return pixelsToMeters(Math.round(pixel));
    }

    public static Vector2 metersToPixels(float mX, float mY) {
        return new Vector2(metersToPixels(mX), metersToPixels(mY));
    }

    public static Vector2 metersToPixels(Vector2 v) {
        return new Vector2(metersToPixels(v.x), metersToPixels(v.y));
    }

    public static float metersToPixels(float meters) {
        return meters * PPM;
    }

    public static float distance(Vector2 v1, Vector2 v2) {
        return v1.dst(v2);
    }
}
