package com.example.newsapplication.ui.adapter

import android.content.Context
import android.content.Intent
import android.databinding.DataBindingUtil
import android.view.LayoutInflater
import android.view.ViewGroup
import com.example.newsapplication.R
import com.example.newsapplication.databinding.ItemSourcesRecyclerViewBinding
import com.example.newsapplication.model.Sources
import com.example.newsapplication.ui.ArticlesSearchActivity
import java.util.*

/**
 * Created by Rizky on 7/3/2018.
 */
class SourcesListAdapter()
    : DataBoundListAdapter<Sources, ItemSourcesRecyclerViewBinding>()
{
    override fun createBinding(parent: ViewGroup): ItemSourcesRecyclerViewBinding =
            DataBindingUtil.inflate(LayoutInflater.from(parent.context), R.layout.item_sources_recycler_view, parent, false)

    override fun bind(binding: ItemSourcesRecyclerViewBinding, item: Sources, position: Int) {
        binding.sources = item

        binding.root.setOnClickListener {
            //Goto articles page
            val context = binding.root.context
            val intent = Intent(context, ArticlesSearchActivity::class.java)
            intent.putExtra("source", item.id)
            context.startActivity(intent)
        }
    }

    override fun areItemsTheSame(oldItem: Sources, newItem: Sources): Boolean =
            Objects.equals(oldItem.id, newItem.id)

    override fun areContentsTheSame(oldItem: Sources, newItem: Sources): Boolean =
            Objects.equals(oldItem.description, newItem.description)

}