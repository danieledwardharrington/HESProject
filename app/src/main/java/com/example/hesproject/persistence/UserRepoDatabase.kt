package com.example.hesproject.persistence

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [UserRepoEntity::class], version = 1)
abstract class UserRepoDatabase: RoomDatabase() {

    abstract fun repoDao(): RepoDao

    companion object {
        private var instance: UserRepoDatabase? = null

        fun getInstance(context: Context): UserRepoDatabase? {
            if (instance == null) {
                synchronized(UserRepoDatabase::class) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        UserRepoDatabase::class.java,
                        "repo_db.db"
                    ).fallbackToDestructiveMigration().build()
                }
            }

            return instance
        }
    }
}