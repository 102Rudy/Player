package com.rygital.player.explorer.presentation

import android.content.pm.PackageManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.afollestad.materialdialogs.MaterialDialog
import com.rygital.core.presentation.PermissionDialogExplanationViewData
import com.rygital.core.utils.componentProvider
import com.rygital.player.explorer.R
import com.rygital.player.explorer.databinding.FragmentExplorerBinding
import com.rygital.player.explorer.di.DaggerExplorerComponent
import timber.log.Timber
import javax.inject.Inject


class ExplorerFragment : Fragment() {

    @Inject
    lateinit var viewModelFactory: ExplorerViewModelFactory

    private var viewModel: ExplorerViewModel? = null
    private var binding: FragmentExplorerBinding? = null

    private val adapter by lazy {
        ExplorerAdapter { audioFile -> viewModel?.playAudioFile(audioFile) }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_explorer, container, false)

        Timber.i("ExplorerFragment onCreateView $this")
        DaggerExplorerComponent.builder()
                .coreAndroidApi(componentProvider.coreAndroidApi)
                .databaseApi(componentProvider.databaseApi)
                .audioPlayerApi(componentProvider.audioPlayerApi)
                .build()
                .inject(this)

        viewModel = ViewModelProviders.of(this, viewModelFactory).get(ExplorerViewModel::class.java)

        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding?.run {
            swipeRefresh?.run {
                setOnRefreshListener { viewModel?.refreshAudioFiles() }
            }

            rvExplorer?.run {
                layoutManager = LinearLayoutManager(requireContext())
                setHasFixedSize(true)
                adapter = this@ExplorerFragment.adapter
            }
        }

        registerDataListeners()

        viewModel?.getAudioFiles()
    }

    private fun registerDataListeners() {
        viewModel?.run {
            showRequestPermissionDialog.observe(this@ExplorerFragment, Observer {
                it.getContentIfNotHandled()?.let { (permission, requestCode) ->
                    tryToRequestPermission(permission, requestCode)
                }
            })

            showPermissionExplanationDialog.observe(this@ExplorerFragment, Observer {
                it.getContentIfNotHandled()?.let { data -> showPermissionExplanationDialog(data) }
            })

            audioFiles.observe(this@ExplorerFragment, Observer {
                adapter.setItems(it)
            })

            showProgress.observe(this@ExplorerFragment, Observer {
                binding?.swipeRefresh?.isRefreshing = it
            })
        }
    }

    /// region Permission methods
    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        if ((grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED)) {
            viewModel?.onPermissionGranted(requestCode)
        } else {
            viewModel?.onPermissionDenied(requestCode)
        }
    }

    private fun tryToRequestPermission(permission: String, requestCode: Int) {
        if (ContextCompat.checkSelfPermission(requireActivity(), permission) != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(requireActivity(), permission)) {
                viewModel?.onShouldShowPermissionExplanation(permission, requestCode)
            } else {
                requestPermission(permission, requestCode)
            }
        } else {
            viewModel?.onPermissionGranted(requestCode)
        }
    }

    private fun showPermissionExplanationDialog(data: PermissionDialogExplanationViewData) {
        MaterialDialog(requireContext()).show {
            title(text = data.dialogTitle)
            message(text = data.dialogMessage)
            negativeButton(text = data.negativeText)
            positiveButton(text = data.positiveText) {
                requestPermission(data.permission, data.requestCode)
            }

            cancelOnTouchOutside(false)
        }
    }

    private fun requestPermission(permission: String, requestCode: Int) {
        requestPermissions(arrayOf(permission), requestCode)
    }
    /// endregion
}