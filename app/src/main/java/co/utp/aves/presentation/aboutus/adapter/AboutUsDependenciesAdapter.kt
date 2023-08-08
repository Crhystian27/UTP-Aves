package co.utp.aves.presentation.aboutus.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import co.utp.aves.base.BaseAdapter
import co.utp.aves.base.BaseViewHolder
import co.utp.aves.databinding.ItemDependenciesBinding
import co.utp.aves.presentation.model.AboutUsDependencies
import co.utp.aves.utils.loadDrawable

class AboutUsDependenciesAdapter(private val listener: AboutUsDependenciesClick):
BaseAdapter<AboutUsDependencies, AboutUsDependenciesAdapter.AboutUsDependenciesViewHolder>(
    diffCallBack
){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int)=
        AboutUsDependenciesViewHolder(ItemDependenciesBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        ))

    inner class AboutUsDependenciesViewHolder(
        private val binding: ItemDependenciesBinding
    ):BaseViewHolder<AboutUsDependencies>(binding) {
        override fun bind(data: AboutUsDependencies) {
            binding.apply {

                val drawable = ContextCompat.getDrawable(root.context,root.resources.getIdentifier(data.Image.replace(".webp",""),"drawable",root.context.packageName))
                loadDrawable(imgDependencies,drawable!!, root.context)


                imgDependencies.setOnClickListener {
                    listener.onClickAboutUsDependencies(data)
                }

            }
        }
    }

    companion object{
        private val diffCallBack = object : DiffUtil.ItemCallback<AboutUsDependencies>(){
            override fun areItemsTheSame(
                oldItem: AboutUsDependencies,
                newItem: AboutUsDependencies
            )= oldItem.Name == newItem.Name

            override fun areContentsTheSame(
                oldItem: AboutUsDependencies,
                newItem: AboutUsDependencies
            ) = oldItem == newItem
        }
    }
}

interface AboutUsDependenciesClick{
    fun onClickAboutUsDependencies(aboutUsDependencies: AboutUsDependencies)
}