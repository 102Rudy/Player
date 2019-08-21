package com.rygital.player.presentation.dashboard

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProviders
import com.rygital.audioplayer.domain.AudioInteractor
import com.rygital.audioplayer.domain.AudioInteractorImpl
import com.rygital.player.R
import com.rygital.player.databinding.FragmentDashboardBinding

class DashboardViewModel : ViewModel() {

//    private val audioInteractor: AudioInteractor =
//        AudioInteractorImpl

    fun onPlayClicked() {
//        audioInteractor.play()
    }
}

class DashboardFragment : Fragment(), View.OnClickListener {

    private var viewModel: DashboardViewModel? = null
    private var binding: FragmentDashboardBinding? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_dashboard, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(DashboardViewModel::class.java)

        binding?.btnPlay?.setOnClickListener(this)
    }

    override fun onClick(view: View?) {
        when (view?.id) {
            R.id.btnPlay -> viewModel?.onPlayClicked()
        }
    }
}