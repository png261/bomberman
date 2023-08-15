package com.png261.bomberman.manager;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.ObjectMap;

public final class AnimationManager {
	private float timer = 0;
	private String currentAnimation = "";
	private boolean looping = true;
	private final ObjectMap<String, Animation<TextureRegion>> animations;

	public AnimationManager() {
		animations = new ObjectMap<>();
	}

	public void addAnimation(final String name, final Animation<TextureRegion> animation) {
		animations.put(name, animation);
	}

	public void setCurrentAnimation(final String name) {
		if (currentAnimation.equals(name)) {
			return;
		}

		currentAnimation = name;
		timer = 0;
		looping = true;
	}

	public void setCurrentAnimation(final String name, final boolean looping) {
		setCurrentAnimation(name);
		this.looping = looping;
	}

	public void setAnimationDuration(final long duration) {
		animations.get(currentAnimation)
				.setFrameDuration(duration / ((float) animations.get(currentAnimation).getKeyFrames().length * 1000));
	}

	public boolean isCurrentAnimation(final String name) {
		return currentAnimation.equals(name);
	}

	public boolean isFinished() {
		return animations.get(currentAnimation).isAnimationFinished(timer);
	}

	public int getCurrentFrameIndex() {
		return animations.get(currentAnimation).getKeyFrameIndex(timer);
	}

	public void setFrameToLeft() {
		for (final TextureRegion textureRegion : animations.get(currentAnimation).getKeyFrames()) {
			textureRegion.flip(true, false);
		}
	}

	public TextureRegion getCurrentFrame() {
		timer += Gdx.graphics.getDeltaTime();
		return animations.get(currentAnimation).getKeyFrame(timer, looping);
	}
}
