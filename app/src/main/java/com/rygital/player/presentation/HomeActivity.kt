package com.rygital.player.presentation

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.rygital.player.R
import com.rygital.player.explorer.presentation.ExplorerFragment


class HomeActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        if (savedInstanceState == null) {
            supportFragmentManager
                    .beginTransaction()
                    .replace(R.id.homeContainer, ExplorerFragment())
                    .disallowAddToBackStack()
                    .commit()
        }
    }
}