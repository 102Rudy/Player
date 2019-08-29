package com.rygital.audioplayer.system

import android.content.Context
import android.media.AudioManager
import com.rygital.audioplayer.di.AudioPlayerScope
import com.rygital.core.di.ApplicationContext
import javax.inject.Inject

internal data class DeviceAudioInfo(
        val sampleRate: Int,
        val bufferSize: Int
)

@AudioPlayerScope
internal class DeviceManager @Inject constructor(
        @ApplicationContext private val context: Context
) {

    companion object {
        private const val DEFAULT_SAMPLE_RATE: Int = 48000
        private const val DEFAULT_BUFFER_SIZE: Int = 512
    }

    fun getDeviceAudioInfo(): DeviceAudioInfo {
        val audioManager = context.getSystemService(Context.AUDIO_SERVICE) as AudioManager

        val sampleRate = audioManager
                .getProperty(AudioManager.PROPERTY_OUTPUT_SAMPLE_RATE)?.toInt() ?: DEFAULT_SAMPLE_RATE

        val bufferSize = audioManager
                .getProperty(AudioManager.PROPERTY_OUTPUT_FRAMES_PER_BUFFER)?.toInt() ?: DEFAULT_BUFFER_SIZE

        return DeviceAudioInfo(sampleRate, bufferSize)
    }
}