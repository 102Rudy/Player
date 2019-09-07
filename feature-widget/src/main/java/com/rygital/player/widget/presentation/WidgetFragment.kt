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

    companion object {
        const val LENGTH = 1000
    }

    @Inject
    lateinit var viewModelFactory: WidgetViewModelFactory

    private var viewModel: WidgetViewModel? = null
    private var binding: FragmentWidgetBinding? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_widget, container, false)

        Timber.i("ExplorerFragment onCreateView $this")
        DaggerWidgetComponent.builder()
                .coreAndroidApi(componentProvider.coreAndroidApi)
                .audioPlayerApi(componentProvider.audioPlayerApi)
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
        binding?.run {
            ivPlayPause.setOnClickListener(this@WidgetFragment)
            seekBar.max = LENGTH
            seekBar.setOnSeekBarChangeListener(object : SimpleSeekBarChangeListener {
                override fun onStartTrackingTouch(seekBar: SeekBar?) {
                    viewModel?.setEnabledUpdatePosition(false)
                }

                override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                    if (seekBar != null && fromUser) {
                        viewModel?.seekTo(seekBar.progress / LENGTH.toDouble())
                    }
                }

                override fun onStopTrackingTouch(seekBar: SeekBar?) {
                    viewModel?.setEnabledUpdatePosition(true)
                }
            })
        }

        viewModel?.audioFile?.observe(this, Observer {
            binding?.audioFile = it
        })

        viewModel?.playerState?.observe(this, Observer {
            binding?.playerState = it
        })

        viewModel?.position?.observe(this, Observer {
            binding?.seekBar?.progress = (it * LENGTH).toInt()
        })
    }

    /// region View.OnClickListener
    override fun onClick(view: View?) {
        when (view?.id) {
            R.id.ivPlayPause -> viewModel?.togglePlayPause()
        }
    }
    /// endregion
}