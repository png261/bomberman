package com.png261.bomberman;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

public final class SoundManager
{
    private static volatile SoundManager instance;
    private final Map<String, MediaPlayer> soundMap;

    private SoundManager() { soundMap = new ConcurrentHashMap<>(); }

    public static SoundManager getInstance()
    {
        if (instance == null) {
            instance = new SoundManager();
        }
        return instance;
    }

    public void load(String id, String path)
    {
        try {
            Media media = new Media(getClass().getResource(path).toExternalForm());
            MediaPlayer mediaPlayer = new MediaPlayer(media);
            soundMap.put(id, mediaPlayer);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void play(String id)
    {
        final MediaPlayer player = soundMap.get(id);
        if (player != null) {
            player.play();
        }
    }
}
