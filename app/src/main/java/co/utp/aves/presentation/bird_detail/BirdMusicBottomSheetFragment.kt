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
import co.utp.aves.R
import co.utp.aves.databinding.ItemMusicBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import kotlinx.coroutines.*
import java.io.IOException


class BirdMusicBottomSheetFragment : BottomSheetDialogFragment() {

    lateinit var sound: String
    lateinit var name:  String

    private lateinit var binding: ItemMusicBinding
    private lateinit var mediaPlayer: MediaPlayer
    private lateinit var handler: Handler



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

        mediaPlayer = MediaPlayer.create(context, assetName)

        binding.apply {

            if(name.contains("/")) titleBirdSong.text = name.substringAfter("/") else titleBirdSong.text = name


            seekBarSong.progress = 0
            seekBarSong.max = mediaPlayer.duration

            fabPlay.setOnClickListener {
                if(!mediaPlayer.isPlaying) {
                    mediaPlayer.start()
                    handler.postDelayed(updateSeekBar, 0)
                    fabPlay.setImageResource(R.drawable.ic_pause_player)

                }else {
                    mediaPlayer.pause()
                    handler.removeCallbacks(updateSeekBar)
                    fabPlay.setImageResource(R.drawable.ic_play_player)
                }
            }

            closeSong.setOnClickListener {

                dismiss()
            }

            seekBarSong.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener{
                override fun onProgressChanged(p0: SeekBar?, position: Int, changed: Boolean) {
                    if(changed)
                        mediaPlayer.seekTo(position)
                }

                override fun onStartTrackingTouch(p0: SeekBar?) {
                    handler.removeCallbacks(updateSeekBar)
                }

                override fun onStopTrackingTouch(p0: SeekBar?) {
                    handler.postDelayed(updateSeekBar, 0)
                }

            })

            mediaPlayer.setOnCompletionListener {
                fabPlay.setImageResource(R.drawable.ic_play_player)
                seekBarSong.progress = 0
                handler.removeCallbacks(updateSeekBar)
            }

            handler = Handler()
        }


    }

    private val updateSeekBar = object : Runnable {
        override fun run() {
            binding.seekBarSong.progress = mediaPlayer.currentPosition
            handler.postDelayed(this, 1000)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        mediaPlayer.release()
        handler.removeCallbacks(updateSeekBar)
    }

}