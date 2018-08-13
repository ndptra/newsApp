package com.example.newsapplication.repo

import android.arch.lifecycle.LiveData
import com.example.newsapplication.model.SourcesResponse
import com.example.newsapplication.model.network.Resource
import com.example.newsapplication.service.NetworkBoundResource
import com.example.newsapplication.service.SourcesService
import android.os.Handler
import android.util.Log
import com.example.newsapplication.AppExecutors
import com.example.newsapplication.apiKey
import com.example.newsapplication.model.Sources
import com.example.newsapplication.utils.AbsentLiveData

/**
 * Created by Rizky on 7/4/2018.
 */
public class SourcesRepo(
        private val sourcesService: SourcesService,
        private val appExecutors: AppExecutors = AppExecutors()
){
    val handler = Handler()
    fun getSources(): LiveData<Resource<List<Sources>?>>{
        return object : NetworkBoundResource<List<Sources>, SourcesResponse>(appExecutors){
            var resultDb = listOf<Sources>()
            override fun saveCallResult(item: SourcesResponse) {
                resultDb = item.sources
            }

            override fun shouldFetch(data: List<Sources>?): Boolean = true

            override fun loadFromDb(): LiveData<List<Sources>> {
                return if(resultDb == null){
                    AbsentLiveData()
                }
                else{
                    object : LiveData<List<Sources>>() {
                        override fun onActive() {
                            super.onActive()
                            value = resultDb
                        }
                    }
                }
            }

            override fun createCall(): LiveData<Resource<SourcesResponse>> {
                val res = sourcesService.getAllSources(apiKey)
                Log.d("result", res.value.toString())
                return res
            }

        }.asLiveData()
    }

}