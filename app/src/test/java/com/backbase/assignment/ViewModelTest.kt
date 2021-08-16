package com.backbase.assignment

import androidx.arch.core.executor.testing.InstantTaskExecutorRule

import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import com.backbase.assignment.repository.MovieRepository
import com.backbase.assignment.viewmodel.MoviesViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Before
import org.junit.Rule
import org.mockito.Mockito


@ExperimentalCoroutinesApi
class ViewModelTest {
    @Rule // -> allows liveData to work on different thread besides main, must be public!
    var executorRule = InstantTaskExecutorRule()
    private var mockRepository: MovieRepository? = null
    private var testSubject: MoviesViewModel? = null
    private var mockObserver: Observer<LiveData<Any>>? = null


    @Before
    fun setUp() {
        //setup view model with dependencies mocked
        mockRepository = Mockito.mock(MovieRepository::class.java)
    }

}
