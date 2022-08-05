package com.czech.paybacktask.ui.photosList

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.czech.paybacktask.data.network.connectivity.NetworkConnection
import com.czech.paybacktask.data.network.repositories.PhotoRepository
import com.czech.paybacktask.utils.states.PhotosListState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PhotosListViewModel @Inject constructor(
    private val photoRepository: PhotoRepository,
    networkConnection: NetworkConnection
) : ViewModel() {

    val isNetworkConnected = networkConnection
    val photosListState = MutableStateFlow<PhotosListState?>(null)

    fun getPhotos(query: String) {
        viewModelScope.launch {
            photoRepository.getSearchResult(query).collect { it ->
                when {
                    it.isLoading -> {
                        photosListState.value = PhotosListState.Loading
                    }
                    it.data == null -> {
                        photosListState.value =
                            PhotosListState.Error(message = it.message.toString())
                    }
                    else -> {
                        it.data.let { photos ->
                            photosListState.value = PhotosListState.Success(data = photos)
                        }
                    }
                }
            }
        }
    }

    fun getFromDB() {
        viewModelScope.launch {
            photoRepository.getCachedResult().collect {
                when {
                    it.isLoading -> {
                        photosListState.value = PhotosListState.Loading
                    }
                    it.data == null -> {
                        photosListState.value =
                            PhotosListState.Error(message = it.message.toString())
                    }
                    else -> {
                        it.data.let { photos ->
                            photosListState.value = PhotosListState.Success(data = photos)
                        }
                    }
                }
            }
        }
    }
}