package com.png261.bomberman.physic;

public class BitCollision
{
    public static final short NULL = 0;
    public static final short BOMBERMAN = 1;
    public static final short WALL = 2;
    public static final short BRICK = 4;
    public static final short ENEMY = 8;
    public static final short BOMB = 16;
    public static final short FLAME = 32;
    public static final short ITEM = 64;

    public static short orOperation(short... bits)
    {
        short res = 0;
        for (short bit : bits) res |= bit;
        return res;
    }
}
