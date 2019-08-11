package com.rygital.player

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.rygital.audiolibrary.AudioLibWrapper
import timber.log.Timber

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        Timber.i("string from JNI: ${AudioLibWrapper().stringFromJNI()}")
    }
}