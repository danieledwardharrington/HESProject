package com.example.hesproject.persistence

import androidx.room.*
import io.reactivex.Completable
import io.reactivex.Observable

@Dao
interface RepoDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(userRepo: UserRepoEntity): Long

    @Delete
    fun delete(userRepo: UserRepoEntity): Int

    @Query("SELECT * FROM repo_table")
    fun getAllRepos(): Observable<List<UserRepoEntity>>
}