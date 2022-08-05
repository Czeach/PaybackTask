package com.czech.paybacktask.utils.states

import com.czech.paybacktask.data.network.model.Result

sealed class PhotosListState {
    data class Success(val data: List<Result.Hit>?): PhotosListState()
    data class Error(val message: String): PhotosListState()
    object Loading: PhotosListState()
}

sealed class DatabaseState {
    data class IsNotEmpty(val data: List<Result.Hit>?): DatabaseState()
    data class IsEmpty(val message: String): DatabaseState()
}