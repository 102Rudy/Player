package com.rygital.audiolibrary

class AudioLibWrapper {

    companion object {
        private const val LIBRARY_NAME: String = "audio-lib"
    }

    fun loadLibrary() {
        System.loadLibrary(LIBRARY_NAME)
    }

    external fun initialize(sampleRate: Int, bufferSize: Int)
    external fun setAudioFileEndCallback(listener: AudioFileEndListener)
    external fun onBackground()
    external fun onForeground()

    external fun openAudioFile(pathToFile: String, fileOffset: Int, fileLength: Int)
    external fun play()
    external fun pause()
}