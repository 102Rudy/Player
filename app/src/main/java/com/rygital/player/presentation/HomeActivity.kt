package com.rygital.player.presentation

import android.os.Bundle
import android.os.StrictMode
import androidx.appcompat.app.AppCompatActivity
import com.rygital.player.BuildConfig
import com.rygital.player.R
import com.rygital.player.explorer.presentation.ExplorerFragment


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
}