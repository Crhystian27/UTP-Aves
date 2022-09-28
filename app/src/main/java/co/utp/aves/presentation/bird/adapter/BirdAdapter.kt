package co.utp.aves.presentation.bird.adapter

import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import co.utp.aves.R
import co.utp.aves.base.BaseAdapter
import co.utp.aves.base.BaseViewHolder
import co.utp.aves.databinding.ItemBirdBinding
import co.utp.aves.presentation.model.*
import co.utp.aves.utils.loadDrawable
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class BirdAdapter :
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

            }
        }

    }

    companion object{
        private val diffCallBack = object : DiffUtil.ItemCallback<Ave>(){
            override fun areItemsTheSame(
                oldItem: Ave,
                newItem: Ave
            ): Boolean = equals(oldItem,newItem)

            override fun areContentsTheSame(
                oldItem: Ave,
                newItem: Ave
            ): Boolean =
                oldItem.Alimentos == newItem.Alimentos && oldItem.Codigo == newItem.Codigo &&
                    oldItem.Distribucion == newItem.Distribucion && oldItem.Familia == newItem.Familia &&
                    oldItem.Imagen_Ave == newItem.Imagen_Ave && oldItem.Nombre_Cientifico == newItem.Nombre_Cientifico &&
                    oldItem.Nombre_Comun == newItem.Nombre_Comun && oldItem.Nombre_Ingles == newItem.Nombre_Ingles &&
                    oldItem.Orden == newItem.Orden && oldItem.Sonidos == newItem.Sonidos &&
                    oldItem.Tamano == newItem.Tamano && oldItem.Vocalizacion == newItem.Vocalizacion

        }
    }
}