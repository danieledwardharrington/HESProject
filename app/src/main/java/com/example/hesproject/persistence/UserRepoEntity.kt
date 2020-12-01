package com.example.hesproject.persistence

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "repo_table")
data class UserRepoEntity(@ColumnInfo(name = "name")
                     private var name: String = "",
                     @ColumnInfo(name = "description")
                     private var description: String = "",
                     @ColumnInfo(name = "language")
                     private var language: String = "",
                     @ColumnInfo(name = "aUrl")
                     private var avatarUrl: String = "",
                     @ColumnInfo(name = "favorited")
                     private var favorited: Boolean = false) {

    @PrimaryKey(autoGenerate = true)
    private var id: Long = 0
/*    @ColumnInfo(name = "name")
    private var name: String = ""
    @ColumnInfo(name = "description")
    private var description: String = ""
    @ColumnInfo(name = "language")
    private var language: String = ""
    @ColumnInfo(name = "aUrl")
    private var avatarUrl: String = ""
    @ColumnInfo(name = "favorited")
    private var favorited: Boolean = false*/

    fun getId(): Long {
        return id
    }

    fun setId(i: Long) {
        id = i
    }

    fun getName(): String? {
        return name
    }

    fun setName(n: String){
        name = n
    }

    fun getDescription(): String? {
        return description
    }

    fun setDescription(desc: String) {
        description = desc
    }

    fun getLanguage(): String? {
        return language
    }

    fun setLanguage(lang: String) {
        language = lang
    }

    fun setAvatarUrl(url: String) {
        avatarUrl = url
    }

    fun getAvatarUrl(): String {
        return avatarUrl
    }

    fun setFavorited(fav: Boolean) {
        favorited = fav
    }

    fun getFavorited(): Boolean {
        return favorited
    }

    override fun toString(): String {
        return "UserRepoEntity(name='$name', description='$description', language='$language', avatarUrl='$avatarUrl', repoID=${getId().toString()})"
    }


}