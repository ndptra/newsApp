package com.example.newsapplication.model

import com.google.gson.annotations.SerializedName

/**
 * Created by Rizky on 7/2/2018.
 */
data class Sources(
        @SerializedName("id") val id: String,
        @SerializedName("name") val name: String,
        @SerializedName("description") val description: String,
        @SerializedName("url") val url: String,
        @SerializedName("category") val category: String,
        @SerializedName("language") val language: String,
        @SerializedName("country") val country: String
)