package com.example.hesproject.remote

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class UserRepoModel {

    @SerializedName("name")
    @Expose
    private var name: String = ""

    @SerializedName("owner")
    @Expose
    private var owner: Owner = Owner()

    @SerializedName("description")
    @Expose
    private var description: String = ""

    @SerializedName("language")
    @Expose
    private var language: String = ""

    private var favorited: Boolean = false

    class Owner() {
        @SerializedName("avatar_url")
        @Expose
        private var aUrl: String = ""

        fun getAvatarUrl(): String {
            return aUrl
        }

        fun setAvatarUrl(url: String) {
            aUrl = url
        }
    }

    fun getName(): String {
        return name
    }

    fun setName(n: String){
        name = n
    }

    fun getDescription(): String {
        return description
    }

    fun setDescription(desc: String) {
        description = desc
    }

    fun getLanguage(): String {
        return language
    }

    fun setLanguage(lang: String) {
        language = lang
    }

    fun getFavorited(): Boolean {
        return favorited
    }

    fun setFavorited(fav: Boolean) {
        favorited = fav
    }

    fun getOwner(): Owner {
        return owner
    }

    fun setOwner(own: Owner) {
        owner = own
    }

    override fun toString(): String {
        return "UserRepoModel(name='$name', owner=$owner, description='$description', language='$language')"
    }
}