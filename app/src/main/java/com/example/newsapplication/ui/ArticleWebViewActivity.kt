package com.example.newsapplication.ui

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.example.newsapplication.R
import kotlinx.android.synthetic.main.activity_article_web_view.*

class ArticleWebViewActivity : AppCompatActivity() {
    var url = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_article_web_view)

        url = intent.getStringExtra("url")
        webView.loadUrl(url)
    }
}
