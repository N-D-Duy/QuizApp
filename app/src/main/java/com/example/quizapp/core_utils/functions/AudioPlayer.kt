package com.example.quizapp.core_utils.functions

import android.media.MediaPlayer

class AudioPlayer {
    private var mediaPlayer: MediaPlayer? = null

    operator fun invoke(
        url: String,
        isPlaying: Boolean,
        onFinished: () -> Unit
    ) {
        try {
            if (mediaPlayer == null) {
                mediaPlayer = MediaPlayer()
                mediaPlayer?.setDataSource(url)
                mediaPlayer?.prepareAsync()
                mediaPlayer?.setOnPreparedListener {
                    if (isPlaying) {
                        mediaPlayer?.start()
                    }
                }
                mediaPlayer?.setOnCompletionListener {
                    onFinished()
                }
            } else {
                if (isPlaying) {
                    if (!mediaPlayer!!.isPlaying) {
                        mediaPlayer?.start()
                    }
                } else {
                    mediaPlayer?.pause()
                    mediaPlayer?.seekTo(0) // Reset audio về đầu
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}
