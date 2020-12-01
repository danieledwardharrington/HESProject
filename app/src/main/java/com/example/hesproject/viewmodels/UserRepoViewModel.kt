package com.example.hesproject.viewmodels

import android.app.Application
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.hesproject.adapter.FavReposAdapter
import com.example.hesproject.adapter.RestReposAdapter
import com.example.hesproject.persistence.UserRepoEntity
import com.example.hesproject.persistence.UserRepoRepository
import com.example.hesproject.remote.GitHubResponse
import com.example.hesproject.remote.GitHubService
import com.example.hesproject.remote.UserRepoModel
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class UserRepoViewModel(application: Application): ViewModel() {

    private var repository: UserRepoRepository = UserRepoRepository(application)
    private var compositeDisposable: CompositeDisposable = CompositeDisposable()
    private var responseRepos: List<UserRepoModel> = ArrayList()
    var favRepos: MutableLiveData<List<UserRepoEntity>> = MutableLiveData()

    fun insert(repo: UserRepoEntity) {
        Completable.fromAction { repository.insert(repo) }.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe()
    }

    fun delete(repo: UserRepoEntity) {
        Completable.fromAction { repository.delete(repo) }.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe()
    }

    fun getAllDBRepos() {
        repository.getAllRepos()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                favRepos.postValue(it)
            },{
                it.printStackTrace()
            })

/*        repository.getAllRepos().let {

            repository.getAllRepos().subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    Log.d("getAllDBRepos", "let -> subscribe")
                    Log.d("getAllDBRepos subscribe", it.size.toString())
                    fAdapter.submitList(ArrayList(it))
                },
                    {
                        Log.e("getAllRepos", it.message!!)
                    })
        }*/

        /*compositeDisposable.add(repository.getAllRepos().observeOn(AndroidSchedulers.mainThread()).subscribe({
            Log.d("getAllDBRepos", "subscribe adapter")
            Log.d("getAllDbRepos", "it SIZE: ${it.size}")
            for (entity in it) {
                Log.d("PRINTING SUBSCRIBE LIST", entity.getName())
            }
            adapter.submitList(it as ArrayList<UserRepoEntity>)
        }, { print("ERROR GET ALL DB: ${it.toString()}")}))*/


    }

    fun getRestUsers(filter: String, adapter: RestReposAdapter) {
        compositeDisposable.add(GitHubService.create().getRemoteRepos(filter)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribe({response -> onResponse(response, adapter)},
                {throwable -> onFailure(throwable)}))


    }

    private fun onResponse(response: GitHubResponse, adapter: RestReposAdapter) {
        Log.d("onResponse", "RESPONSE: ${response.toString()}")
        responseRepos = response.results
        adapter.submitList(ArrayList(responseRepos))
    }

    private fun onFailure(throwable: Throwable){

    }


    fun clearDisposable() {
        compositeDisposable.clear()
    }

}