package co.utp.aves.presentation.bird_detail

import android.media.MediaPlayer
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SeekBar
import androidx.fragment.app.FragmentManager
import co.utp.aves.R
import co.utp.aves.databinding.ItemMusicBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import kotlin.math.ceil


class BirdMusicBottomSheetFragment : BottomSheetDialogFragment() {

    lateinit var sound: String

    private lateinit var binding: ItemMusicBinding

    private var mediaPlayer: MediaPlayer? = null


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        val rawSound = context?.resources?.getIdentifier(
            sound.replace(".mp3", ""),
            "raw",
            requireContext().packageName
        )
        controlSound(rawSound!!)
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


    private fun controlSound(assetName: Int) {

        binding.fabPlay.setOnClickListener {
            if (mediaPlayer == null) {

                mediaPlayer = MediaPlayer.create(context, assetName)
                initializeSeekBar()
            }
            mediaPlayer?.start()

        }

        binding.fabPause.setOnClickListener {
            if (mediaPlayer != null) {
                mediaPlayer?.pause()
            }
        }

        binding.fabStop.setOnClickListener {
            if (mediaPlayer != null) {
                mediaPlayer?.stop()
                mediaPlayer?.reset()
                mediaPlayer?.release()
                mediaPlayer = null
            }
        }

        binding.seekBarSong.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                if (fromUser) mediaPlayer?.seekTo(progress)
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {

            }

            override fun onStopTrackingTouch(seekBar: SeekBar?) {

            }


        })

        binding.closeSong.setOnClickListener {
            if (mediaPlayer != null) {
                mediaPlayer?.stop()
                mediaPlayer?.reset()
                mediaPlayer?.release()
                mediaPlayer = null
            }
            dismiss()
        }
    }

    private fun initializeSeekBar() {
        binding.seekBarSong.max = mediaPlayer!!.duration

        val handler = Handler()
            handler.postDelayed(object : Runnable {
                override fun run() {
                    try {
                        binding.seekBarSong.max = mediaPlayer!!.currentPosition
                        handler.postDelayed(this, 1000)
                    } catch (e: Exception) {
                        binding.seekBarSong.progress = 0
                    }
                }
            }, 0)

    }
}