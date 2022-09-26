package co.utp.aves.presentation.bird

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import co.utp.aves.R
import co.utp.aves.base.BaseFragment
import co.utp.aves.databinding.FragmentBirdBinding
import co.utp.aves.presentation.BirdEvent
import co.utp.aves.presentation.BirdViewModel
import co.utp.aves.presentation.model.Ave
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class BirdFragment : BaseFragment<FragmentBirdBinding, BirdViewModel>() {

    override val viewModel: BirdViewModel by viewModels()


    override fun inflateView(
        inflater: LayoutInflater,
        container: ViewGroup?
    )= FragmentBirdBinding.inflate(inflater, container,false)


    override fun setUI() {
        setToolbarActivity(getString(R.string.title),
            R.color.white,
            R.color.utp_blue
        )

    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.getBirds()
    }

    override fun setListeners() {
        super.setListeners()
    }

    private fun setAdapter(birds: List<Ave>){

    }

    override fun observe() {
        viewModel.event.observe(viewLifecycleOwner){ event ->
            when (event) {
                is BirdEvent.ListBird -> {
                    setAdapter(event.result)
                }
                else -> {}
            }
        }
    }


}