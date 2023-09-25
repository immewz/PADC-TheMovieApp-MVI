package com.padcmyanmar.mewz.themovieapp.mvi.states

import com.padcmyanmar.mewz.themovieapp.data.vos.ActorVO
import com.padcmyanmar.mewz.themovieapp.data.vos.MovieVO
import com.padcmyanmar.mewz.themovieapp.mvi.mvibase.MVIState

data class MovieDetailState(
    val isLoading: Boolean = false,
    val errorMessage: String = "",
    val movieDetail: MovieVO?,
    val creditByMovie: Pair<List<ActorVO>, List<ActorVO>>
) : MVIState {
    companion object{

        private val  mMovieVO: MovieVO? = null

        fun idle(): MovieDetailState = MovieDetailState(
            isLoading = false,
            errorMessage = "",
            movieDetail = mMovieVO,
            creditByMovie = Pair(listOf(), listOf())
        )
    }
}