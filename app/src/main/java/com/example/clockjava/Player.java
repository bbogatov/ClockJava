package com.example.clockjava;

import android.media.MediaPlayer;

/**
 * Class used for creating {@link MediaPlayer} that plays music for wake up user.
 * This class implements singleton pattern.
 * If you want to use this class you should initialize player with {@link #init()} method.
 */
class Player {
    private static Player player;
    private MediaPlayer mediaPlayer;

    /**
     * Private class constructor
     */
    private Player() {
        Logger.log("Initialize Player.class");
    }

    /**
     * Method that initializing {@link Player} class, and returns static class object.
     *
     * @return static class object
     */
    static Player init() {
        if (player == null) {
            player = new Player();
        }
        return player;
    }

    /**
     * Method starts playing song to wake up user
     */
    void playAlarmSong() {
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
        Logger.log("Player start playing music");
    }

    /**
     * Method used to stop playing music. If user clicked button "I woke up" in class {@link Notificator}
     */
    void stopPlayer() {
        if (mediaPlayer != null) {
            mediaPlayer.release();
            mediaPlayer = null;
        }
        Logger.log("Player stops playing music");
    }
}
