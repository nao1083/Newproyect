package com.naomi.basecleanarch.infrastructure.db

import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import android.content.Context
import com.naomi.basecleanarch.infrastructure.db.dao.RssUrlDao
import com.naomi.testarch.domain.entities.RssUrl

@Database(entities = [RssUrl::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun rssUrlDao(): RssUrlDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "rss_database"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}
