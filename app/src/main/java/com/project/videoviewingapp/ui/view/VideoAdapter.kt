package com.project.videoviewingapp.ui.view

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.project.videoviewingapp.R
import com.project.videoviewingapp.data.model.VideoData
import com.project.videoviewingapp.databinding.ItemVideoBinding
import javax.inject.Inject

class VideoAdapter @Inject constructor(
    private var videos: List<VideoData>
): RecyclerView.Adapter<VideoAdapter.ViewHolder>() {

    class ViewHolder(
        private val binding: ItemVideoBinding,
    ): RecyclerView.ViewHolder(binding.root){

        fun bind(video: VideoData){
            binding.video = video
            loadImage(binding, video)
        }

        private fun loadImage(binding: ItemVideoBinding,video: VideoData) {
            val imageResId = itemView.context.resources.getIdentifier(
                video.thumb,
                "drawable",
                itemView.context.packageName
            )

            if (imageResId != 0) {
                binding.thumb.setImageResource(imageResId)
            } else {
                binding.thumb.setImageResource(R.drawable.default_image)
            }

            // Для завантаження картинок по URI
            /*Glide.with(binding.thumb.context)
                .load(video.thumb)
                .error(R.drawable.default_image)
                .into(binding.thumb)*/
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemVideoBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int = videos.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(videos[position])
    }

    fun updateVideoList(newList: List<VideoData>){
        videos = newList
        notifyDataSetChanged()
    }
}