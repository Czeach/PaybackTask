package com.czech.paybacktask.ui.photosList

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.czech.paybacktask.R
import com.czech.paybacktask.databinding.PhotosListFragmentBinding

class PhotosListFragment : Fragment() {

    private var _binding: PhotosListFragmentBinding? = null
    private val binding get() = _binding!!

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
                        queryField.hint = getString(R.string.search_hint)
                        searchButton.isEnabled = false
                    }
                }
            }
        }
    }
}