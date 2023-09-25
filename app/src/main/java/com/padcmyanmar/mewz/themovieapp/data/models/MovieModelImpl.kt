package com.padcmyanmar.mewz.themovieapp.data.models

import android.annotation.SuppressLint
import androidx.lifecycle.LiveData
import com.padcmyanmar.mewz.themovieapp.data.vos.ActorVO
import com.padcmyanmar.mewz.themovieapp.data.vos.GenreVO
import com.padcmyanmar.mewz.themovieapp.data.vos.MovieVO
import com.padcmyanmar.mewz.themovieapp.data.vos.NOW_PLAYING
import com.padcmyanmar.mewz.themovieapp.data.vos.POPULAR
import com.padcmyanmar.mewz.themovieapp.data.vos.TOP_RATED
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.schedulers.Schedulers

@SuppressLint("CheckResult")
object MovieModelImpl: BaseModel(), MovieModel {

//    private var mMovieDataAgent: MovieDataAgent = RetrofitDataAgentImpl
//    private var mMovieDatabase: MovieDatabase? = null
//
//    fun initDatabase(context: Context) {
//        mMovieDatabase = MovieDatabase.getDBInstance(context)
//    }

    override fun getNowPlayingMovies(onFailure: (String) -> Unit): LiveData<List<MovieVO>>? {
        // Network
        mTheMovieAPI.getNowPlayingMovies()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                it.results?.forEach { movie -> movie.type = NOW_PLAYING }
                mMovieDatabase?.movieDao()?.insertMovies(it.results ?: listOf())
            }, {
                onFailure(it.localizedMessage ?: "")
            })

        // Database
        return mMovieDatabase?.movieDao()?.getMoviesByType(type = NOW_PLAYING)
    }

    override fun getPopularMovies(onFailure: (String) -> Unit): LiveData<List<MovieVO>>? {

        // Network
        mTheMovieAPI.getPopularMovies()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                it.results?.forEach { movie -> movie.type = POPULAR }
                mMovieDatabase?.movieDao()?.insertMovies(it.results ?: listOf())
            }, {
                onFailure(it.localizedMessage ?: "")
            })

        // Database
        return mMovieDatabase?.movieDao()?.getMoviesByType(type = POPULAR)
    }

    override fun getTopRatedMovies(onFailure: (String) -> Unit): LiveData<List<MovieVO>>? {

        // Network
        mTheMovieAPI.getTopRatedMovies()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                it.results?.forEach { movie -> movie.type = TOP_RATED }
                mMovieDatabase?.movieDao()?.insertMovies(it.results ?: listOf())
            }, {
                onFailure(it.localizedMessage ?: "")
            })

        // Database
        return mMovieDatabase?.movieDao()?.getMoviesByType(type = TOP_RATED)
    }

    override fun getGenreList(onSuccess: (List<GenreVO>) -> Unit, onFailure: (String) -> Unit) {
        mTheMovieAPI.getGenreList()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                onSuccess(it.genres ?: listOf())
            }, {
                onFailure(it.localizedMessage ?: "")
            })
    }

    override fun getMoviesByGenre(
        genreId: Int,
        onSuccess: (List<MovieVO>) -> Unit,
        onFailure: (String) -> Unit
    ) {
        mTheMovieAPI.getMoviesByGenre(genreId)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                onSuccess(it.results ?: listOf())
            }, {
                onFailure(it.localizedMessage ?: "")
            })
    }

    override fun getActors(onSuccess: (List<ActorVO>) -> Unit, onFailure: (String) -> Unit) {
        mTheMovieAPI.getActors()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                onSuccess(it.results ?: listOf())
            }, {
                onFailure(it.localizedMessage ?: "")
            })
    }

    override fun getMovieDetail(movieId: Int, onFailure: (String) -> Unit): LiveData<MovieVO?>? {

        // Network
        mTheMovieAPI.getMovieDetail(movieId)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                val movieFromDatabase = mMovieDatabase?.movieDao()?.getMovieByIdOneTime(movieId)
                it.type = movieFromDatabase?.type
                mMovieDatabase?.movieDao()?.insertSingleMovie(it)
            }, {
                onFailure(it.localizedMessage ?: "")
            })

        // Database
        return mMovieDatabase?.movieDao()?.getMovieById(movieId)
    }

    override fun getCreditsByMovie(
        movieId: Int,
        onSuccess: (Pair<List<ActorVO>, List<ActorVO>>) -> Unit,
        onFailure: (String) -> Unit
    ) {
        mTheMovieAPI.getCreditsByMovie(movieId)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                onSuccess(Pair(it.cast ?: listOf(), it.crew ?: listOf()))
            }, {
                onFailure(it.localizedMessage ?: "")
            })
    }

    override fun getSearchMovie(query: String): Observable<List<MovieVO>> {
        return mTheMovieAPI.getSearchMovie(query = query)
            .map { it.results ?: listOf() }
            .onErrorResumeNext { Observable.just(listOf()) }
            .subscribeOn(Schedulers.io())
    }

    // MVI
    override fun getNowPlayingMoviesObservable(): Observable<List<MovieVO>> {
        mTheMovieAPI.getNowPlayingMovies()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe{
                it.results?.forEach { movie -> movie.type = NOW_PLAYING }
                mMovieDatabase?.movieDao()?.insertMovies(it.results ?: listOf())
            }
        return mMovieDatabase?.movieDao()?.getMoviesByTypeFlowable(type = NOW_PLAYING)?.toObservable()!!
    }

    override fun getPopularMoviesObservable(): Observable<List<MovieVO>> {
        mTheMovieAPI.getPopularMovies()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe{
                it.results?.forEach { movie -> movie.type = POPULAR }
                mMovieDatabase?.movieDao()?.insertMovies(it.results ?: listOf())
            }
        return mMovieDatabase?.movieDao()?.getMoviesByTypeFlowable(type = POPULAR)?.toObservable()!!
    }

    override fun getTopRatedMoviesObservable(): Observable<List<MovieVO>> {
        // Network
        mTheMovieAPI.getTopRatedMovies()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe{
                it.results?.forEach { movie -> movie.type = TOP_RATED }
                mMovieDatabase?.movieDao()?.insertMovies(it.results ?: listOf())
            }
        return mMovieDatabase?.movieDao()?.getMoviesByTypeFlowable(type = TOP_RATED)?.toObservable()!!
    }

    override fun getGenresObservable(): Observable<List<GenreVO>> {
        return mTheMovieAPI.getGenreList()
            .map { it.genres ?: listOf() }
            .subscribeOn(Schedulers.io())
    }

    override fun getActorsObservable(): Observable<List<ActorVO>> {
        return mTheMovieAPI.getActors()
            .map { it.results ?: listOf() }
            .subscribeOn(Schedulers.io())
    }

    override fun getMoviesByGenreObservable(genreId: Int): Observable<List<MovieVO>> {
        return mTheMovieAPI.getMoviesByGenre(genreId)
            .map { it.results ?: listOf() }
            .subscribeOn(Schedulers.io())
    }

    override fun getMovieByIdObservable(movieId: Int): Observable<MovieVO> {
        mTheMovieAPI.getMovieDetail(movieId)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {
                val movieFromDatabase = mMovieDatabase?.movieDao()?.getMovieByIdOneTime(movieId)
                it.type = movieFromDatabase?.type
                mMovieDatabase?.movieDao()?.insertSingleMovie(it)
            }
        return mMovieDatabase?.movieDao()?.getMovieByIdFlowable(movieId)?.toObservable()!!
    }

    override fun getCreditsByMovieObservable(movieId: Int): Observable<Pair<List<ActorVO>, List<ActorVO>>> {
        return mTheMovieAPI.getCreditsByMovie(movieId)
            .map { Pair(it.cast ?: listOf(), it.crew ?: listOf()) }
            .subscribeOn(Schedulers.io())

    }
}