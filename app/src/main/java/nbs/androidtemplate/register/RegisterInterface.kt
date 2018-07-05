package nbs.androidtemplate.register

/**
 * Created by vkeresztes on 26/04/2018.
 */
interface RegisterInterface {

    fun succes(result: Boolean?)

    fun displayInternetError()

    fun displayError()
}