package com.rygital.player.presentation.explorer

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.rygital.audioplayer.di.DaggerAudioPlayerComponent
import com.rygital.player.ApplicationComponentProvider
import com.rygital.player.R
import com.rygital.player.databinding.FragmentExplorerBinding
import com.rygital.player.di.DaggerExplorerComponent
import timber.log.Timber
import javax.inject.Inject


class ExplorerFragment : Fragment() {

    @Inject
    lateinit var viewModelFactory: ExplorerViewModelFactory

    private var viewModel: ExplorerViewModel? = null
    private var binding: FragmentExplorerBinding? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_explorer, container, false)

        Timber.i("ExplorerFragment onCreateView $this")
        DaggerExplorerComponent.builder()
                .coreAndroidApi((requireActivity().applicationContext as ApplicationComponentProvider)
                        .getApplicationComponent())
                .audioPlayerApi(
                        DaggerAudioPlayerComponent.builder()
                                .coreAndroidApi(
                                        (requireActivity().applicationContext as ApplicationComponentProvider)
                                                .getApplicationComponent()
                                )
                                .build()
                )
                .build()
                .inject(this)

        viewModel = ViewModelProviders.of(this, viewModelFactory).get(ExplorerViewModel::class.java)

        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val explorerAdapter = ExplorerAdapter()
        viewModel?.getAudioFiles()?.let {
            explorerAdapter.setItems(it)
        }

        binding?.rvExplorer?.run {
            layoutManager = LinearLayoutManager(requireContext())
            setHasFixedSize(true)
            adapter = explorerAdapter
        }
    }
}