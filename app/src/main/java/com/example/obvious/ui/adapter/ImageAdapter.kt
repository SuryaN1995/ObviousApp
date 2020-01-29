package com.example.obvious.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.obvious.R
import com.example.obvious.databinding.AdapterImageGridBinding
import com.example.obvious.interfaces.RecyclerListener
import com.example.obvious.model.ImageDetails

/**
 * Created by SURYA N on 29/1/20.
 */
class ImageAdapter(
    var details: List<ImageDetails>,
    val listener: RecyclerListener?,
    val isFullScreen: Boolean
) :
    RecyclerView.Adapter<ImageAdapter.ImageViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageViewHolder {
        val binding = DataBindingUtil.inflate<AdapterImageGridBinding>(
            LayoutInflater.from(parent.context),
            R.layout.adapter_image_grid, parent, false
        )
        return ImageViewHolder(binding, listener, isFullScreen)
    }

    override fun getItemCount(): Int {
        return details.size
    }

    override fun onBindViewHolder(holder: ImageViewHolder, position: Int) {
        holder.bind(details[position])
    }


    class ImageViewHolder(
        private val binding: AdapterImageGridBinding,
        private val listener: RecyclerListener?,
        private val fullScreen: Boolean
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(image: ImageDetails) {
            if (!fullScreen) {
                binding.root.setOnClickListener {
                    listener?.onItemClicked(adapterPosition)
                }
            }
            val params = if(fullScreen) ViewGroup.LayoutParams.MATCH_PARENT else ViewGroup.LayoutParams.WRAP_CONTENT
            binding.image.layoutParams = ViewGroup.LayoutParams(params,params)
            binding.imageUrl = if(fullScreen) image.hdurl else image.url
            binding.size = if(fullScreen) 1024 else 500
        }
    }
}