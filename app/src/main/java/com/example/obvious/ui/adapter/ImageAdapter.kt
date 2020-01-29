package com.example.obvious.ui.adapter

import android.graphics.drawable.Drawable
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition
import com.example.obvious.R
import com.example.obvious.interfaces.RecyclerListener
import com.example.obvious.databinding.AdapterImageGridBinding
import com.example.obvious.model.ImageDetails

/**
 * Created by SURYA N on 29/1/20.
 */
class ImageAdapter(var details: List<ImageDetails>,val listener: RecyclerListener) : RecyclerView.Adapter<ImageAdapter.ImageViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageViewHolder {
        val binding = DataBindingUtil.inflate<AdapterImageGridBinding>(LayoutInflater.from(parent.context),
            R.layout.adapter_image_grid,parent,false)
        return ImageViewHolder(binding,listener)
    }

    override fun getItemCount(): Int {
        return details.size
    }

    override fun onBindViewHolder(holder: ImageViewHolder, position: Int) {
        holder.bind(details[position])
    }


    class ImageViewHolder(
        private val binding: AdapterImageGridBinding,
        private val listener: RecyclerListener
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(image: ImageDetails) {
            binding.root.setOnClickListener {
                listener.onItemClicked(adapterPosition)
            }
            val context = binding.root.context
            Glide.with(context).load(image.url)
                .thumbnail(0.2f)
                .transition(DrawableTransitionOptions.withCrossFade(100))
                .transform(CenterCrop(), RoundedCorners(10))
                .apply(RequestOptions().override(400,400))
                .placeholder(R.drawable.ic_launcher_background)
                .into(object : CustomTarget<Drawable>() {
                    override fun onLoadCleared(placeholder: Drawable?) {
                        Log.d("Glide", "Imfo")
                    }

                    override fun onResourceReady(
                        resource: Drawable,
                        transition: Transition<in Drawable>?
                    ) {
                        binding.image.setImageDrawable(resource)
                    }

                })

        }
    }
}