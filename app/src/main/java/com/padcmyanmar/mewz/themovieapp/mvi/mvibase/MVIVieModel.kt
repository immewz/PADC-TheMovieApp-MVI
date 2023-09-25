package com.padcmyanmar.mewz.themovieapp.mvi.mvibase

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData

interface MVIVieModel<S: MVIState, I: MVIIntent> {
    val state: MutableLiveData<S>
    fun processIntent(intent: I, lifecycleOwner: LifecycleOwner)
}