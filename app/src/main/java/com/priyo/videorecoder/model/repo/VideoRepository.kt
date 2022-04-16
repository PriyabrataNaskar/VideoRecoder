package com.priyo.videorecoder.model.repo

import android.content.Context
import com.CodeBoy.MediaFacer.MediaFacer
import com.CodeBoy.MediaFacer.VideoGet
import com.CodeBoy.MediaFacer.mediaHolders.videoContent
import com.priyo.videorecoder.model.data.VideoDetails
import java.util.ArrayList

/**
 * Created by Priyabrata Naskar on 16-04-2022.
 */
class VideoRepository(context: Context) {
    val context: Context
    init {
        this.context = context
    }

    suspend fun getAllVideos(): List<VideoDetails> {
        val allVideos: ArrayList<videoContent> = MediaFacer.withVideoContex(context)
            .getAllVideoContent(VideoGet.externalContentUri)

        val list: MutableList<VideoDetails> = mutableListOf()
        for (video in allVideos) {
            val videoDetails = VideoDetails(
                videoId = video.videoId,
                videoName = video.videoName,
                path = video.path,
                videoDuration = video.videoDuration,
                videoSize = video.videoSize,
                AssetFileStringUri = video.assetFileStringUri,
                artist = video.artist
            )
            list.add(videoDetails)
        }
        return list
    }
}