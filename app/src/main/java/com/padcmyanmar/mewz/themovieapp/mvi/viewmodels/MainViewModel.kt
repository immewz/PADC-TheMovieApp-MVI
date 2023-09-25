package com.padcmyanmar.mewz.themovieapp.mvi.viewmodels

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.padcmyanmar.mewz.themovieapp.mvi.intents.MainIntent
import com.padcmyanmar.mewz.themovieapp.mvi.mvibase.MVIVieModel
import com.padcmyanmar.mewz.themovieapp.mvi.processors.MainProcessor
import com.padcmyanmar.mewz.themovieapp.mvi.states.MainState

class MainViewModel(override val state: MutableLiveData<MainState> = MutableLiveData(MainState.idle()))
    : MVIVieModel<MainState, MainIntent>, ViewModel() {

    private val mProcessor = MainProcessor

    override fun processIntent(intent: MainIntent, lifecycleOwner: LifecycleOwner) {


        when(intent) {

            // Load Home Page Data
            MainIntent.LoadAllHomePageData -> {
                state.value?.let {
                    mProcessor.loadAllHomePageData(
                        previousState = it
                    ).observe(lifecycleOwner){ newState ->
                        state.postValue(newState)
                        if (newState.moviesByGenre.isEmpty()){
                            processIntent(MainIntent.LoadMoviesByGenreIntent(0), lifecycleOwner)
                        }
                    }
                }
            }

            // Load Movies By Genre
            is MainIntent.LoadMoviesByGenreIntent -> {
                state.value?.let {
                    val genreId = it.genres.getOrNull(intent.genrePosition)?.id ?: 0
                    mProcessor.loadMoviesByGenre(
                        genreId = genreId,
                        previousState = it
                    ).observe(lifecycleOwner, state::postValue)
                }
            }

        }


    }
}