package com.czech.paybacktask.data.room.repositories

import com.czech.paybacktask.data.room.PhotosDao
import com.czech.paybacktask.data.room.entities.PhotoEntity
import javax.inject.Inject

class PhotosDaoRepositoryImpl @Inject constructor(
    private val photosDao: PhotosDao
): PhotosDaoRepository {

    override suspend fun insertPhotos(photos: List<PhotoEntity>) {
        photosDao.insertPhotos(photos)
    }

    override suspend fun getPhotos(): List<PhotoEntity> {
        return photosDao.getPhotos()
    }

    override suspend fun getPhotoById(id: Int): PhotoEntity {
        return photosDao.getPhotoById(id)
    }

    override suspend fun deleteAll() {
        photosDao.deleteAll()
    }
}