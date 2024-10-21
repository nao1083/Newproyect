package com.naomi.newproyect.domain.entities

data class RssItem(
    val title: String,
    val link: String,
    val pubDate: String,
    val siteName: String,
    val thumbnailUrl: String?
)
