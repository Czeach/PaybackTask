package com.czech.paybacktask.utils.states

import com.czech.paybacktask.data.network.model.Result

sealed class PhotosDetailState {
    data class Success(val data: Result.Hit?): PhotosDetailState()
    data class Error(val message: String): PhotosDetailState()
    object Loading: PhotosDetailState()
}
