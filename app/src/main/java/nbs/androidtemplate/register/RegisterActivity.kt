package nbs.androidtemplate.register

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import nbs.androidtemplate.R

class RegisterActivity : AppCompatActivity(), RegisterInterface {

    private val presenter = RegisterPresenter(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)
        presenter.register("vince", "vince", "last", "first", "em", "phon", "FR", "city", "99520", "28 rue jean dudu", "14/04/99", "description")
    }


    ///////////////////////////////////////////////////////////////////////////
    //      RegisterInterface
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
