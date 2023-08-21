package com.png261.bomberman.utils;

public final class Util {
	public static int getRandomInRange(final int min, final int max) {
		return (int) ((Math.random() * (max - min)) + min);
	}
}
