package com.padcmyanmar.mewz.themovieapp.mvi.intents

import com.padcmyanmar.mewz.themovieapp.mvi.mvibase.MVIIntent

sealed class MovieDetailIntent: MVIIntent {
    object LoadAllMovieDetailPageData: MovieDetailIntent()
}
