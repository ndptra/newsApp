package com.example.newsapplication.service

import android.arch.lifecycle.LiveData
import android.util.Log
import com.example.newsapplication.model.SourcesResponse
import com.example.newsapplication.model.network.Resource
import com.example.newsapplication.utils.LiveDataCallAdapterFactory
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor



/**
 * Created by Rizky on 7/2/2018.
 */
interface SourcesService{
    @GET("sources") fun getAllSources(@Query("apiKey") apiKey: String): LiveData<Resource<SourcesResponse>>

    companion object Factory {
        private const val BASE_URL = "https://newsapi.org/v2/"

        // TODO: Move to DI.
        fun getSourceService(): SourcesService {
            val interceptor = HttpLoggingInterceptor()
            interceptor.level = HttpLoggingInterceptor.Level.BODY
            val client = OkHttpClient.Builder().addInterceptor(interceptor).build()
            val service = Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(LiveDataCallAdapterFactory())
                    .client(client)
                    .build()
                    .create(SourcesService::class.java)

            Log.d("Service", service.toString())
            return service
        }
    }
}