package com.padcmyanmar.mewz.themovieapp.mvi.viewmodels

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.padcmyanmar.mewz.themovieapp.mvi.intents.MovieDetailIntent
import com.padcmyanmar.mewz.themovieapp.mvi.mvibase.MVIVieModel
import com.padcmyanmar.mewz.themovieapp.mvi.processors.MovieDetailProcessor
import com.padcmyanmar.mewz.themovieapp.mvi.states.MovieDetailState

class MovieDetailViewModel(override val state: MutableLiveData<MovieDetailState> = MutableLiveData(MovieDetailState.idle()))
    : MVIVieModel<MovieDetailState, MovieDetailIntent>, ViewModel() {

    private val mProcessor = MovieDetailProcessor

    override fun processIntent(intent: MovieDetailIntent, lifecycleOwner: LifecycleOwner) {
        when(intent) {
            // Load Movie Detail Page Data
            MovieDetailIntent.LoadAllMovieDetailPageData -> {
                state.value?.let {
                    mProcessor.loadAllMovieDetailPageData(
                        previousState = it,
                        movieId = 0
                    ).observe(lifecycleOwner) { newState ->
                        state.postValue(newState)
                    }
                }
            }
        }
    }
}