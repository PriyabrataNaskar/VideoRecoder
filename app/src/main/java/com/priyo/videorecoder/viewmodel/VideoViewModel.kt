package com.priyo.videorecoder.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.priyo.videorecoder.model.data.VideoDetails
import com.priyo.videorecoder.model.repo.VideoRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

/**
 * Created by Priyabrata Naskar on 16-04-2022.
 */

class VideoViewModel(
    private val videoRepository: VideoRepository,
    application: Application
) : AndroidViewModel(application) {
    private val _videoList: MutableLiveData<List<VideoDetails>> = MutableLiveData()
    val videoList: LiveData<List<VideoDetails>> get() = _videoList

    //handles loading state when video is getting loaded
    private val _isLoading: MutableLiveData<Boolean> = MutableLiveData()
    val isLoading: LiveData<Boolean> get() = _isLoading

    private val TAG: String = "ViewModel"

    init {
        getVideoList()
    }

    fun getVideoList() {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                _isLoading.postValue(true)
                delay(5000)
                val response = videoRepository.getAllVideos()
                _videoList.postValue(response)
                _isLoading.postValue(false)
            }
        }
    }

}