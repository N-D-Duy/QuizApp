package com.example.quizapp.core_utils.text_to_speech

import android.content.Context
import android.speech.tts.TextToSpeech
import android.speech.tts.UtteranceProgressListener
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner
import java.util.Locale

class TTSListener(
    private val context: Context,
    private val word: String,
    private val isPlayingState: Boolean,
    private val onFinished: () -> Unit
) : DefaultLifecycleObserver {

    private var textToSpeechEngine: TextToSpeech? = null
    private var isSpeaking: Boolean = false

    init {
        setupTextToSpeechEngine()
    }

    // This function is called when the TTSListener is created
    private fun setupTextToSpeechEngine() {
        textToSpeechEngine = TextToSpeech(context) { status ->
            if (status == TextToSpeech.SUCCESS) {
                textToSpeechEngine?.language = Locale.US
                startSpeaking()

            }
        }
    }

    private fun startSpeaking() {
        if (!isSpeaking) {
            isSpeaking = true
            textToSpeechEngine?.speak(word, TextToSpeech.QUEUE_FLUSH, null, "tts1")
        }
    }

    private fun stop() {
        if (isSpeaking) {
            isSpeaking = false
            textToSpeechEngine?.stop()
            onFinished()
        }
    }

    private fun onSpeechCompleted() {
        stop()
    }

    override fun onStart(owner: LifecycleOwner) {
        super.onStart(owner)
        textToSpeechEngine?.setOnUtteranceProgressListener(object : UtteranceProgressListener() {
            override fun onStart(utteranceId: String?) {
            }

            override fun onDone(utteranceId: String?) {
                onSpeechCompleted()
            }

            @Deprecated("Deprecated in Java")
            override fun onError(utteranceId: String?) {
            }
        })
    }

    override fun onStop(owner: LifecycleOwner) {
        super.onStop(owner)
        stop()
    }

    override fun onDestroy(owner: LifecycleOwner) {
        super.onDestroy(owner)
        textToSpeechEngine?.shutdown()
    }
}


