package com.png261.bomberman.manager;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.utils.Disposable;

public final class SoundManager implements Disposable {
    private static volatile SoundManager instance;

    private final AssetManager manager;
    private final Map<String, String> musicMap;
    private final Map<String, String> soundMap;

    public static SoundManager getInstance() {
        if (instance == null) {
            instance = new SoundManager();
        }
        return instance;
    }

    private SoundManager() {
        manager = new AssetManager();
        musicMap = new ConcurrentHashMap<>();
        soundMap = new ConcurrentHashMap<>();
    }

    public void loadMusic(final String id, final String filePath) {
        if (musicMap.get(id) != null) {
            return;
        }

        manager.load(filePath, Music.class);
        musicMap.putIfAbsent(id, filePath);
        manager.finishLoading();
    }

    public void loadSound(final String id, final String filePath) {
        if (soundMap.get(id) != null) {
            return;
        }

        manager.load(filePath, Music.class);
        soundMap.putIfAbsent(id, filePath);
        manager.finishLoading();
    }

    public Music getMusic(final String id) {
        final String filePath = musicMap.get(id);
        if (filePath == null) {
            return null;
        }

        return manager.get(filePath, Music.class);
    }

    public Sound getSound(final String id) {
        final String filePath = soundMap.get(id);
        if (filePath == null) {
            return null;
        }

        return manager.get(filePath, Sound.class);
    }

    public void playMusic(final String id, final boolean isLooping) {
        final Music music = getMusic(id);
        if (music != null) {
            music.setLooping(isLooping);
            music.play();
        }
    }

    public void playSound(final String id) {
        final Sound sound = getSound(id);
        if (sound != null) {
            sound.play();
        }
    }

    public void stopMusic(final String id) {
        final Music music = getMusic(id);
        if (music != null && music.isPlaying()) {
            music.stop();
        }
    }

    public void pauseMusic(final String id) {
        final Music music = getMusic(id);
        if (music != null && music.isPlaying()) {
            music.pause();
        }
    }

    @Override
    public void dispose() {
        manager.clear();
    }
}
