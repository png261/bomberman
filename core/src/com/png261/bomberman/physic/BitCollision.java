package com.png261.bomberman.physic;

public final class BitCollision {
    public static final short NULL = 0x000;
    public static final short BOMBERMAN = 0x001;
    public static final short WALL = 0x002;
    public static final short BRICK = 0x004;
    public static final short ENEMY = 0x008;
    public static final short BOMB = 0x010;
    public static final short FLAME = 0x020;
    public static final short ITEM = 0x040;
    public static final short ALL = 0xFFF;

    public static short orOperation(short... bits) {
        short res = 0;
        for (short bit : bits)
            res |= bit;
        return res;
    }
}
