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
import com.czech.paybacktask.utils.*
import com.czech.paybacktask.utils.states.PhotosListState
import kotlinx.coroutines.launch

class PhotosListFragment : Fragment() {

    private lateinit var binding: PhotosListFragmentBinding

    private val viewModel by activityViewModels<PhotosListViewModel>()

    private val photosListAdapter by lazy { PhotoListAdapter(PhotoListDiffCallback) }

    private var query = ""

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = PhotosListFragmentBinding.inflate(inflater, container, false)

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

        checkNetworkConnectivity()

        enterQuery()

        observeViewModel()

        navigateToDetailsPage()
    }

    private fun checkNetworkConnectivity() {
        viewModel.isNetworkConnected.observe(viewLifecycleOwner) { isConnected ->
            when (isConnected) {
                false -> {
                    requireContext().showDialog(getString(R.string.displayFromDB), null)
                    viewModel.getFromDB()
                }
                true -> {
                    query = getString(R.string.defaultSearch)

                    viewModel.getPhotos(query)
                }
            }
        }
    }

    private fun observeViewModel() {
        lifecycleScope.launch {
            viewModel.photosListState.collect {
                when (it) {
                    is PhotosListState.Loading -> {
                        binding.apply {
                            progressBar.show()
                            result.hide()
                        }
                    }
                    is PhotosListState.Success -> {
                        binding.apply {
                            progressBar.hide()
                            result.show()

                            searched.text = query
                        }

                        if (it.data != null) {
                            photosListAdapter.submitList(it.data)
                        }
                    }
                    is PhotosListState.Error -> {
                        binding.apply {
                            progressBar.hide()
                            result.show()
                        }
                        requireContext().showDialog(it.message, null)
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
                    }
                    false -> {

                        if (queryField.text.isEmpty()) {
                            queryField.hint = getString(R.string.search_hint)
                        }
                    }
                }
            }
        }
    }

    private fun enterQuery() {
        binding.apply {
            searchButton.setOnClickListener {

                viewModel.isNetworkConnected.observe(viewLifecycleOwner) { isConnected ->
                    when (isConnected) {
                        true -> {
                            query = queryField.text.toString()

                            if (query.isNotEmpty()) {
                                queryField.text.clear()
                                queryField.hint = getString(R.string.search_hint)

                                viewModel.getPhotos(query)
                            } else {
                                requireContext().showToast(getString(R.string.enterValidSearch))
                            }
                        }
                        false -> {
                            requireContext().showDialog(getString(R.string.displayFromDB), null)
                            viewModel.getFromDB()
                        }
                    }
                }
            }
        }
    }

    private fun navigateToDetailsPage() {
        photosListAdapter.onClickItemListener = {

            val message = getString(R.string.navigateToDetailScreen)

            val positiveButton = DialogInterface.OnClickListener{ _: DialogInterface, _: Int ->
                launchFragment(
                    PhotosListFragmentDirections.actionPhotosListFragmentToPhotoDetailsFragments(
                        it.id!!
                    )
                )
            }
            requireContext().showDialog(message, positiveButton)
        }
    }
}