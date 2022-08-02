package com.czech.paybacktask.data.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.czech.paybacktask.data.room.entities.PhotoEntity

@Dao
interface PhotosDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPhotos(photos: List<PhotoEntity>)

    @Query("SELECT * FROM photos")
    suspend fun getPhotos(): List<PhotoEntity>

    @Query("SELECT * FROM photos where id = :id LIMIT 1")
    suspend fun getPhotoById(id: Int): PhotoEntity

    @Query("DELETE from photos")
    suspend fun deleteAll()
}