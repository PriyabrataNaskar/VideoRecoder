package com.priyo.videorecoder.model.data

/**
 * Created by Priyabrata Naskar on 15-04-2022.
 */
data class VideoDetails(
    val videoId: Long,
    val videoName: String? = null,
    val path: String? = null,
    val videoDuration: Long,
    val videoSize: Long,
    val AssetFileStringUri: String? = null,
    val artist: String? = null
)