package com.png261.bomberman.manager;

import com.png261.bomberman.util.*;
import java.io.*;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import javax.sound.sampled.*;

public final class SoundManager
{
    private static volatile SoundManager instance;
    private final Map<String, Clip> musicMap;
    private final Map<String, Clip> sfxMap;

    private SoundManager()
    {
        musicMap = new ConcurrentHashMap<>();
        sfxMap = new ConcurrentHashMap<>();
    }

    public static SoundManager getInstance()
    {
        if (instance == null) {
            instance = new SoundManager();
        }
        return instance;
    }
    public void loadMusic(String id, String path)
    {
        try (
            final InputStream inputStream = getClass().getClassLoader().getResourceAsStream(path)) {
            if (inputStream != null) {
                AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(inputStream);
                Clip clip = AudioSystem.getClip();
                clip.open(audioInputStream);
                musicMap.put(id, clip);
            } else {
                Log.error("Failed to load music: " + path);
            }
        } catch (Exception e) {
            Log.error("Failed to load music: " + path);
            e.printStackTrace();
        }
    }

    public void playMusic(String id)
    {
        final Clip clip = musicMap.get(id);
        if (clip != null && !clip.isRunning()) {
            clip.start();
        }
    }

    // public void loadSFX(String id, String path) {}
    // public void playSFX(String id) {}
}
