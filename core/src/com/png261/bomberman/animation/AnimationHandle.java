package com.png261.bomberman.animation;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.ObjectMap;

public final class AnimationHandle {
	private float timer = 0;
	private String currentAnimation = "";
	private boolean looping = true;
	private final ObjectMap<String, Animation<TextureRegion>> animations;

	public AnimationHandle() {
		animations = new ObjectMap<>();
	}

	public void addAnimation(String name, Animation<TextureRegion> animation) {
		animations.put(name, animation);
	}

	public void setCurrentAnimation(String name) {
		if (currentAnimation.equals(name)) {
			return;
		}

		currentAnimation = name;
		timer = 0;
		looping = true;
	}

	public void setCurrentAnimation(String name, boolean looping) {
		setCurrentAnimation(name);
		this.looping = looping;
	}

	public void setAnimationDuration(long duration) {
		animations.get(currentAnimation)
				.setFrameDuration(duration / ((float) animations.get(currentAnimation).getKeyFrames().length * 1000));
	}

	public boolean isCurrentAnimation(String name) {
		return currentAnimation.equals(name);
	}

	public boolean isFinished() {
		return animations.get(currentAnimation).isAnimationFinished(timer);
	}

	public int getCurrentFrameIndex() {
		return animations.get(currentAnimation).getKeyFrameIndex(timer);
	}

	public void setFrameToLeft() {
		for (TextureRegion textureRegion : animations.get(currentAnimation).getKeyFrames()) {
			textureRegion.flip(true, false);
		}
	}

	public TextureRegion getCurrentFrame() {
		timer += Gdx.graphics.getDeltaTime();
		return animations.get(currentAnimation).getKeyFrame(timer, looping);
	}
}
