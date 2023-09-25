package com.padcmyanmar.mewz.themovieapp.mvi.processors

import androidx.lifecycle.LiveData
import androidx.lifecycle.toLiveData
import com.padcmyanmar.mewz.themovieapp.data.models.MovieModel
import com.padcmyanmar.mewz.themovieapp.data.models.MovieModelImpl
import com.padcmyanmar.mewz.themovieapp.mvi.states.MovieDetailState
import io.reactivex.rxjava3.core.BackpressureStrategy
import io.reactivex.rxjava3.core.Observable

object MovieDetailProcessor {

    private val mMovieModel: MovieModel = MovieModelImpl

    fun loadAllMovieDetailPageData(
        previousState: MovieDetailState,
        movieId: Int
    ): LiveData<MovieDetailState> {
        return Observable.zip(
            mMovieModel.getMovieByIdObservable(movieId),
            mMovieModel.getCreditsByMovieObservable(movieId),
        ){movieDetail, creditByMovie ->
            return@zip previousState.copy(
                isLoading = false,
                errorMessage = "",
                movieDetail = movieDetail,
                creditByMovie = creditByMovie
            )
        }.toFlowable(BackpressureStrategy.BUFFER).toLiveData()
    }
}