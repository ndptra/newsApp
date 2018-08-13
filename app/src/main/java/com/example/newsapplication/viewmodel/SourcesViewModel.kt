package com.example.newsapplication.viewmodel

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.ViewModel
import com.example.newsapplication.repo.SourcesRepo
import com.example.newsapplication.model.Sources
import com.example.newsapplication.model.network.Resource
import com.example.newsapplication.service.SourcesService


/**
 * Created by Rizky on 7/2/2018.
 */
class SourcesViewModel : ViewModel(){
    private var sources: LiveData<Resource<List<Sources>?>>
    init {
        val sourcesRepo = SourcesRepo(SourcesService.getSourceService())

        sources = sourcesRepo.getSources()
    }

    fun getSources() = sources


}