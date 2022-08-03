package com.czech.paybacktask.data.room.repositories

import com.czech.paybacktask.data.room.entities.PhotoEntity

interface PhotosDaoRepository {

    suspend fun insertPhotos(photos: List<PhotoEntity>)

    suspend fun getPhotos(): List<PhotoEntity>

    suspend fun getPhotoById(id: Int): PhotoEntity

    suspend fun deleteAll()
}