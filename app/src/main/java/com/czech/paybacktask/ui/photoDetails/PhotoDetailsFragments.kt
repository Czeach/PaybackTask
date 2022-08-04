package com.czech.paybacktask.ui.photoDetails

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import com.czech.paybacktask.databinding.PhotoDetailsFragmentsFragmentBinding
import com.czech.paybacktask.ui.photosList.PhotosListViewModel
import com.czech.paybacktask.utils.states.PhotosDetailState
import kotlinx.coroutines.launch

class PhotoDetailsFragments : Fragment() {

    private var _binding: PhotoDetailsFragmentsFragmentBinding? = null
    private val binding get() = _binding!!

    private val viewModel by activityViewModels<PhotoDetailsFragmentsViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = PhotoDetailsFragmentsFragmentBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val photoId = PhotoDetailsFragmentsArgs.fromBundle(requireArguments()).photoId

        viewModel.getPhotoById(photoId)

        observeViewModel()
    }

    fun observeViewModel() {
        lifecycleScope.launch {
            viewModel.photosDetailState.collect {
                when (it) {
                    is PhotosDetailState.Loading -> {
                        Log.d("Loading", "Loading")
                    }
                    is PhotosDetailState.Success -> {
                        Log.d("Success", it.data.toString())
                    }
                    is PhotosDetailState.Error -> {
                        Log.d("Error", "Error")
                    }
                    else -> {}
                }
            }
        }
    }

}