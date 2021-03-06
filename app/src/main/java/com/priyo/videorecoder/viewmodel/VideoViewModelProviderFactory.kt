package com.priyo.videorecoder.viewmodel

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.priyo.videorecoder.model.repo.VideoRepository

/**
 * Created by Priyabrata Naskar on 16-04-2022.
 */

class VideoViewModelProviderFactory(val videoRepository: VideoRepository, val application: Application) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return VideoViewModel(videoRepository = videoRepository, application = application) as T
    }
}