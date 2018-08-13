package com.example.newsapplication.viewmodel

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import com.example.newsapplication.model.Articles
import com.example.newsapplication.model.ArticlesResponse
import com.example.newsapplication.model.network.Resource
import com.example.newsapplication.repo.ArticlesRepo
import com.example.newsapplication.service.ArticlesService
import android.arch.lifecycle.Transformations.switchMap

/**
 * Created by Rizky on 7/5/2018.
 */
class ArticlesViewModel: ViewModel(){
    private lateinit var articles: LiveData<Resource<ArticlesResponse?>>
    val articlesRepo: ArticlesRepo = ArticlesRepo(ArticlesService.getSourceService())
    private var trigger: MutableLiveData<Boolean> = MutableLiveData()
    private var query = ""
    private var page = 0
    private var source = ""
    init {
        articles = switchMap(trigger, {articlesRepo.getArticles(query, source, page)})
    }

    fun setData(query: String, source: String, page: Int){
        this.query = query
        this.page = page
        this.source = source
        setTrigger(true)
    }

    private fun setTrigger(trigger: Boolean){
        this.trigger.value = trigger
    }

    fun getArticles() = articles
}