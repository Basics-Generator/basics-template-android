package nbs.androidtemplate.login

import android.util.Log
import nbs.androidtemplate.BuildConfig
import org.json.JSONException
import org.json.JSONObject

/**
 * Created by avallier on 24/01/2018.
 */
class LoginPresenter(private var loginInterface: LoginInterface) : LoginInterface {

    private val tag = "LoginPresenter"
    private val interactor = LoginInteractor(this)

    fun login(username : String, password : String) {

        val jsonObject = getLoginJsonObject(username, password)
        val url = getLoginUrl()

        if(jsonObject == null){
            displayError()
        } else{
            interactor.login(url,jsonObject)
        }
    }

    private fun getLoginJsonObject(username: String, password: String): JSONObject? {
        val jsonObject = JSONObject()
        return try {
            jsonObject.put("username", username)
            jsonObject.put("password", password)
            jsonObject
        } catch (e: JSONException) {
            Log.e(tag, "execut, jsonObjec: $e")
            null
        }
    }

    private fun getLoginUrl() = BuildConfig.BaseUrl + "/api/user/login"

    ///////////////////////////////////////////////////////////////////////////
    //      LoginInterface
    ///////////////////////////////////////////////////////////////////////////

    override fun succes(result: Boolean?) {

        result?.let {
            loginInterface.succes(true)
        } ?: run {
            displayError()
        }
    }

    override fun displayInternetError() {
        loginInterface.displayInternetError()
    }

    override fun displayError() {
        loginInterface.displayError()
    }
}
