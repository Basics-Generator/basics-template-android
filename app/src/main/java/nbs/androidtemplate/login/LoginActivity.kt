package nbs.androidtemplate.login

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import nbs.androidtemplate.R

class LoginActivity : AppCompatActivity(), LoginInterface {

    val loginPresenter = LoginPresenter(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        loginPresenter.login("vince", "vince")
    }


    ///////////////////////////////////////////////////////////////////////////
    //      LoginInterface
    ///////////////////////////////////////////////////////////////////////////

    override fun succes(result: Boolean?) {
        runOnUiThread {
            TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        }
    }

    override fun displayInternetError() {
        runOnUiThread {
            TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        }
    }

    override fun displayError() {
        runOnUiThread {
            TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        }
    }
}
