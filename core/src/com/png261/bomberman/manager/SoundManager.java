package com.png261.bomberman.manager;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.utils.Disposable;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

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

    public void loadMusic(String id, String filePath) {
        if (musicMap.get(id) != null) {
            return;
        }

        manager.load(filePath, Music.class);
        musicMap.putIfAbsent(id, filePath);
        manager.finishLoading();
    }

    public void loadSound(String id, String filePath) {
        if (soundMap.get(id) != null) {
            return;
        }

        manager.load(filePath, Music.class);
        soundMap.putIfAbsent(id, filePath);
        manager.finishLoading();
    }

    public Music getMusic(String id) {
        String filePath = musicMap.get(id);
        if (filePath == null) {
            return null;
        }

        return manager.get(filePath, Music.class);
    }

    public Sound getSound(String id) {
        String filePath = soundMap.get(id);
        if (filePath == null) {
            return null;
        }

        return manager.get(filePath, Sound.class);
    }

    public void playMusic(String id, boolean isLooping) {
        Music music = getMusic(id);
        if (music != null) {
            music.setLooping(isLooping);
            music.play();
        }
    }

    public void playSound(String id) {
        Sound sound = getSound(id);
        if (sound != null) {
            sound.play();
        }
    }

    public void stopMusic(String id) {
        Music music = getMusic(id);
        if (music != null && music.isPlaying()) {
            music.stop();
        }
    }

    public void pauseMusic(String id) {
        Music music = getMusic(id);
        if (music != null && music.isPlaying()) {
            music.pause();
        }
    }

    @Override
    public void dispose() {
        manager.clear();
    }
}
