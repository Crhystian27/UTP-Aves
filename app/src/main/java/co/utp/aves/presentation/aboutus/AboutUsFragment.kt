package co.utp.aves.presentation.aboutus

import android.content.ActivityNotFoundException
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import co.utp.aves.R
import co.utp.aves.base.BaseFragment
import co.utp.aves.databinding.FragmentAboutUsBinding
import co.utp.aves.presentation.BirdEvent
import co.utp.aves.presentation.BirdViewModel
import co.utp.aves.presentation.aboutus.adapter.AboutUsAdapter
import co.utp.aves.presentation.aboutus.adapter.AboutUsClick
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
            null,
            R.color.utp_blue
        )

        with(binding){
            aboutUsBotanicGarden.setOnClickListener {
                //Open url
            }
            aboutUsNyquist.setOnClickListener {
                //open url
            }
        }
    }

    override fun setListeners() {

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

    override fun onClickAboutUsEmail(aboutUs: AboutUs) {
        val i = Intent(Intent.ACTION_SEND)
        i.type = "message/rfc822"
        i.putExtra(Intent.EXTRA_EMAIL, aboutUs.Email)
        i.putExtra(Intent.EXTRA_SUBJECT, "subject of email")
        i.putExtra(Intent.EXTRA_TEXT, "")
        try {
            startActivity(Intent.createChooser(i, "Send mail..."))
        } catch (ex: ActivityNotFoundException) {
            Toast.makeText(
                context,
                "There are no email clients installed.",
                Toast.LENGTH_SHORT
            ).show()
        }
    }

}