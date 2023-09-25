package com.padcmyanmar.mewz.themovieapp.data.models

import androidx.lifecycle.LiveData
import com.padcmyanmar.mewz.themovieapp.data.vos.ActorVO
import com.padcmyanmar.mewz.themovieapp.data.vos.GenreVO
import com.padcmyanmar.mewz.themovieapp.data.vos.MovieVO
import io.reactivex.rxjava3.core.Observable

interface MovieModel {

    fun getNowPlayingMovies(
        onFailure: (String) -> Unit
    ): LiveData<List<MovieVO>>?

    fun getPopularMovies(
        onFailure: (String) -> Unit
    ): LiveData<List<MovieVO>>?

    fun getTopRatedMovies(
        onFailure: (String) -> Unit
    ): LiveData<List<MovieVO>>?

    fun getGenreList(
        onSuccess: (List<GenreVO>) -> Unit,
        onFailure: (String) -> Unit
    )

    fun getMoviesByGenre(
        genreId: Int,
        onSuccess: (List<MovieVO>) -> Unit,
        onFailure: (String) -> Unit
    )

    fun getActors(
        onSuccess: (List<ActorVO>) -> Unit,
        onFailure: (String) -> Unit
    )

    fun getMovieDetail(
        movieId: Int,
        onFailure: (String) -> Unit
    ): LiveData<MovieVO?>?

    fun getCreditsByMovie(
        movieId: Int,
        onSuccess: (Pair<List<ActorVO>, List<ActorVO>>) -> Unit,
        onFailure: (String) -> Unit
    )

    fun getSearchMovie(
        query: String,
    ): Observable<List<MovieVO>>

    // Reactive Streams Only
    fun getNowPlayingMoviesObservable(): Observable<List<MovieVO>>
    fun getPopularMoviesObservable(): Observable<List<MovieVO>>
    fun getTopRatedMoviesObservable(): Observable<List<MovieVO>>
    fun getGenresObservable(): Observable<List<GenreVO>>
    fun getActorsObservable(): Observable<List<ActorVO>>
    fun getMoviesByGenreObservable(genreId: Int): Observable<List<MovieVO>>
    fun getMovieByIdObservable(movieId: Int): Observable<MovieVO>
    fun getCreditsByMovieObservable(movieId: Int): Observable<Pair<List<ActorVO>, List<ActorVO>>>

}