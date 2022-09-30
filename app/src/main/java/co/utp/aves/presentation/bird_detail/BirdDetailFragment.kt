package co.utp.aves.presentation.bird_detail

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import co.utp.aves.R
import co.utp.aves.base.BaseFragment
import co.utp.aves.databinding.FragmentBirdDetailBinding
import co.utp.aves.presentation.BirdViewModel
import co.utp.aves.presentation.model.Ave
import co.utp.aves.utils.AVE_ITEM
import co.utp.aves.utils.loadDrawable
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class BirdDetailFragment: BaseFragment<FragmentBirdDetailBinding,BirdViewModel>() {

    override val viewModel: BirdViewModel by viewModels()

    var ave : Ave? =null

    override fun inflateView(
        inflater: LayoutInflater,
        container: ViewGroup?
    )= FragmentBirdDetailBinding.inflate(inflater,container,false)

    override fun getBundleArgs() {
        arguments?.let{
            ave = it.getParcelable(AVE_ITEM)
        }
    }

    override fun setUI() {
        ave?.Nombre_Comun?.let {
            setToolbarActivity(
                it,
                R.color.white,
                R.color.utp_blue
            )
        }
        /**
         *
        "Nombre_Ingles": "Cauca guan",

         */

        with(binding){

            val drawable = ContextCompat.getDrawable(root.context,root.resources.getIdentifier(ave?.Imagen_Ave!!.replace(".webp",""),"drawable",root.context.packageName))

            loadDrawable(imgBirdDetail,drawable!!,root.context)
            val title = "${ave?.Nombre_Cientifico}"
            val titleFeature = "Caracteristicas"
            val featureBody = "• Distribucion: ${ave?.Distribucion}\n• Familia: ${ave?.Familia}\n• Orden: ${ave?.Orden}\n• Tamaño: ${ave?.Tamano} "
            val vocalization = "${ave?.Vocalizacion}"
            titleBirdDetail.text = title
            titleFeatureBirdDetail.text = titleFeature
            featureBirdDetail.text = featureBody
            titleSoundBirdDetail.text = vocalization
        }
    }




    override fun observe() {}


}