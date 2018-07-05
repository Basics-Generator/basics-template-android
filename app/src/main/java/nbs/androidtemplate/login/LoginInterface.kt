package nbs.androidtemplate.login

/**
 * Created by vkeresztes on 26/04/2018.
 */
interface LoginInterface {

    fun succes(result: Boolean?)

    fun displayInternetError()

    fun displayError()
}