package com.example.clockjava;

import android.media.MediaPlayer;

import com.example.clockjava.logger.Logger;

/**
 * Class used for creating {@link MediaPlayer} that plays music for wake up user.
 * This class implements singleton pattern.
 * If you want to use this class you should initialize instance with {@link #getInstance()} method.
 */
public class AlarmPlayer {

    private static AlarmPlayer instance;
    private static MediaPlayer mediaPlayer;

    /**
     * Private class constructor
     */
    private AlarmPlayer() {
        Logger.log("Initialize AlarmPlayer.class");
    }

    /**
     * Method that initializing {@link AlarmPlayer} class, and returns static class object.
     *
     * @return static class object
     */
    public static AlarmPlayer getInstance() {
        if (instance == null) {
            instance = new AlarmPlayer();
        }
        return instance;
    }

    /**
     * Method starts playing song to wake up user
     */
    public void playAlarmSong() {
        mediaPlayer = MediaPlayer.create(App.getContext(), R.raw.nature_sounds);
        mediaPlayer.setLooping(true);
        mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                if (mediaPlayer.isLooping()) {
                    mediaPlayer.seekTo(0);
                }
            }
        });
        mediaPlayer.start();
        Logger.log("AlarmPlayer start playing music");
    }

    /**
     * Method used to stop playing music. If user clicked button "I woke up" in class {@link Notificator}
     */
    public void stopPlayer() {
        if (mediaPlayer != null) {
            mediaPlayer.release();
            mediaPlayer = null;
        }
        Logger.log("AlarmPlayer stops playing music" );
    }
}
