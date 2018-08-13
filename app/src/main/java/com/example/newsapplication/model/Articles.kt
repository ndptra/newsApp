package com.example.newsapplication.model

import com.google.gson.annotations.SerializedName
import java.net.URL

data class Articles(
        @SerializedName("source") val source: ArticleSource,
        @SerializedName("author") val author: String,
        @SerializedName("title") val title: String,
        @SerializedName("description") val description: String,
        @SerializedName("url") val url: String,
        @SerializedName("urlToImage") val urlToImage: String?,
        @SerializedName("publishedAt") val publishedAt: String
)