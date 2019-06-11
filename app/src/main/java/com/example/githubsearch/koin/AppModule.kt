package com.example.githubsearch.koin

import com.example.githubsearch.MainViewModel
import com.google.gson.Gson
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {
    single { Gson() }
    viewModel { MainViewModel(get(), get()) }
}