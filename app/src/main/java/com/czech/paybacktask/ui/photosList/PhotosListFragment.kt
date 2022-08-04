package com.czech.paybacktask.ui.photosList

import android.content.DialogInterface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.czech.paybacktask.R
import com.czech.paybacktask.databinding.PhotosListFragmentBinding
import com.czech.paybacktask.ui.photosList.adapter.PhotoListAdapter
import com.czech.paybacktask.ui.photosList.adapter.PhotoListDiffCallback
import com.czech.paybacktask.utils.launchFragment
import com.czech.paybacktask.utils.showDialog
import com.czech.paybacktask.utils.states.PhotosListState
import kotlinx.coroutines.launch

class PhotosListFragment : Fragment() {

    private var _binding: PhotosListFragmentBinding? = null
    private val binding get() = _binding!!

    private val viewModel by activityViewModels<PhotosListViewModel>()

    private val photosListAdapter by lazy { PhotoListAdapter(PhotoListDiffCallback) }

    private var query = ""

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = PhotosListFragmentBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        activateQueryField()

        binding.photosListRecycler.apply {
            layoutManager =
                LinearLayoutManager(requireActivity(), LinearLayoutManager.VERTICAL, false)
            adapter = photosListAdapter
        }

        query = "fruits"

        viewModel.getPhotos(query)

        enterQuery()

        observeViewModel()
    }

    private fun observeViewModel() {
        lifecycleScope.launch {
            viewModel.photosListState.collect {
                when (it) {
                    is PhotosListState.Loading -> {
                        binding.apply {
                            progressBar.visibility = View.VISIBLE
                            result.visibility = View.GONE
                        }
                    }
                    is PhotosListState.Success -> {
                        binding.apply {
                            progressBar.visibility = View.GONE
                            result.visibility = View.VISIBLE

                            searched.text = query
                        }

                        if (it.data != null) {
                            photosListAdapter.submitList(it.data)

                            navigateToDetailsPage()
                        }
                    }
                    is PhotosListState.Error -> {
                        binding.apply {
                            progressBar.visibility = View.GONE
                            result.visibility = View.VISIBLE
                        }
                        Toast.makeText(requireContext(), it.message, Toast.LENGTH_SHORT).show()
                    }
                    else -> {}
                }
            }
        }
    }

    private fun activateQueryField() {
        binding.apply {
            queryField.setOnFocusChangeListener { _, focused ->
                when (focused) {
                    true -> {
                        queryField.hint = ""
                        searchButton.isEnabled = true
                    }
                    false -> {

                        if (queryField.text.isEmpty()) {
                            queryField.hint = getString(R.string.search_hint)
                        }
                        searchButton.isEnabled = false
                    }
                }
            }
        }
    }

    private fun enterQuery() {
        binding.apply {
            searchButton.setOnClickListener {

                query = queryField.text.toString()

                if (query.isEmpty()) {
                    Toast.makeText(
                        requireContext(),
                        "Please enter a valid search field",
                        Toast.LENGTH_SHORT
                    ).show()
                }

                queryField.text.clear()

                viewModel.getPhotos(query)
            }
        }
    }

    private fun navigateToDetailsPage() {
        photosListAdapter.onClickItemListener = {

            val message = "Would you like to navigate to the photo detail screen?"

            val positiveButton = DialogInterface.OnClickListener{ _: DialogInterface, _: Int ->
                launchFragment(
                    PhotosListFragmentDirections.actionPhotosListFragmentToPhotoDetailsFragments(
                        it.id!!
                    )
                )
            }
            val negativeButton = DialogInterface.OnClickListener{ dialog: DialogInterface, _: Int ->
                dialog.cancel()
            }

            requireContext().showDialog(message, positiveButton, negativeButton)
        }
    }
}