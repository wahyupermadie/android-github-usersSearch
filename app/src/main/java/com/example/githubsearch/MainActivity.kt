package com.example.githubsearch

import android.app.ProgressDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.githubsearch.model.Items
import kotlinx.android.synthetic.main.activity_main.*
import org.jetbrains.anko.indeterminateProgressDialog
import org.jetbrains.anko.toast
import org.koin.android.viewmodel.ext.android.viewModel
import androidx.appcompat.widget.SearchView
import androidx.core.view.MenuItemCompat
import androidx.recyclerview.widget.RecyclerView


class MainActivity : AppCompatActivity() {
    val mainViewModel : MainViewModel by viewModel()
    private var adapter: UsersAdapter? = null
    private var lists : MutableList<Items>? = mutableListOf()
    private lateinit var dialog : ProgressDialog
    private var isLoading = false
    private var perPage = 0
    private var searchValue = "wahyupermadie"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        adapter = UsersAdapter(this)
        rv_users_list.adapter = adapter
        val layoutManager = LinearLayoutManager(this)
        rv_users_list.layoutManager = layoutManager
//        rv_users_list.addItemDecoration(DividerItemDecoration(this, DividerItemDecoration.VERTICAL))

        dialog = indeterminateProgressDialog("Please wait..","Wait..")
        dialog.setCancelable(false)
        dialog.show()
        initLoadMore()
        getDataUsers(searchValue, perPage.toString())
    }

    private fun getDataUsers(nama : String, page : String) {
        mainViewModel.getUserGithub(nama,page).observe(this, Observer {
            lists?.clear()
            it.items?.toMutableList()?.let { it1 -> lists?.addAll(it1) }
            lists?.let { it1 -> adapter?.addUsers(it1, perPage) }
            if (it.totalCount == 0){
                toast("Tidak Ada User Terkait")
            }
            adapter?.notifyDataSetChanged()
            dialog.dismiss()
        })
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.search_menu, menu)
        val searchView = MenuItemCompat.getActionView(menu?.findItem(R.id.search_menu)) as SearchView
        searchView.queryHint = "Search Github Users"
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {
                getDataUsers(query.toString(), perPage.toString())
                searchValue = query.toString()
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return true
            }

        })
        return super.onCreateOptionsMenu(menu)
    }

    private fun initLoadMore() {
        rv_users_list.addOnScrollListener(object : RecyclerView.OnScrollListener() {

            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                val layoutManager = recyclerView.getLayoutManager() as LinearLayoutManager
                if (!isLoading) {
                    if (layoutManager.findLastCompletelyVisibleItemPosition() === lists!!.size - 1) {
                        perPage += 10
                        getDataUsers(searchValue, perPage.toString())
                        isLoading = false
                    }
                }
            }
        })
    }
}
