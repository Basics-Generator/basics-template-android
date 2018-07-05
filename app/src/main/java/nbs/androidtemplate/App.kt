package nbs.androidtemplate

import android.app.Application
import android.text.TextUtils

import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.toolbox.Volley

class App : Application() {

    private var mRequestQueue: RequestQueue? = null


    //*************************************************************************
    // REQUEST
    //*************************************************************************
    val requestQueue: RequestQueue?
        get() {
            if (mRequestQueue == null) {
                mRequestQueue = Volley.newRequestQueue(applicationContext)
            }

            return mRequestQueue
        }

    override fun onCreate() {
        super.onCreate()

        instance = this
        mRequestQueue = Volley.newRequestQueue(applicationContext)
    }

    fun <T> addToRequestQueue(req: Request<T>, tag: String) {
        // set the default tag if tag is empty
        req.tag = if (TextUtils.isEmpty(tag)) TAG else tag
        requestQueue?.add(req)
    }

    companion object {
        val TAG = App::class.java.simpleName

        @get:Synchronized
        var instance: App? = null
            private set
    }
}
