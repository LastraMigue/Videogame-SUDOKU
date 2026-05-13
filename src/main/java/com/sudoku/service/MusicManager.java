package com.sudoku.service;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.AudioClip;
import java.net.URL;

/**
 * Singleton service to manage background music and volume levels across the application.
 */
public class MusicManager {
    private static MusicManager instance;
    private MediaPlayer mediaPlayer;
    private AudioClip clickSound;
    private double volume = 0.5;

    private MusicManager() {
        try {
            URL musicResource = getClass().getResource("/music/music.mp3");
            if (musicResource != null) {
                Media media = new Media(musicResource.toExternalForm());
                mediaPlayer = new MediaPlayer(media);
                mediaPlayer.setCycleCount(MediaPlayer.INDEFINITE);
                mediaPlayer.setVolume(volume);
                mediaPlayer.play();
            }

            URL clickResource = getClass().getResource("/music/mouse_click.mp3");
            if (clickResource != null) {
                clickSound = new AudioClip(clickResource.toExternalForm());
            }
        } catch (Exception e) {
            System.err.println("Could not load music: " + e.getMessage());
        }
    }

    public static MusicManager getInstance() {
        if (instance == null) {
            instance = new MusicManager();
        }
        return instance;
    }

    public void setVolume(double volume) {
        this.volume = volume;
        if (mediaPlayer != null) {
            mediaPlayer.setVolume(volume);
        }
    }

    public double getVolume() {
        return volume;
    }

    public void play() {
        if (mediaPlayer != null) mediaPlayer.play();
    }

    public void pause() {
        if (mediaPlayer != null) mediaPlayer.pause();
    }

    public void playClickSound() {
        if (clickSound != null) {
            clickSound.play(volume);
        }
    }
}
