package nbs.androidtemplate.register

import android.util.Log
import nbs.androidtemplate.BuildConfig
import org.json.JSONException
import org.json.JSONObject

/**
 * Created by avallier on 24/01/2018.
 */
class RegisterPresenter(private var mInterface: RegisterInterface) : RegisterInterface {

    private val tag = "RegisterPresenter"
    private val interactor = RegisterInteractor(this)

    fun register(username : String, password : String, lastname : String, firsname : String, email : String, phone : String, country : String, city : String, postCode : String, address : String, birthday : String, desc : String) {

        val jsonObject = getRegisterJsonObject(username, password, lastname, firsname, email, phone, country, city, postCode, address, birthday, desc)
        val url = getRegisterUrl()

        if(jsonObject == null){
            displayError()
        } else{
            interactor.register(url,jsonObject)
        }
    }

    private fun getRegisterJsonObject(username : String, password : String, lastname : String, firsname : String, email : String, phone : String, country : String, city : String, postCode : String, address : String, birthday : String, desc : String): JSONObject? {
        val jsonObject = JSONObject()
        return try {
            jsonObject.put("username", username)
            jsonObject.put("password", password)
            jsonObject.put("lastname", lastname)
            jsonObject.put("firsname", firsname)
            jsonObject.put("email", email)
            jsonObject.put("phone", phone)
            jsonObject.put("country", country)
            jsonObject.put("city", city)
            jsonObject.put("postalCode", postCode)
            jsonObject.put("adress", address)
            jsonObject.put("birthday", birthday)
            jsonObject.put("description", desc)
            jsonObject
        } catch (e: JSONException) {
            Log.e(tag, "execut, jsonObjec: $e")
            null
        }
    }

    private fun getRegisterUrl() = BuildConfig.BaseUrl + "/api/user/"

    ///////////////////////////////////////////////////////////////////////////
    //      RegisterInterface
    ///////////////////////////////////////////////////////////////////////////

    override fun succes(result: Boolean?) {

        result?.let {
            mInterface.succes(true)
        } ?: run {
            displayError()
        }
    }

    override fun displayInternetError() {
        mInterface.displayInternetError()
    }

    override fun displayError() {
        mInterface.displayError()
    }
}
