package co.utp.aves.presentation.bird.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import co.utp.aves.base.BaseAdapter
import co.utp.aves.base.BaseViewHolder
import co.utp.aves.databinding.ItemFoodBinding
import co.utp.aves.utils.loadDrawable


class FoodAdapter:
BaseAdapter<String,FoodAdapter.FoodViewHolder>(
    diffCallBack
){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        FoodViewHolder(ItemFoodBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        ))


    inner class FoodViewHolder(
        private val binding: ItemFoodBinding
    ):BaseViewHolder<String>(binding){
        override fun bind(data: String) {
            binding.apply {

                when(data){
                    "anfi" -> {foodName.text ="Anfibios" }
                    "crusta" -> {foodName.text ="Crustaceos" }
                    "frugiv" -> {foodName.text ="Frugívoro" }
                    "frutpeq" -> {foodName.text ="Frutos\nPequeños" }
                    "gran" -> {foodName.text ="Granos" }
                    "huevos" -> {foodName.text ="Huevos" }
                    "insec" -> {foodName.text ="Insectos" }
                    "nectari" -> {foodName.text ="Nectar" }
                    "paj" -> {foodName.text ="Pajaros" }
                    "pisci" -> {foodName.text ="Peces" }
                    "rat" -> {foodName.text ="Roedores\nReptiles" }
                    "semllas" -> {foodName.text ="Semillas" }
                    "semlleros" -> {foodName.text ="Semilleros" }
                    else -> {foodName.text = ""}
                }

                val drawable = ContextCompat.getDrawable(root.context,root.resources.getIdentifier(data.replace(".webp",""),"drawable",root.context.packageName))
                loadDrawable(foodImage, drawable!!,root.context)

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