package com.example.hesproject.remote

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class GitHubResponse(@SerializedName("items")
                          @Expose
                          val results: List<UserRepoModel>)