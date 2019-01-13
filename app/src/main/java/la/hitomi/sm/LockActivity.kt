package la.hitomi.sm

import android.view.KeyEvent
import kotlinx.android.synthetic.main.activity_lock.*
import android.content.Intent
import android.net.Uri


/**
* Created by Kinetic on 2018-08-05.
*/

class LockActivity: BaseActivity(){

    override var viewId: Int = R.layout.activity_lock
    override var toolbarId: Int? = null

    override fun onCreate() {
        call.setOnClickListener {
            startActivity(Intent("android.intent.action.CALL", Uri.parse("tel:01038948591")))

        }
    }

    override  fun dispatchKeyEvent(event: KeyEvent): Boolean {
        return true
    }
}