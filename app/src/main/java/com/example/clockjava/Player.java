package com.example.clockjava;

import android.media.MediaPlayer;

public class Player {
    private static Player player;
    private static MediaPlayer mediaPlayer;

    private Player() {
        System.out.println("Init player");
    }

    public static Player init() {
        if (player == null) {
            player = new Player();

        }
        return player;
    }

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
    }

    void stopPlayer() {
        if (mediaPlayer != null) {
            mediaPlayer.release();
            mediaPlayer = null;
        }
    }
}
