package co.utp.aves.presentation.bird_detail

import android.annotation.SuppressLint
import android.content.res.AssetFileDescriptor
import android.media.MediaPlayer
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import co.utp.aves.R
import co.utp.aves.base.BaseFragment
import co.utp.aves.databinding.FragmentBirdDetailBinding
import co.utp.aves.presentation.BirdViewModel
import co.utp.aves.presentation.bird.adapter.*
import co.utp.aves.presentation.model.Ave
import co.utp.aves.utils.AVE_ITEM
import co.utp.aves.utils.LogDebug
import co.utp.aves.utils.LogError
import co.utp.aves.utils.loadDrawable
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class BirdDetailFragment : BaseFragment<FragmentBirdDetailBinding, BirdViewModel>(), SoundClick {

    override val viewModel: BirdViewModel by viewModels()

    var ave: Ave? = null

    override fun inflateView(
        inflater: LayoutInflater,
        container: ViewGroup?
    ) = FragmentBirdDetailBinding.inflate(inflater, container, false)

    override fun getBundleArgs() {
        arguments?.let {
            ave = it.getParcelable(AVE_ITEM)
        }
    }

    override fun setListeners() {
        with(binding.imgBirdDetail){
            setOnClickListener {  }
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

        with(binding) {

            val drawable = ContextCompat.getDrawable(
                root.context,
                root.resources.getIdentifier(
                    ave?.Imagen_Ave!!.replace(".webp", ""),
                    "drawable",
                    root.context.packageName
                )
            )

            loadDrawable(imgBirdDetail, drawable!!, root.context)
            val title = "${ave?.Nombre_Cientifico}"
            val titleFeature = "Caracteristicas"
            val featureBody =
                "• Distribucion: ${ave?.Distribucion}\n• Familia: ${ave?.Familia}\n• Orden: ${ave?.Orden}\n• Tamaño: ${ave?.Tamano} "
            val vocalization = "${ave?.Vocalizacion}"
            titleBirdDetail.text = title
            titleFeatureBirdDetail.text = titleFeature
            featureBirdDetail.text = featureBody
            titleSoundBirdDetail.text = vocalization
            titleFoodBirdDetail.text = "Alimentación"

            if (ave?.Sonidos.isNullOrEmpty()) {
                rvSoundBird.visibility = View.GONE
            }

            ave?.Sonidos?.let { setSoundAdapter(it) }
            ave?.Alimentos?.let { setFoodAdapter(it) }
        }
    }


    @SuppressLint("NotifyDataSetChanged")
    private fun setFoodAdapter(food: List<String>) {
        with(binding.rvFoodBird) {

            if (adapter == null) {
                layoutManager = LinearLayoutManager(
                    requireContext(),
                    LinearLayoutManager.HORIZONTAL,
                    false
                )

                adapter = FoodAdapter()
            }

            (adapter as? FoodAdapter)?.submitList(food)

        }
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun setSoundAdapter(sound: List<String>) {
        with(binding.rvSoundBird) {

            if (adapter == null) {
                layoutManager = LinearLayoutManager(
                    requireContext(),
                    LinearLayoutManager.HORIZONTAL,
                    false
                )

                adapter = SoundAdapter(this@BirdDetailFragment)
            }

            (adapter as? SoundAdapter)?.submitList(sound)

        }
    }


    override fun observe() {}

    override fun onClickSound(sound: String, view: ProgressBar) {
        playAssetSound(sound, view)
    }

    private fun playAssetSound(assetName: String, view: ProgressBar) {

        val mMediaPlayer = MediaPlayer()
        val afd: AssetFileDescriptor = requireContext().assets.openFd("sounds/$assetName")
        view.visibility = View.VISIBLE

        try {
            mMediaPlayer.setDataSource(afd.fileDescriptor, afd.startOffset, afd.length)
            afd.close()
            mMediaPlayer.prepare()
            mMediaPlayer.start()
        } catch (ex: Exception) {
            ex.toString().LogDebug()
        }


    }

}