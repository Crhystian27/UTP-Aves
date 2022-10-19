package co.utp.aves.presentation.aboutus

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import co.utp.aves.R
import co.utp.aves.base.BaseFragment
import co.utp.aves.databinding.FragmentAboutUsBinding
import co.utp.aves.presentation.BirdEvent
import co.utp.aves.presentation.BirdViewModel
import co.utp.aves.presentation.aboutus.adapter.AboutUsAdapter
import co.utp.aves.presentation.aboutus.adapter.AboutUsClick
import co.utp.aves.presentation.bird.BirdFragmentDirections
import co.utp.aves.presentation.bird.adapter.BirdAdapter
import co.utp.aves.presentation.model.AboutUs
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AboutUsFragment : BaseFragment<FragmentAboutUsBinding, BirdViewModel>(), AboutUsClick {

    override val viewModel: BirdViewModel by viewModels()


    override fun inflateView(
        inflater: LayoutInflater,
        container: ViewGroup?
    )= FragmentAboutUsBinding.inflate(inflater, container,false)


    override fun setUI() {
        setToolbarActivity(getString(R.string.title_about_us_fragment),
            R.color.white,
            R.color.utp_blue
        )
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.getAboutUs()
    }

    private fun setAdapter(aboutUs: List<AboutUs>){
        with(binding.rvAboutUs){

            if (adapter == null) {
                layoutManager = LinearLayoutManager(
                    requireContext(),
                   LinearLayoutManager.VERTICAL,
                    false
                )

                adapter = AboutUsAdapter(this@AboutUsFragment)
            }

            (adapter as? AboutUsAdapter)?.submitList(aboutUs)
        }
    }


    override fun observe() {
        viewModel.event.observe(viewLifecycleOwner){ event ->
            when(event){
                is BirdEvent.ListAboutUs -> {
                    setAdapter(event.listAboutUs)
                }
                else -> {}
            }
        }

    }

    override fun onClickAboutUs(aboutUs: AboutUs) {
        findNavController().navigate(
            AboutUsFragmentDirections.actionAboutUsToAboutUsDetail(
                aboutUs
            )
        )
    }


}