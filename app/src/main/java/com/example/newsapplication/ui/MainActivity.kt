package com.example.newsapplication.ui

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProvider
import android.arch.lifecycle.ViewModelProviders
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.View
import android.widget.Toast
import com.example.newsapplication.R
import com.example.newsapplication.model.Sources
import com.example.newsapplication.model.network.Status

import com.example.newsapplication.ui.adapter.SourcesListAdapter
import com.example.newsapplication.utils.getViewModel
import com.example.newsapplication.viewmodel.SourcesViewModel
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    private val sourcesViewModel by lazy { getViewModel<SourcesViewModel>() }
    private var sources: List<Sources>? = listOf()
    private lateinit var sourceAdapter: SourcesListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    override fun onStart() {
        super.onStart()

        sourceAdapter = SourcesListAdapter()

        val layoutManager = LinearLayoutManager(this)
        layoutManager.orientation = LinearLayoutManager.VERTICAL
        sourcesRecyclerView.layoutManager = layoutManager
        sourcesRecyclerView.adapter = sourceAdapter
        sourcesRecyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView?, dx: Int, dy: Int) {
                val layoutManager = recyclerView?.layoutManager as LinearLayoutManager

                val lastPosition = layoutManager
                        .findLastVisibleItemPosition()
                if (lastPosition + 1 == sourceAdapter.itemCount) {

                }
            }
        })


        initObserver()
    }

    override fun onResume() {
        super.onResume()
//        sourcesViewModel.setTrigger(true)
    }

    fun initObserver(){
        sourcesViewModel.getSources().observe(this, Observer {
            Log.d("observed", it?.status.toString())
            val data = it?.data
            Log.d("observed", "data: $data")
            sources = listOf()
            when {
                it?.status == Status.SUCCESS -> {
                    sourcesProgressBar.visibility = View.GONE
                    sourcesRecyclerView.visibility = View.VISIBLE

                    sources = data
                    Log.d("sources", sources.toString())
                }
                it?.status == Status.LOADING -> {
                    sourcesProgressBar.visibility = View.VISIBLE
                    sourcesRecyclerView.visibility = View.GONE
                }
                else -> {
                    sourcesProgressBar.visibility = View.GONE
                    sourcesRecyclerView.visibility = View.GONE
                    sources = listOf()
                }
            }
            updateRecyclerView()
        })
    }

    private fun updateRecyclerView() {
        var booleanCheck = false
        sources?.let {
            if(it.isNotEmpty()) booleanCheck = true
        }
        if(sources!=null && booleanCheck){
            sourceAdapter.replace(sources)
            Log.d("sources", sources.toString())
        }
        else {
            //show no data notification if there is any
            sourceAdapter.replace(listOf())
        }
//        sources = listOf(Sources("abc", "ABC News", "ABC News description", "abcnews.com", "General", "EN", "US"), Sources("Goal", "Goal.com", "Goal.com description", "goal.com","Sports","En", "US"))
//        sourceAdapter.replace(sources)
        sourceAdapter.notifyDataSetChanged()
    }
}
