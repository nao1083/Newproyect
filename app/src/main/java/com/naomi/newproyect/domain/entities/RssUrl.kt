package com.naomi.testarch.domain.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "rss_urls")
data class RssUrl(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val url: String
)
