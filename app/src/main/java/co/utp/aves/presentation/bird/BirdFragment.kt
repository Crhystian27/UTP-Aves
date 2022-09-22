package co.utp.aves.presentation.bird

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import co.utp.aves.R
import co.utp.aves.base.BaseFragment
import co.utp.aves.databinding.FragmentListBirdBinding
import co.utp.aves.presentation.bird.model.Ave
import co.utp.aves.utils.fromJson
import co.utp.aves.utils.getJson
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class BirdFragment : BaseFragment<FragmentListBirdBinding,BirdViewModel>() {

    override val viewModel: BirdViewModel by viewModels()


    override fun inflateView(
        inflater: LayoutInflater,
        container: ViewGroup?
    )= FragmentListBirdBinding.inflate(inflater, container,false)


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