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

    private lateinit var binding: ItemMusicBinding

    private lateinit var mediaPlayer: MediaPlayer


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        val rawSound = requireContext().resources.getIdentifier(
            sound.replace(".mp3", ""),
            "raw",
            requireContext().packageName
        )

        controlSound(rawSound)
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



        //    binding.fabPlay.setBackgroundResource(R.drawable.ic_pause_player)


        binding.fabPlay.setOnClickListener {
                try {
                    mediaPlayer = MediaPlayer.create(context, assetName).apply {
                        setAudioAttributes(
                            AudioAttributes.Builder()
                                .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
                                .setUsage(AudioAttributes.USAGE_MEDIA)
                                .build()
                        )  
                    }
                    mediaPlayer.prepare()
                    mediaPlayer.setOnPreparedListener { media -> media.start() }
                    mediaPlayer.prepareAsync()

                }catch (e: IOException){
                    e.printStackTrace()
                }

        }

        binding.fabPause.setOnClickListener {
            mediaPlayer.pause()
        }

        binding.fabStop.setOnClickListener {
            mediaPlayer.stop()

        }

        binding.seekBarSong.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                mediaPlayer?.seekTo(progress)
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {

            }

            override fun onStopTrackingTouch(seekBar: SeekBar?) {

            }


        })

        binding.closeSong.setOnClickListener {
                mediaPlayer.stop()
                mediaPlayer.reset()
                mediaPlayer.release()
            dismiss()
        }
    }

    private fun initializeSeekBar(mediaPlayer: MediaPlayer) {
        binding.seekBarSong.max = mediaPlayer.duration

        val handler = Handler()
            handler.postDelayed(object : Runnable {
                override fun run() {
                    try {
                        binding.seekBarSong.max = mediaPlayer.currentPosition
                        handler.postDelayed(this, 1000)
                    } catch (e: Exception) {
                        binding.seekBarSong.progress = 0
                    }
                }
            }, 0)

    }
}