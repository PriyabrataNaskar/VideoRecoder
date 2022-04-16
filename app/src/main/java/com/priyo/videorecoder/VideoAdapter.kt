package com.priyo.videorecoder

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.ImageView
import android.widget.TextView
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.priyo.videorecoder.databinding.VideoItemBinding
import kotlin.time.Duration.Companion.microseconds
import kotlin.time.Duration.Companion.milliseconds
import kotlin.time.Duration.Companion.minutes
import kotlin.time.Duration.Companion.seconds

/**
 * Created by Priyabrata Naskar on 15-04-2022.
 */
class VideoAdapter(private val mVideoDetailsData: List<VideoDetails>, mContext: Context): RecyclerView.Adapter<VideoAdapter.ViewHolder>() {

    // Member variables.
    private val mContext: Context

    init {
        this.mContext = mContext
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = VideoItemBinding.inflate(LayoutInflater.from(parent.context))
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return mVideoDetailsData.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        // Get current news
        val currentVideoDetails: VideoDetails = mVideoDetailsData[position]

        // Populate the items with data.
        holder.bindTo(currentVideoDetails)
    }

    inner class ViewHolder(binding: VideoItemBinding) : RecyclerView.ViewHolder(binding.root), View.OnClickListener{

        // Member Variables for the TextViews
        private val mVideoTitleText: TextView
        private val mVideoImage: ImageView
        private val mArtistName: TextView
        private val mVideoDuration: TextView
        private val mVideoSize: TextView
        private var shareButton: CheckBox

        init {
            // Initialize the views.
            mVideoTitleText = binding.videoTitle
            mVideoImage = binding.videoThumbImage
            mArtistName = binding.videoArtist
            mVideoDuration = binding.videoDuration
            mVideoSize = binding.videoSize
            shareButton = binding.shareButton
            // Set the OnClickListener to the entire view.
            itemView.setOnClickListener(this)
            shareButton.setOnClickListener(this)
        }

        override fun onClick(view: View) {
            val video: VideoDetails = mVideoDetailsData[adapterPosition]

            val videoId: Long = video.videoId
            val videoName: String? = video.videoName
            val path: String? = video.path
            val videoDuration: Long = video.videoDuration
            val videoSize: Long = video.videoSize
            val AssetFileStringUri: String? = video.AssetFileStringUri
            val artist: String? = video.artist

            if (view.id == itemView.id) {
                // Create an nav direction with a destination
                // Add the news details to the nav direction
                    /** Handle Navigation
                val action = NewsListFragmentDirections.actionNewsListFragmentToNewsDetailsFragment(
                    authorName = newsAuthorName,
                    newsTitle = newsTitle,
                    newsDescription = newsDescription,
                    newsImageResource = newsImageResource,
                    newsPublishTime = newsPublishTime,
                    content = content
                )
                view.findNavController().navigate(action)
                **/

                val action = FirstFragmentDirections.actionFirstFragmentToPlayerFragment(videoPath = video.AssetFileStringUri.toString())
                view.findNavController().navigate(action)
            } else if (view.id == shareButton.id) {
                val shareIntent = Intent(Intent.ACTION_SEND)

                //Intent shareIntent = new Intent();
                /**
                shareIntent.type = "text/plain"
                shareIntent.putExtra(
                    Intent.EXTRA_TEXT,
                    "$newsTitle \nDescription:$newsDescription \nby- $newsAuthorName $newsImageResource"
                )
                //shareIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                view.context
                    .startActivity(Intent.createChooser(shareIntent, "Share News With"))
                **/
            }
        }

        fun bindTo(currentVideo: VideoDetails) {
            mVideoTitleText.text = currentVideo.videoName

            // Load the images into the ImageView using the Glide library.
            Glide.with(mContext).load(currentVideo.AssetFileStringUri).placeholder(R.drawable.placeholder_image).centerCrop().into(mVideoImage)
            mArtistName.text = currentVideo.artist
            mVideoDuration.text = (currentVideo.videoDuration.milliseconds).toString()
            mVideoSize.text = (currentVideo.videoSize/(1024*1024)).toString()+ " MB"
        }
    }

}