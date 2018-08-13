package com.example.newsapplication.ui.adapter

import android.content.Intent
import android.databinding.DataBindingUtil
import android.view.LayoutInflater
import android.view.ViewGroup
//import com.bumptech.glide.Glide
import com.example.newsapplication.R
import com.example.newsapplication.databinding.ItemArticlesRecyclerViewBinding
import com.example.newsapplication.model.Articles
import com.example.newsapplication.ui.ArticleWebViewActivity
import com.example.newsapplication.utils.GlideApp
import kotlinx.android.synthetic.main.item_articles_recycler_view.view.*
import java.util.*

/**
 * Created by Rizky on 7/5/2018.
 */
class ArticlesListAdapter()
    :DataBoundListAdapter<Articles, ItemArticlesRecyclerViewBinding>() {
    override fun createBinding(parent: ViewGroup): ItemArticlesRecyclerViewBinding =
            DataBindingUtil.inflate(LayoutInflater.from(parent.context), R.layout.item_articles_recycler_view, parent, false)

    override fun bind(binding: ItemArticlesRecyclerViewBinding, item: Articles, position: Int) {
        binding.article = item
        val context = binding.root.context
        item.urlToImage?.let {
            if(it.isNotEmpty()){
                GlideApp.with(context)
                        .load(it)
                        .fitCenter()
                        .into(binding.root.articleImage)
            }
        }

        binding.root.setOnClickListener {
            //goto webview activity
            val intent = Intent(context, ArticleWebViewActivity::class.java)
            intent.putExtra("url", item.url)

            context.startActivity(intent)
        }
    }

    override fun areItemsTheSame(oldItem: Articles, newItem: Articles): Boolean {
        return Objects.equals(oldItem.url, newItem.url)
    }

    override fun areContentsTheSame(oldItem: Articles, newItem: Articles): Boolean {
        return Objects.equals(oldItem,newItem)
    }
}