package co.utp.aves.presentation.bird.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil

import co.utp.aves.base.BaseAdapter
import co.utp.aves.base.BaseViewHolder
import co.utp.aves.databinding.ItemBirdBinding
import co.utp.aves.presentation.model.*
import co.utp.aves.utils.loadDrawable


class BirdAdapter(private val listener: BirdClick) :
BaseAdapter<Ave,BirdAdapter.BirdViewHolder>(
    diffCallBack
){

    override fun onBindViewHolder(holder: BirdViewHolder, position: Int) {
        super.onBindViewHolder(holder, position)
            holder.itemView.layout(0,0,0,0)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        BirdViewHolder(ItemBirdBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        ))


    inner class BirdViewHolder(
        private val binding: ItemBirdBinding
    ): BaseViewHolder<Ave>(binding){
        override fun bind(data: Ave) {
            binding.apply {

                val drawable = ContextCompat.getDrawable(root.context,root.resources.getIdentifier(data.Imagen_Ave.replace(".webp",""),"drawable",root.context.packageName))

                loadDrawable(imgBird, drawable!!,root.context)
                titleBird.text = data.Nombre_Comun

                imgBird.setOnClickListener {
                    listener.onClick(data)
                }

            }
        }

    }

    companion object{
        private val diffCallBack = object : DiffUtil.ItemCallback<Ave>(){
            override fun areItemsTheSame(
                oldItem: Ave,
                newItem: Ave
            )= oldItem.Codigo == newItem.Codigo

            override fun areContentsTheSame(
                oldItem: Ave,
                newItem: Ave
            ) = oldItem == newItem
        }
    }
}

interface BirdClick{
    fun onClick(ave: Ave)
}