package com.backbase.assignment.di

import androidx.lifecycle.ViewModelProvider
import com.backbase.assignment.network.ApiService
import com.backbase.assignment.repository.MovieRepository
import com.backbase.assignment.viewmodel.ViewModelFactory
/**
 * Class that handles object creation.
 * Like this, objects can be passed as parameters in the constructors and then replaced for
 * testing, where needed.
 */
object Injection {

    /**
     * Creates an instance of [MovieRepository] based on the [ApiService] and a
     */
    private fun provideMovieRepository(): MovieRepository {
        return MovieRepository(ApiService.create())
    }

    /**
     * Provides the [ViewModelProvider.Factory] that is then used to get a reference to
     * [ViewModel] objects.
     */
    fun provideViewModelFactory(): ViewModelProvider.Factory {
        return ViewModelFactory(provideMovieRepository())
    }
}
