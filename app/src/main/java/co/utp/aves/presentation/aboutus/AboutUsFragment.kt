package co.utp.aves.presentation.aboutus

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import co.utp.aves.base.BaseFragment
import co.utp.aves.databinding.FragmentAboutUsBinding
import co.utp.aves.presentation.BirdViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AboutUsFragment : BaseFragment<FragmentAboutUsBinding, BirdViewModel>() {

    override val viewModel: BirdViewModel by viewModels()


    override fun inflateView(
        inflater: LayoutInflater,
        container: ViewGroup?
    )= FragmentAboutUsBinding.inflate(inflater, container,false)



    override fun setListeners() {
        super.setListeners()
    }

    override fun observe() {
        //TODO("Not yet implemented")
    }


}