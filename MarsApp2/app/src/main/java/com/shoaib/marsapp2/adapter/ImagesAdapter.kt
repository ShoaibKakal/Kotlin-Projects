package com.shoaib.marsapp2.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.net.toUri
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.shoaib.marsapp2.R
import com.shoaib.marsapp2.databinding.ItemImageLayoutBinding
import com.shoaib.marsapp2.model.MarsPhotos

class ImagesAdapter(private val images:List<MarsPhotos>): RecyclerView.Adapter<ImagesAdapter.ImageViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_image_layout, parent, false)
        return ImageViewHolder(view)
    }

    override fun onBindViewHolder(holder: ImageViewHolder, position: Int) {
        holder.bindView(images[position])
    }

    override fun getItemCount(): Int {
        return images.size
    }


    class ImageViewHolder(v:View):RecyclerView.ViewHolder(v) {
        val binding = ItemImageLayoutBinding.bind(v)

        fun bindView(image:MarsPhotos)
        {
            binding.itemImage.load(image.imgUrlSrc.toUri().buildUpon().scheme("https").build())
        }
    }
}