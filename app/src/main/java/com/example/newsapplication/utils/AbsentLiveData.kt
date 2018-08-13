package com.example.newsapplication.utils

import android.arch.lifecycle.LiveData

/**
 * Created by Rizky on 7/4/2018.
 */
class AbsentLiveData <T> : LiveData<T>() {
    init {
        postValue(null)
    }

    fun <T> create(): LiveData<T> = AbsentLiveData()
}