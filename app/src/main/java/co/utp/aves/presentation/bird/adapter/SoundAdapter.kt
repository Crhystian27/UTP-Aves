package co.utp.aves.presentation.bird.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import androidx.recyclerview.widget.DiffUtil
import co.utp.aves.base.BaseAdapter
import co.utp.aves.base.BaseViewHolder
import co.utp.aves.databinding.ItemSoundBinding

class SoundAdapter(private val listener: SoundClick):
BaseAdapter<String,SoundAdapter.SoundViewHolder>(
    diffCallBack
){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        SoundViewHolder(ItemSoundBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        ))


    inner class SoundViewHolder(
        private val binding: ItemSoundBinding
    ):BaseViewHolder<String>(binding){
        override fun bind(data: String) {
            binding.apply {

                sound.setOnClickListener {
                    listener.onClickSound(data,adapterPosition)
                }
            }
        }

    }


    companion object{
        private val diffCallBack = object : DiffUtil.ItemCallback<String>(){
            override fun areItemsTheSame(
                oldItem: String,
                newItem: String
            )= oldItem == newItem

            override fun areContentsTheSame(
                oldItem: String,
                newItem: String
            ) = oldItem == newItem
        }
    }

}

interface SoundClick{
    fun onClickSound(sound: String,position: Int)
}