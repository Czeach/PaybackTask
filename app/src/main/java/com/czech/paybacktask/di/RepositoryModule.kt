package com.czech.paybacktask.di

import com.czech.paybacktask.data.network.ApiService
import com.czech.paybacktask.data.network.repositories.PhotoRepository
import com.czech.paybacktask.data.network.repositories.PhotoRepositoryImpl
import com.czech.paybacktask.data.room.PhotosDao
import com.czech.paybacktask.data.room.repositories.PhotosDaoRepository
import com.czech.paybacktask.data.room.repositories.PhotosDaoRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@[Module InstallIn(SingletonComponent::class)]
class RepositoryModule {

    @[Provides Singleton]
    fun providePhotoRepository(
        apiService: ApiService,
        photosDaoRepository: PhotosDaoRepository
    ): PhotoRepository {
        return PhotoRepositoryImpl(
            apiService = apiService,
            photosDaoRepository = photosDaoRepository
        )
    }

    @[Provides Singleton]
    fun providePhotosDaoRepository(
        photosDao: PhotosDao
    ): PhotosDaoRepository {
        return PhotosDaoRepositoryImpl(
            photosDao = photosDao
        )
    }

}