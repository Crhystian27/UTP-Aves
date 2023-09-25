package co.utp.aves.presentation.aboutus.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import co.utp.aves.base.BaseAdapter
import co.utp.aves.base.BaseViewHolder
import co.utp.aves.databinding.ItemAboutUsBinding
import co.utp.aves.presentation.model.AboutUs
import co.utp.aves.utils.loadDrawable

class AboutUsAdapter(private val listener: AboutUsClick) :
    BaseAdapter<AboutUs, AboutUsAdapter.AboutUsViewHolder>(
        diffCallBack
    ) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        AboutUsViewHolder(
            ItemAboutUsBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )

    inner class AboutUsViewHolder(
        private val binding: ItemAboutUsBinding
    ) : BaseViewHolder<AboutUs>(binding) {
        override fun bind(data: AboutUs) {
            binding.apply {

                val drawable = ContextCompat.getDrawable(
                    root.context,
                    root.resources.getIdentifier(
                        data.Photo.replace(".webp", ""),
                        "drawable",
                        root.context.packageName
                    )
                )
                loadDrawable(aboutUsImage, drawable!!, root.context)

                aboutUsName.text = data.Name
                aboutUsTitle.text = data.Title

                clAboutUs.setOnClickListener {
                    if (data.LinkedIn.isNotEmpty()) listener.onClickAboutUs(data)
                }

            }
        }
    }

    companion object {
        private val diffCallBack = object : DiffUtil.ItemCallback<AboutUs>() {
            override fun areItemsTheSame(
                oldItem: AboutUs,
                newItem: AboutUs
            ) = oldItem.Name == newItem.Name

            override fun areContentsTheSame(
                oldItem: AboutUs,
                newItem: AboutUs
            ) = oldItem == newItem
        }
    }
}

interface AboutUsClick {
    fun onClickAboutUs(aboutUs: AboutUs)
}