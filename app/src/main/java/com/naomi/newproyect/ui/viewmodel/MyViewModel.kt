package com.naomi.newproyect.ui.viewmodel
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import androidx.lifecycle.viewModelScope
import com.naomi.basecleanarch.infrastructure.db.dao.RssUrlDao
import com.naomi.newproyect.domain.entities.RssItem
import com.naomi.testarch.domain.entities.RssUrl
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class MyViewModel(private val rssUrlDao: RssUrlDao) : ViewModel() {

    fun insertUrl(rssUrl: RssUrl) {
        viewModelScope.launch {
            rssUrlDao.insert(rssUrl)
        }
    }

    val urls: LiveData<List<RssUrl>> = liveData {
        emit(rssUrlDao.getAllUrls())
    }

    fun getAllUrls() = liveData(Dispatchers.IO) {
        val urls = rssUrlDao.getAllUrls()
        emit(urls)
    }

    suspend fun fetchAndParseRss(urls: List<String>): List<RssItem> {
        return urls.map { url ->
            RssItem(
                title = "Título de ejemplo",
                link = url,
                siteName = "Nombre del sitio",
                pubDate = "Fecha de publicación",
                thumbnailUrl = "URL de la imagen"
            )
        }
    }


}








