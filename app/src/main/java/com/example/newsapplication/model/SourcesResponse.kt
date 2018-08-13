package com.example.newsapplication.model

import com.google.gson.annotations.SerializedName

/**
 * Created by Rizky on 7/2/2018.
 */
data class SourcesResponse(
    @SerializedName("status") val status: String,
    @SerializedName("sources") val sources: List<Sources>
)