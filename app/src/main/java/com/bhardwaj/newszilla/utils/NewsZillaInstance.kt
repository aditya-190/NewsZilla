package com.bhardwaj.newszilla.utils

import android.app.Application
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.toolbox.Volley
import com.bhardwaj.newszilla.repository.NewsRepository
import com.bhardwaj.newszilla.repository.model.NewsDatabase

class NewsZillaInstance : Application() {

    private var mRequestQueue: RequestQueue? = null

    val database by lazy { NewsDatabase.getDatabase(this) }
    val repository by lazy { NewsRepository(database.newsDao()) }

    override fun onCreate() {
        super.onCreate()
        instances = this
    }

    private val requestQueue: RequestQueue?
        get() {
            if (mRequestQueue == null)
                mRequestQueue = Volley.newRequestQueue(applicationContext)
            return mRequestQueue
        }

    fun <T> addToRequestQueue(req: Request<T>?) {
        requestQueue!!.add(req)
    }

    companion object {
        @get:Synchronized
        var instances: NewsZillaInstance? = null
            private set
    }
}