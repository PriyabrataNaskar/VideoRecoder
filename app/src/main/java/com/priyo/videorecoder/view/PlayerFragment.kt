package com.priyo.videorecoder.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.google.android.exoplayer2.ExoPlayer
import com.google.android.exoplayer2.MediaItem
import com.priyo.videorecoder.viewmodel.VideoViewModel
import com.priyo.videorecoder.databinding.FragmentPlayerBinding

/**
 * A simple [Fragment] subclass.
 * Use the [PlayerFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class PlayerFragment : Fragment() {

    private var player: ExoPlayer? = null

    private var _binding: FragmentPlayerBinding? = null

    // This property is only valid between onCreateView and onDestroyView.
    private val binding get() = _binding!!

    private lateinit var viewModel: VideoViewModel

    private var isPlayReady = true
    private var currentWindow = 0
    private var playbackPosition = 0L
    private lateinit var path: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            path = it.getString("videoPath").toString()
        }
    }

    private fun initializePlayer() {
        player = ExoPlayer.Builder(requireContext())
            .build()
            .also { exoPlayer ->
                binding.videoView.player = exoPlayer
                val mediaItem = MediaItem.fromUri(path)
                exoPlayer.setMediaItem(mediaItem)
            }
        player?.playWhenReady = isPlayReady
        player?.seekTo(currentWindow, playbackPosition)
        player?.prepare()
    }

    private fun releasePlayer() {
        player?.run {
            playbackPosition = this.currentPosition
            currentWindow = this.currentMediaItemIndex
            isPlayReady = this.playWhenReady
            release()
        }
        player = null
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentPlayerBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initializePlayer()

        viewModel = (activity as MainActivity).viewModel
    }

    override fun onDestroyView() {
        super.onDestroyView()
        releasePlayer()
    }

}