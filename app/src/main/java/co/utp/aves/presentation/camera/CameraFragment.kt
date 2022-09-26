package co.utp.aves.presentation.camera

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import co.utp.aves.base.BaseFragment
import co.utp.aves.databinding.FragmentCameraBinding
import co.utp.aves.presentation.BirdViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CameraFragment : BaseFragment<FragmentCameraBinding, BirdViewModel>() {

    override val viewModel: BirdViewModel by viewModels()


    override fun inflateView(
        inflater: LayoutInflater,
        container: ViewGroup?
    )= FragmentCameraBinding.inflate(inflater, container,false)



    override fun setListeners() {
        super.setListeners()
    }

    override fun observe() {

    }


}