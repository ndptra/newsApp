package com.example.newsapplication.ui.adapter

import android.annotation.SuppressLint
import android.databinding.ViewDataBinding
import android.os.AsyncTask
import android.support.annotation.MainThread
import android.support.v7.util.DiffUtil
import android.support.v7.widget.RecyclerView
import android.view.ViewGroup

/**
 * Created by Rizky on 7/3/2018.
 */
abstract class DataBoundListAdapter<T, V : ViewDataBinding> : RecyclerView.Adapter<DataBoundViewHolder<V>>(){

    private var items: List<T>? = null

    private var dataVersion = 0

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DataBoundViewHolder<V> {
        val binding = createBinding(parent)
        return DataBoundViewHolder(binding)
    }

    protected abstract fun createBinding(parent: ViewGroup): V

    override fun onBindViewHolder(holder: DataBoundViewHolder<V>, position: Int) {
        items?.let {
            bind(holder.binding, it[position],position)
            holder.binding.executePendingBindings()
        }
    }

    @SuppressLint("StaticFieldLeak")
    @MainThread
    fun replace(update: List<T>?) {
        dataVersion++
        when {
            items == null -> {
                if (update == null) {
                    return
                }
                items = update
                notifyDataSetChanged()
            }
            update == null -> {
                val oldSize = items?.size ?: 0
                items = null
                notifyItemRangeRemoved(0, oldSize)
            }
            else -> {
                val startVersion = dataVersion
                val oldItems = items
                object : AsyncTask<Void, Void, DiffUtil.DiffResult>() {
                    override fun doInBackground(vararg voids: Void): DiffUtil.DiffResult {
                        return DiffUtil.calculateDiff(object : DiffUtil.Callback() {
                            override fun getOldListSize(): Int = oldItems?.size ?: 0

                            override fun getNewListSize(): Int = update.size

                            override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
                                val oldItem = oldItems!![oldItemPosition]
                                val newItem = update[newItemPosition]
                                return this@DataBoundListAdapter.areItemsTheSame(oldItem, newItem)
                            }

                            override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
                                val oldItem = oldItems!![oldItemPosition]
                                val newItem = update[newItemPosition]
                                return this@DataBoundListAdapter.areContentsTheSame(oldItem, newItem)
                            }
                        })
                    }

                    override fun onPostExecute(diffResult: DiffUtil.DiffResult) {
                        if (startVersion != dataVersion) {
                            // ignore update
                            return
                        }
                        items = update
                        diffResult.dispatchUpdatesTo(this@DataBoundListAdapter)

                    }
                }.execute()
            }
        }
    }

    fun getIndex(item: T) : Int = items?.indexOf(item) ?: 0

    protected abstract fun bind(binding: V, item: T, position: Int)

    protected abstract fun areItemsTheSame(oldItem: T, newItem: T): Boolean

    protected abstract fun areContentsTheSame(oldItem: T, newItem: T): Boolean



    override fun getItemCount(): Int {
//        Timber.d("item count : %s",items?.size)
        return items?.size ?: 0
    }


}