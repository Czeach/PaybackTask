package com.czech.paybacktask.data.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.czech.paybacktask.data.room.entities.PhotoEntity

@Database(entities = [PhotoEntity::class], version = 1, exportSchema = false)
abstract class PhotosDatabase : RoomDatabase() {

    abstract fun photosDao(): PhotosDao
}