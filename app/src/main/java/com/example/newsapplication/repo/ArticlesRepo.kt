package com.example.newsapplication.repo

import android.arch.lifecycle.LiveData
import com.example.newsapplication.AppExecutors
import com.example.newsapplication.apiKey
import com.example.newsapplication.model.Articles
import com.example.newsapplication.model.ArticlesResponse
import com.example.newsapplication.model.network.Resource
import com.example.newsapplication.service.ArticlesService
import com.example.newsapplication.service.NetworkBoundResource
import com.example.newsapplication.utils.AbsentLiveData

class ArticlesRepo(
        private val articlesService: ArticlesService,
        private val appExecutors: AppExecutors = AppExecutors()
){
    fun getArticles(query: String, sources: String, page: Int): LiveData<Resource<ArticlesResponse?>>{
        return object : NetworkBoundResource<ArticlesResponse, ArticlesResponse>(appExecutors){
            var resultDb: ArticlesResponse? = null

            override fun saveCallResult(item: ArticlesResponse) {
                resultDb = item
            }

            override fun shouldFetch(data: ArticlesResponse?): Boolean {
                return true
            }

            override fun loadFromDb(): LiveData<ArticlesResponse> {
                return if(resultDb == null){
                    AbsentLiveData()
                }
                else{
                    object : LiveData<ArticlesResponse>() {
                        override fun onActive() {
                            super.onActive()
                            value = resultDb
                        }
                    }
                }
            }

            override fun createCall(): LiveData<Resource<ArticlesResponse>> {
                return articlesService.getAllArticles(query,sources, page, apiKey)
            }
        }.asLiveData()
    }
}