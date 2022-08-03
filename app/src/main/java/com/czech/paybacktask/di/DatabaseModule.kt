package com.czech.paybacktask.di

import android.content.Context
import androidx.room.Room
import com.czech.paybacktask.data.room.PhotosDao
import com.czech.paybacktask.data.room.PhotosDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideUserDatabase(@ApplicationContext context: Context): PhotosDatabase {
        return Room.databaseBuilder(context, PhotosDatabase::class.java, "PHOTOS_DB")
            .fallbackToDestructiveMigration()
            .build()
    }

    @Provides
    @Singleton
    fun providePhotosDao(photosDatabase: PhotosDatabase): PhotosDao {
        return photosDatabase.photosDao()
    }
}