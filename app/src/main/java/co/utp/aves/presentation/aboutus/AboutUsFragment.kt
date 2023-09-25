package co.utp.aves.presentation.aboutus

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import co.utp.aves.R
import co.utp.aves.base.BaseFragment
import co.utp.aves.databinding.FragmentAboutUsBinding
import co.utp.aves.presentation.BirdEvent
import co.utp.aves.presentation.BirdViewModel
import co.utp.aves.presentation.aboutus.adapter.AboutUsAdapter
import co.utp.aves.presentation.aboutus.adapter.AboutUsClick
import co.utp.aves.presentation.aboutus.adapter.AboutUsDependenciesAdapter
import co.utp.aves.presentation.aboutus.adapter.AboutUsDependenciesClick
import co.utp.aves.presentation.model.AboutUs
import co.utp.aves.presentation.model.AboutUsDependencies
import co.utp.aves.utils.addTabs
import com.google.android.material.tabs.TabLayout
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class AboutUsFragment : BaseFragment<FragmentAboutUsBinding, BirdViewModel>(), AboutUsClick,
    AboutUsDependenciesClick {

    override val viewModel: BirdViewModel by viewModels()


    override fun inflateView(
        inflater: LayoutInflater, container: ViewGroup?
    ) = FragmentAboutUsBinding.inflate(inflater, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.getAboutUs()
    }


    override fun setUI() {
        setToolbarActivity(
            getString(R.string.title_about_us_fragment), null, R.color.utp_blue
        )
    }

    override fun setListeners() {
        binding.apply {

            val collaborators = getString(R.string.string_collaborators)
            val dependencies = getString(R.string.string_dependencies)

            tlAboutUs.addTabs(
                listOf(
                    collaborators, dependencies
                )
            )

            tlAboutUs.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener{
                override fun onTabSelected(tab: TabLayout.Tab) {
                    when(tab.text){
                        collaborators -> {rvAboutUs.visibility = View.VISIBLE}
                        dependencies -> {rvAboutUsDependencies.visibility = View.VISIBLE
                            infoDependencies.visibility = View.VISIBLE}
                    }

                }

                override fun onTabUnselected(tab: TabLayout.Tab) {
                    when(tab.text){
                        collaborators -> {rvAboutUs.visibility = View.GONE}
                        dependencies -> {rvAboutUsDependencies.visibility = View.GONE
                            infoDependencies.visibility = View.GONE}
                    }
                }

                override fun onTabReselected(tab: TabLayout.Tab) {}
            })

            infoDependencies.setOnClickListener {
                showSimpleDialog()
            }

        }

    }

    private fun showSimpleDialog() {
        val dialogBuilder = AlertDialog.Builder(requireContext())
        dialogBuilder.setTitle("Recuerda.")
        dialogBuilder.setMessage("Esta app se elaboró con el apoyo de la Vicerrectoría de Investigaciones, Innovación y Extensión de la Universidad Tecnológica de Pereira.\n\nCon el nombre de proyecto:\nÁrea urbana de Pereira:\nTerritorio de aves, sonidos y colores.\nCódigo: 2-23-6.")
        dialogBuilder.create().show()
    }

    private fun setAdapterAboutUs(aboutUs: List<AboutUs>) {
        with(binding.rvAboutUs) {

            if (adapter == null) {
                layoutManager = LinearLayoutManager(
                    requireContext(), LinearLayoutManager.VERTICAL, false
                )

                adapter = AboutUsAdapter(this@AboutUsFragment)
            }

            (adapter as? AboutUsAdapter)?.submitList(aboutUs)
        }
    }


    private fun setAdapterAboutUsDependencies(aboutUsDependencies: List<AboutUsDependencies>) {
        with(binding.rvAboutUsDependencies) {

            if (adapter == null) {
                layoutManager = GridLayoutManager(
                    requireContext(), 2
                )

                adapter = AboutUsDependenciesAdapter(this@AboutUsFragment)
            }

            (adapter as? AboutUsDependenciesAdapter)?.submitList(aboutUsDependencies)

        }
    }


    override fun observe() {
        viewModel.event.observe(viewLifecycleOwner) { event ->
            when (event) {
                is BirdEvent.ListAboutUs -> {
                    setAdapterAboutUs(event.listAboutUs)
                    viewModel.getAboutUsDependencies()
                }
                is BirdEvent.ListAboutUsDependencies -> {
                    setAdapterAboutUsDependencies(event.listAboutUsDependencies)
                }
                else -> {}
            }
        }
    }

    override fun onClickAboutUs(aboutUs: AboutUs) {
        findNavController().navigate(
            AboutUsFragmentDirections.actionAboutUsToAboutUsDetail(
                aboutUs.LinkedIn
            )
        )
    }

    override fun onClickAboutUsDependencies(aboutUsDependencies: AboutUsDependencies) {
        findNavController().navigate(
            AboutUsFragmentDirections.actionAboutUsToAboutUsDetail(
                aboutUsDependencies.Url
            )
        )
    }
}