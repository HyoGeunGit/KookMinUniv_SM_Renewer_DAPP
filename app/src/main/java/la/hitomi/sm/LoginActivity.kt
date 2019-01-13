package la.hitomi.sm

import android.Manifest
import android.content.Intent
import com.gun0912.tedpermission.PermissionListener
import com.gun0912.tedpermission.TedPermission
import kotlinx.android.synthetic.main.activity_login.*
import org.jetbrains.anko.toast
import java.util.ArrayList

/**
 * Created by Kinetic on 2018-08-05.
 */

class LoginActivity: BaseActivity(), PermissionListener {

    override fun onPermissionGranted() {
        //toast("Permission Grant Successfully")
    }

    override fun onPermissionDenied(deniedPermissions: ArrayList<String>?) {
        //toast("Permission Grant Failed")
    }

    override var viewId: Int = R.layout.activity_login
    override var toolbarId: Int? = null

    override fun onCreate() {
        TedPermission.with(this)
                .setPermissionListener(this)
                .setDeniedMessage("If you reject permission,you can not use this service\n\nPlease turn on permissions at [Setting] > [Permission]")
                .setPermissions(Manifest.permission.GET_ACCOUNTS, Manifest.permission.READ_CALENDAR, Manifest.permission.WRITE_CALENDAR,Manifest.permission.CALL_PHONE,Manifest.permission.ACCESS_FINE_LOCATION,Manifest.permission.ACCESS_COARSE_LOCATION)
                .check()

        login_btn.setOnClickListener {
            if(id_tv.text.toString() == "test1234" && pw_tv.text.toString() == "test1234") {
            startActivity(Intent(this@LoginActivity, MainActivity::class.java))
            finish()
            }else{
                toast("미등록 회원입니다. 회원가입을 해주세요")
            }
        }
        signup_go.setOnClickListener {
            startActivity(Intent(this@LoginActivity, SignupActivity::class.java))
        }
    }

}