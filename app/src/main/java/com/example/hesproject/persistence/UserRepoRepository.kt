package com.example.hesproject.persistence

import android.app.Application
import android.util.Log
import io.reactivex.Observable

class UserRepoRepository(application: Application) {
    private val database: UserRepoDatabase? = UserRepoDatabase.getInstance(application)
    private var repoDao: RepoDao = database!!.repoDao()

/*
    init {
        val database: UserRepoDatabase? = UserRepoDatabase.getInstance(application)
        repoDao = database!!.repoDao()
    }
*/

    fun insert(rEntity: UserRepoEntity) {
        Log.d("Repository", "INSERT")
        Log.d("REPOSITORY INSERT", rEntity.toString())
        repoDao.insert(rEntity)
    }

    fun delete(rEntity: UserRepoEntity) {
        Log.d("Repository", "INSERT")
        repoDao.delete(rEntity)
    }

    fun getAllRepos(): Observable<List<UserRepoEntity>> {
        return repoDao.getAllRepos()
    }

}