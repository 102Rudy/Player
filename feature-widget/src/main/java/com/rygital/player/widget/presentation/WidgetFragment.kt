package com.rygital.player.widget.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SeekBar
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.rygital.core.utils.SimpleSeekBarChangeListener
import com.rygital.core.utils.componentProvider
import com.rygital.player.widget.R
import com.rygital.player.widget.databinding.FragmentWidgetBinding
import com.rygital.player.widget.di.DaggerWidgetComponent
import timber.log.Timber
import javax.inject.Inject

class WidgetFragment : Fragment(), View.OnClickListener {
    @Inject
    lateinit var viewModelFactory: WidgetViewModelFactory

    private var viewModel: WidgetViewModel? = null
    private var binding: FragmentWidgetBinding? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_widget, container, false)

        Timber.i("ExplorerFragment onCreateView $this")
        DaggerWidgetComponent.builder()
                .coreAndroidApi(componentProvider.getApplicationComponent())
                .audioPlayerApi(componentProvider.getAudioPlayerComponent())
                .build()
                .inject(this)

        viewModel = ViewModelProviders.of(this, viewModelFactory).get(WidgetViewModel::class.java)

        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        registerDataListeners()
    }

    private fun registerDataListeners() {
        viewModel?.audioFile?.observe(this, Observer {
            binding?.audioFile = it
        })

        viewModel?.playerState?.observe(this, Observer {
            binding?.playerState = it
        })

        binding?.run {
            ivPlayPause.setOnClickListener(this@WidgetFragment)
            seekBar.setOnSeekBarChangeListener(object : SimpleSeekBarChangeListener {
                override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                    seekBar ?: return

                    viewModel?.seekTo(seekBar.progress / 100.0)
                }
            })
        }
    }

    /// region View.OnClickListener
    override fun onClick(view: View?) {
        when (view?.id) {
            R.id.ivPlayPause -> viewModel?.togglePlayPause()
        }
    }
    /// endregion
}