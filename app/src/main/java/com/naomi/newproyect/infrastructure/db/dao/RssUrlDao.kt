package com.naomi.basecleanarch.infrastructure.db.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.naomi.testarch.domain.entities.RssUrl


@Dao
interface RssUrlDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(rssUrl: RssUrl)

    @Query("SELECT * FROM rss_urls")
    suspend fun getAllUrls(): List<RssUrl>
}