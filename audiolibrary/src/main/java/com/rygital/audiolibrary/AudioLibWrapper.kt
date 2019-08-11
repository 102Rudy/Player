package com.rygital.audiolibrary

class AudioLibWrapper {

    companion object {

        private const val LIBRARY_NAME: String = "audio-lib"

        init {
            System.loadLibrary(LIBRARY_NAME)
        }
    }

    external fun stringFromJNI(): String

}