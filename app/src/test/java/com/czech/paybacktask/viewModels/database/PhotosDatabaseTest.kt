package com.czech.paybacktask.viewModels.database

import com.czech.paybacktask.data.room.PhotosDao
import com.czech.paybacktask.data.room.repositories.PhotosDaoRepository
import com.czech.paybacktask.data.room.repositories.PhotosDaoRepositoryImpl
import org.junit.Before
import org.mockito.Mock

class PhotosDatabaseTest {

    @Mock
    private lateinit var dao: PhotosDao

    @Mock
    private lateinit var daoRepo: PhotosDaoRepository

    @Before
    fun before() {
        daoRepo = PhotosDaoRepositoryImpl(dao)
    }

//    fun insertPhotos() {
//        dao.insertPhotos()
//    }
}