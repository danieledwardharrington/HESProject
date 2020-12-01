package com.example.hesproject.viewmodels

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class RepoViewModelFactory: ViewModelProvider.Factory {
    private lateinit var mApplication: Application

    constructor(application: Application) {
        mApplication = application
    }

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return UserRepoViewModel(mApplication) as T
    }
}