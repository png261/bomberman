package com.png261.bomberman.utils;

public final class Util {
	public static int getRandomInRange(int min, int max) {
		return (int) ((Math.random() * (max - min)) + min);
	}

	public static short orOperation(short... bits) {
		short res = 0;
		for (short bit : bits)
			res |= bit;
		return res;
	}
}
