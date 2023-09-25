package com.padcmyanmar.mewz.themovieapp.mvi.intents

import com.padcmyanmar.mewz.themovieapp.mvi.mvibase.MVIIntent

sealed class MainIntent: MVIIntent{
    class LoadMoviesByGenreIntent(val genrePosition: Int): MainIntent()
    object LoadAllHomePageData: MainIntent()
}
