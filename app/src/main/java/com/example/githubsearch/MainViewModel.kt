package com.example.githubsearch

import android.annotation.SuppressLint
import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.githubsearch.model.UsersResponse
import com.example.githubsearch.network.ApiService
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import java.util.concurrent.TimeUnit
import javax.xml.datatype.DatatypeConstants.SECONDS

class MainViewModel(private val service : ApiService, application: Application) : AndroidViewModel(application){
    private var usersList = MutableLiveData<UsersResponse>()

    @SuppressLint("CheckResult")
    fun getUserGithub(name : String, page : String) : LiveData<UsersResponse>{
        service.getUsersGithub(name, page)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribe({
                usersList.value = it
            },{
                usersList.value = null
            })
        return usersList
    }
}