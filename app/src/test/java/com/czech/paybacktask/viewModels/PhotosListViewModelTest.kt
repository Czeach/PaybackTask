package com.czech.paybacktask.viewModels

import com.czech.paybacktask.data.network.model.Result
import com.czech.paybacktask.data.network.repositories.PhotoRepository
import com.czech.paybacktask.ui.photosList.PhotosListViewModel
import com.czech.paybacktask.utils.states.DataState
import com.czech.paybacktask.utils.states.PhotosListState
import com.czech.paybacktask.viewModels.utils.TestRule
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.consumeAsFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations
import org.mockito.junit.MockitoJUnitRunner

@[RunWith(MockitoJUnitRunner::class) OptIn(ExperimentalCoroutinesApi::class)]
class PhotosListViewModelTest {

    @get: Rule
    val testCoroutineRule = TestRule()

    @Mock
    private lateinit var photosListRepository: PhotoRepository

    @Mock
    private lateinit var photosListViewModel: PhotosListViewModel

    private val testCoroutineDispatcher = TestCoroutineDispatcher()

    @Before
    fun initMocks() {
        MockitoAnnotations.initMocks(this)
    }

    @Test
    fun testPhotosListViewModel() = testCoroutineDispatcher.runBlockingTest {

        val response = Result.Hit(
            id = 0,
            comments = 0,
            downloads = 0,
            largeImageURL = "",
            likes = 0,
            previewURL = "",
            tags = "",
            type = "",
            user = "",
            userId = 0,
            userImageURL = "",
            views = 0
        )

        val query = ""

        photosListViewModel = PhotosListViewModel(photosListRepository)

        val responseToData = DataState.data(data = listOf(response))

        val channel = Channel<DataState<List<Result.Hit>>>()

        val flow = channel.consumeAsFlow()

        Mockito.`when`(photosListRepository.getSearchResult(query)).thenReturn(flow)

        val job = launch {
            channel.send(responseToData)
        }

        photosListViewModel.getPhotos(query)

        Assert.assertEquals(
            true,
            photosListViewModel.photosListState.value == PhotosListState.Success(listOf(response))
        )
        Assert.assertEquals(
            false,
            photosListViewModel.photosListState.value == PhotosListState.Error("")
        )
        job.cancel()
    }
}