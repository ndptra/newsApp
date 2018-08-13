package com.example.newsapplication.service

import android.arch.lifecycle.LiveData
import android.util.Log
import com.example.newsapplication.apiKey
import com.example.newsapplication.model.ArticlesResponse
import com.example.newsapplication.model.network.Resource
import com.example.newsapplication.utils.LiveDataCallAdapterFactory
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query
import java.nio.channels.spi.AbstractSelectionKey

interface ArticlesService{
    @GET("everything") fun getAllArticles(@Query("q") q: String? = null, @Query("sources") source: String, @Query("page") page: Int, @Query("apiKey") apiKey: String): LiveData<Resource<ArticlesResponse>>

    companion object Factory {
        private const val BASE_URL = "https://newsapi.org/v2/"

        // TODO: Move to DI.
        fun getSourceService(): ArticlesService {
            val interceptor = HttpLoggingInterceptor()
            interceptor.level = HttpLoggingInterceptor.Level.BODY
            val client = OkHttpClient.Builder().addInterceptor(interceptor).build()
            val service = Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(LiveDataCallAdapterFactory())
                    .client(client)
                    .build()
                    .create(ArticlesService::class.java)

            Log.d("Service", service.toString())
            return service
        }
    }
}