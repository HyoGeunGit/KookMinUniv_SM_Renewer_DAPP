package la.hitomi.sm

import android.content.Intent
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.activity_register.*
import org.jetbrains.anko.toast

/**
 * Created by Kinetic on 2018-08-05.
 */

class SignupActivity : BaseActivity(){

    override var viewId: Int = R.layout.activity_register
    override var toolbarId: Int? = null

    override fun onCreate() {
        sign_btn.setOnClickListener {
            toast("Signup Successfully")
            finish()
        }
    }

}