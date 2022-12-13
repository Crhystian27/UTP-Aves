package co.utp.aves.presentation.bird_detail

import android.media.AudioAttributes
import android.media.MediaPlayer
import android.media.MediaPlayer.OnPreparedListener
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SeekBar
import androidx.fragment.app.FragmentManager
import co.utp.aves.databinding.ItemMusicBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import java.io.IOException


class BirdMusicBottomSheetFragment : BottomSheetDialogFragment() {

    lateinit var sound: String
    lateinit var name:  String
    lateinit var position: String

    private lateinit var binding: ItemMusicBinding

     private lateinit var mediaPlayer: MediaPlayer


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        val rawSound = requireContext().resources.getIdentifier(
            sound.replace(".mp3", ""),
            "raw",
            requireContext().packageName
        )

        controlSound(rawSound, name)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = ItemMusicBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun show(manager: FragmentManager, tag: String?) {
        val fragment = manager.findFragmentByTag(tag)
        val ft = manager.beginTransaction()
        if (fragment != null) {
            ft.remove(fragment)
            ft.commitAllowingStateLoss()
        }

        try {
            super.show(manager, tag)
        } catch (exception: Exception) {
            Log.e(BirdMusicBottomSheetFragment::class.java.name, exception.stackTraceToString())
        }
    }

    override fun onStart() {
        super.onStart()
        isCancelable = false
    }


    private fun controlSound(assetName: Int, name: String) {

        val nameTitle = "$name - $position"

        binding.fabPause.visibility= View.GONE

        if(sound.contains("_")) binding.titleBirdSong.text = nameTitle else binding.titleBirdSong.text = name

        mediaPlayer = MediaPlayer.create(context, assetName).apply {
            setAudioAttributes(
                AudioAttributes.Builder()
                    .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
                    .setUsage(AudioAttributes.USAGE_MEDIA)
                    .build()
            )
        }



        binding.fabPlay.setOnClickListener {
            mediaPlayer.start()
            binding.fabPlay.visibility = View.GONE
            binding.fabPause.visibility= View.VISIBLE

        }

        binding.fabPause.setOnClickListener {
            mediaPlayer.pause()
            binding.fabPlay.visibility = View.VISIBLE
            binding.fabPause.visibility = View.GONE
        }

        binding.fabStop.setOnClickListener {
            mediaPlayer.stop()
            binding.fabPlay.visibility = View.VISIBLE
            binding.fabPause.visibility = View.GONE
        }

        binding.closeSong.setOnClickListener {
                mediaPlayer.stop()
            dismiss()
        }
    }
}