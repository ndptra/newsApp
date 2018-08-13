package com.example.newsapplication.ui

import android.arch.lifecycle.Observer
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.View
import android.widget.Toast
import com.example.newsapplication.R
import com.example.newsapplication.apiKey
import com.example.newsapplication.model.Articles
import com.example.newsapplication.model.ArticlesResponse
import com.example.newsapplication.model.network.Resource
import com.example.newsapplication.model.network.Status
import com.example.newsapplication.ui.adapter.ArticlesListAdapter
import com.example.newsapplication.utils.getViewModel
import com.example.newsapplication.viewmodel.ArticlesViewModel
import kotlinx.android.synthetic.main.activity_articles_search.*

class ArticlesSearchActivity : AppCompatActivity() {
    private val articlesViewModel by lazy { getViewModel<ArticlesViewModel>() }
    private var source = ""
    private var articles: List<Articles>? = listOf()
    private lateinit var articlesAdapter: ArticlesListAdapter
    private var currPage = 1
    private var query = ""

    override fun onStart() {
        super.onStart()

        articlesAdapter = ArticlesListAdapter()
        val layoutManager = LinearLayoutManager(this)
        layoutManager.orientation = LinearLayoutManager.VERTICAL
        articlesRecyclerView.layoutManager = layoutManager
        articlesRecyclerView.adapter = articlesAdapter
        articlesRecyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView?, dx: Int, dy: Int) {
                val layoutManager = recyclerView?.layoutManager as LinearLayoutManager

                val lastPosition = layoutManager
                        .findLastVisibleItemPosition()
                if (lastPosition + 1 == articlesAdapter.itemCount) {

                }
            }
        })

        initObserver()
        initListener()
    }

    private fun initListener() {
        nextPageButton.setOnClickListener {
            currPage++
            retrieveArticle()
        }
        prevPageButton.setOnClickListener {
            currPage--
            retrieveArticle()
        }
        searchButton.setOnClickListener {
            query = articlesSearchEditText.text.toString()
            currPage = 1
            retrieveArticle()
        }
    }

    private fun retrieveArticle(){
        articlesViewModel.setData(query, source, currPage)
        pageIndicator.text = currPage.toString()
        Log.d("retrieveArticle", "getArticles($query, $currPage, $source)")
    }

    private fun initObserver() {
        articlesViewModel.setData(query, source, currPage)
        articlesViewModel.getArticles().observe(this, Observer {
            articlesRetrieved(it)
        })
    }

    private fun articlesRetrieved(it: Resource<ArticlesResponse?>?) {
        val data = it?.data

        articles = listOf()
        when {
            it?.status == Status.SUCCESS -> {
                articlesProgressBar.visibility = View.GONE
                articlesRecyclerView.visibility = View.VISIBLE

                data?.let {
                    articles = it.articles
                }

            }
            it?.status == Status.LOADING -> {
                articlesProgressBar.visibility = View.VISIBLE
                articlesRecyclerView.visibility = View.GONE
            }
            else -> {
                articlesRecyclerView.visibility = View.GONE
                articlesProgressBar.visibility = View.GONE
                articles = listOf()
            }
        }

        if(currPage == 1) prevPageButton.visibility = View.GONE
        else prevPageButton.visibility = View.VISIBLE

        it?.data?.totalResults?.let {
            if(currPage*20 >= it){
                nextPageButton.visibility = View.GONE
            }
            else
                nextPageButton.visibility = View.VISIBLE
        }

        updateRecyclerView()
    }

    private fun updateRecyclerView() {
        var booleanCheck = false
        articles?.let {
            if(it.isNotEmpty()) booleanCheck = true

            if (articles!= null && booleanCheck){
                articlesAdapter.replace(articles)
            }
            else{
                articlesAdapter.replace(listOf())
            }
            articlesAdapter.notifyDataSetChanged()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_articles_search)

        source = intent.getStringExtra("source")
    }
}
