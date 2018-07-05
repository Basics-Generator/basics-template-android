package nbs.androidtemplate.login

import android.util.Log
import com.android.volley.*
import nbs.androidtemplate.network.Requester
import org.json.JSONException
import org.json.JSONObject

/**
 * Created by vkeresztes on 27/12/2017.
 */
class LoginInteractor(private var presenter : LoginInterface) {
    private val TAG : String = javaClass.simpleName

    /**
     * Handle result in another thread
     */
    fun login(url : String, jsonObject: JSONObject){
        Requester.postJSONWithHeader(url, jsonObject, Response.Listener<String> {
            response ->
            Thread(Runnable {
                val result = response.isNotEmpty()
                presenter.succes(result)
            }).start()

        },  Response.ErrorListener {
            error ->

            Thread(Runnable {
                displayError(error)

                val entry = Requester.getCache(url)
                if(entry != null) {
                    try {
                        val json = JSONObject(String(entry.data))
                        presenter.succes(true)
                    } catch (e: JSONException) {
                        Log.d(TAG, "JsonxException Not able to decode volley cache for $url")
                    }
                }
            }).start()
        }, null)
    }

    fun displayError(error : VolleyError){
        when(error){
            is TimeoutError, is NoConnectionError, is AuthFailureError, is ServerError, is NetworkError, is ParseError -> presenter.displayInternetError()
        }
    }
}