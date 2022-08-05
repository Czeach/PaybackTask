package com.czech.paybacktask.ui.photoDetails

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.czech.paybacktask.data.network.repositories.PhotoDetailsRepository
import com.czech.paybacktask.utils.states.PhotosDetailState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PhotoDetailsViewModel @Inject constructor(
    private val photoDetailsRepository: PhotoDetailsRepository
) : ViewModel() {

    val photosDetailState = MutableStateFlow<PhotosDetailState?>(null)

    fun getPhotoById(id: Int) {
        viewModelScope.launch {
            photoDetailsRepository.getPhotoById(id).collect {
                when {
                    it.isLoading -> {
                        photosDetailState.value = PhotosDetailState.Loading
                    }
                    it.data == null -> {
                        photosDetailState.value =
                            PhotosDetailState.Error(message = it.message.toString())
                    }
                    else -> {
                        it.data.let { photo ->
                            photosDetailState.value = PhotosDetailState.Success(data = photo)
                        }
                    }
                }
            }
        }
    }
}