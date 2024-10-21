package com.naomi.newproyect.ui

import NewsAdapter
import android.content.Intent
import android.net.Uri
import kotlinx.coroutines.launch
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.EditText
import android.widget.TextView
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.room.Room
import com.naomi.basecleanarch.infrastructure.db.AppDatabase
import com.naomi.newproyect.R
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import com.naomi.newproyect.domain.entities.RssItem
import com.naomi.newproyect.ui.viewmodel.MyViewModel
import com.naomi.testarch.domain.entities.RssUrl


class MainActivity : AppCompatActivity() {
    private lateinit var toolbar: Toolbar
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: NewsAdapter
    private lateinit var viewModel: MyViewModel
    private lateinit var database: AppDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        database = Room.databaseBuilder(applicationContext, AppDatabase::class.java, "rss_database").build()

        viewModel = MyViewModel(database.rssUrlDao())

        toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)


        recyclerView = findViewById(R.id.recyclerView)
        adapter = NewsAdapter(emptyList()) { url -> openBrowser(url) }
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter

        toolbar.setOnMenuItemClickListener { menuItem ->
            when (menuItem.itemId) {
                R.id.action_add -> {
                    showAddUrlDialog()
                    true
                }
                else -> false
            }
        }
        viewModel.urls.observe(this, Observer { urls ->
            if (urls.isEmpty()) {
                findViewById<TextView>(R.id.empty_state).visibility = View.VISIBLE
            } else {
                findViewById<TextView>(R.id.empty_state).visibility = View.GONE
                lifecycleScope.launch {
                    val newsItems = viewModel.fetchAndParseRss(urls.map { it.url })
                    adapter.updateData(newsItems)
                }
            }
        })
    }

    private fun showAddUrlDialog() {
        val builder = AlertDialog.Builder(this)
        val input = EditText(this)
        builder.setView(input)
        builder.setTitle("Agregar URL")
        builder.setPositiveButton("Agregar") { dialog, which ->
            val url = input.text.toString()
            if (url.isNotBlank()) {
                saveUrl(url)
            }
        }
        builder.setNegativeButton("Cancelar") { dialog, which -> dialog.cancel() }
        builder.show()
    }

    private fun saveUrl(url: String) {
        lifecycleScope.launch {
            val rssUrl = RssUrl(url = url)
            viewModel.insertUrl(rssUrl)
        }
    }

    private fun openBrowser(url: String) {
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
        startActivity(intent)
    }
}
