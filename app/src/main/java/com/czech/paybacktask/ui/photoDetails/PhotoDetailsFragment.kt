package com.czech.paybacktask.ui.photoDetails

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import com.czech.paybacktask.data.network.model.Result
import com.czech.paybacktask.databinding.PhotoDetailsFragmentsFragmentBinding
import com.czech.paybacktask.utils.hide
import com.czech.paybacktask.utils.loadImage
import com.czech.paybacktask.utils.show
import com.czech.paybacktask.utils.showToast
import com.czech.paybacktask.utils.states.PhotosDetailState
import kotlinx.coroutines.launch

class PhotoDetailsFragment : Fragment() {

    private var _binding: PhotoDetailsFragmentsFragmentBinding? = null
    private val binding get() = _binding!!

    private val viewModel by activityViewModels<PhotoDetailsViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = PhotoDetailsFragmentsFragmentBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val photoId = PhotoDetailsFragmentArgs.fromBundle(requireArguments()).photoId

        viewModel.getPhotoById(photoId)

        observeViewModel()
    }

    private fun observeViewModel() {
        lifecycleScope.launch {
            viewModel.photosDetailState.collect {
                when (it) {
                    is PhotosDetailState.Loading -> {
                        binding.apply {
                            progressBar.show()
                            result.hide()
                        }
                    }
                    is PhotosDetailState.Success -> {
                        binding.apply {
                            progressBar.hide()
                            result.show()
                        }

                        if (it.data != null) {
                            bindData(it.data)
                        }
                    }
                    is PhotosDetailState.Error -> {
                        binding.apply {
                            progressBar.hide()
                            result.show()
                        }
                        requireContext().showToast(it.message)
                    }
                    else -> {}
                }
            }
        }
    }

    private fun bindData(data: Result.Hit) {
        binding.apply {
            view?.loadImage(data.largeImageURL.toString(), photo)
            tags.text = data.tags
            user.text = data.user
            comments.text = data.comments.toString()
            likes.text = data.likes.toString()
            downloads.text = data.downloads.toString()
        }
    }

}