package com.padcmyanmar.mewz.themovieapp.mvi.mvibase

interface MVIView<S : MVIState> {
    fun render(state: S)
}