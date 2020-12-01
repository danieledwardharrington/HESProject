package com.example.hesproject.remote

import io.reactivex.Observable
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

interface GitHubService {

    @Headers("Authorization: token 0f6144c816bdb07980980aace498fc7a385e64ef")
    @GET("/search/repositories")
    fun getRemoteRepos(@Query("q") filter: String): Observable<GitHubResponse>

    companion object {
        fun create(): GitHubService {
            val client = OkHttpClient
                .Builder()
                .build()

            val retrofit = Retrofit.Builder()
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .baseUrl("https://api.github.com")
                .build()

            return retrofit.create(GitHubService::class.java)
        }
    }
}