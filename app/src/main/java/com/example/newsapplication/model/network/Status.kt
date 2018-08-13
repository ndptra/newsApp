package com.example.newsapplication.model.network

/**
 * Created by Rizky on 7/4/2018.
 */
enum class Status {
    SUCCESS,
    ERROR,
    LOADING;

    /**
     * Returns `true` if the [Status] is success else `false`.
     */
    fun isSuccessful() = this == SUCCESS

    /**
     * Returns `true` if the [Status] is loading else `false`.
     */
    fun isLoading() = this == LOADING
}