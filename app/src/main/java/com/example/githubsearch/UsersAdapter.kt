package com.example.githubsearch

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import androidx.annotation.NonNull
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.githubsearch.databinding.ItemRowBinding
import com.example.githubsearch.model.Items

class UsersAdapter(private val context : Context) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var items : MutableList<Items>? = mutableListOf()
    private var page = 10
    private val VIEW_TYPE_ITEM = 0
    private val VIEW_TYPE_LOADING = 1
    fun addUsers(items : MutableList<Items>, page : Int) {
        this.items = items
        this.page = page
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val binding = DataBindingUtil.inflate<ItemRowBinding>(
            LayoutInflater.from(parent.context),
            R.layout.item_row, parent, false
        )

        if (viewType == VIEW_TYPE_ITEM){
            return UsersViewHolder(binding)
        }else{
            val view = LayoutInflater.from(parent.getContext()).inflate(R.layout.progress_bar, parent, false)
            return LoadingViewHolder(view)
        }

    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is UsersViewHolder) {
            holder.view.items = this.items?.get(position)
        } else if (holder is LoadingViewHolder) {
            showLoadingView(holder, position)
        }
    }

    private fun showLoadingView(viewHolder: LoadingViewHolder, position: Int) {

    }

    override fun getItemViewType(position: Int): Int {
        Log.d("DATA_SIZE", page.toString()+" SIZE "+position)
        return if (position == page - 1) VIEW_TYPE_LOADING else VIEW_TYPE_ITEM
    }

    override fun getItemCount(): Int {
        Log.d("DATA_SIZE"," SIZE XX "+this.items)
        return if (this.items == null) 0 else this.items!!.size
    }

    private inner class LoadingViewHolder(view:View) : RecyclerView.ViewHolder(view){
        internal var progressBar: ProgressBar
        init {
            progressBar = itemView.findViewById(R.id.progressBar)
        }
    }

    class UsersViewHolder(val view : ItemRowBinding): RecyclerView.ViewHolder(view.root)
}