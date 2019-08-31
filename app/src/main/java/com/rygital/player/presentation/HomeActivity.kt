package com.rygital.player.presentation

import android.os.Bundle
import android.os.StrictMode
import androidx.appcompat.app.AppCompatActivity
import com.rygital.core.utils.componentProvider
import com.rygital.player.BuildConfig
import com.rygital.player.R
import com.rygital.player.explorer.presentation.ExplorerFragment
import com.rygital.player.widget.presentation.WidgetFragment


class HomeActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        if (BuildConfig.DEBUG) {
            enableStrictMode()
        }

        if (savedInstanceState == null) {
            supportFragmentManager
                    .beginTransaction()
                    .replace(R.id.homeContainer, ExplorerFragment())
                    .disallowAddToBackStack()
                    .commit()

            supportFragmentManager
                    .beginTransaction()
                    .add(R.id.bottomSheet, WidgetFragment())
                    .disallowAddToBackStack()
                    .commit()
        }
    }

    private fun enableStrictMode() {
        val threadPolicy = StrictMode.ThreadPolicy.Builder()
                .detectAll()
                .penaltyLog()
                .build()

        val vmPolicy = StrictMode.VmPolicy.Builder()
                .detectAll()
                .penaltyLog()
                .build()

        StrictMode.setThreadPolicy(threadPolicy)
        StrictMode.setVmPolicy(vmPolicy)
    }

    override fun onResume() {
        super.onResume()
        componentProvider.getAudioPlayerComponent().audioInteractor().onForeground()
    }

    override fun onPause() {
        super.onPause()
        componentProvider.getAudioPlayerComponent().audioInteractor().onBackground()
    }
}