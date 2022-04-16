package com.priyo.videorecoder

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.priyo.videorecoder.databinding.FragmentFirstBinding


/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class FirstFragment : Fragment() {

    private var _binding: FragmentFirstBinding? = null

    // This property is only valid between onCreateView and onDestroyView.
    private val binding get() = _binding!!

    // Member variables
    private var mRecyclerView: RecyclerView? = null
    private var mAdapter: VideoAdapter? = null

    private lateinit var viewModel: VideoViewModel
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentFirstBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = (activity as MainActivity).viewModel

        observeLiveData()
        /**
        val allVideos: ArrayList<videoContent> = MediaFacer
        .withVideoContex(activity)
        .getAllVideoContent(VideoGet.externalContentUri)
        Toast.makeText(activity, allVideos.size.toString(), Toast.LENGTH_LONG).show()
        Log.e("Videos", allVideos.lastIndex.toString())

        val list: MutableList<VideoDetails> = mutableListOf()

        //add all the videos to a list
        lifecycleScope.launch {
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
        setRecyclerView(list)
        }
        Toast.makeText(requireContext(), allVideos.size.toString(), Toast.LENGTH_SHORT).show()
         **/

        binding.fab.setOnClickListener {
            findNavController().navigate(R.id.action_FirstFragment_to_SecondFragment)
        }
    }

    private fun observeLiveData() {
        viewModel.isLoading.observe(viewLifecycleOwner, Observer {
            if (it) {
                showProgressBar()
                hideEmptyAnim()
            } else {
                hideProgressBar()
            }
        })
        viewModel.videoList.observe(viewLifecycleOwner, Observer {
            if (it != null && it.isNotEmpty()) {
                hideProgressBar()
                hideEmptyAnim()
                setRecyclerView(it)
            }else if (it.isEmpty()){
                showEmptyAnim()
            }
        })
    }

    private fun showEmptyAnim() {
        binding.emptyAnim.visibility = View.VISIBLE
        binding.emptyAnim.playAnimation()
    }

    private fun hideEmptyAnim() {
        binding.emptyAnim.cancelAnimation()
        binding.emptyAnim.visibility = View.GONE
    }
    private fun showProgressBar() {
        binding.shimmerView.visibility = View.VISIBLE
        binding.shimmerView.startShimmer()
    }

    private fun hideProgressBar() {
        binding.shimmerView.stopShimmer()
        binding.shimmerView.visibility = View.GONE
    }


    private fun setRecyclerView(list: List<VideoDetails>) {
        // Initialize the RecyclerView.
        mRecyclerView = binding.recyclerView
        // Set the Layout Manager.
        mRecyclerView?.layoutManager = GridLayoutManager(context, 1)

        mAdapter = VideoAdapter(list, requireContext())

        mRecyclerView?.adapter = mAdapter
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}