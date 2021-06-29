package com.bhardwaj.newszilla.utils

import android.app.Application
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.toolbox.Volley

class NewsZillaInstance : Application() {

    private var mRequestQueue: RequestQueue? = null

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