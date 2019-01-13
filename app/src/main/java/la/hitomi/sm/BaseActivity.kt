package la.hitomi.sm

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.ValueAnimator
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.view.MenuItem

abstract class BaseActivity : AppCompatActivity() {

    var instance: BaseActivity? = null
    var mToolbarHeight = 0
    var mAnimDuration = 0

    private var mVaActionBar: ValueAnimator? = null

    protected abstract var viewId: Int
    protected abstract var toolbarId: Int?


    protected abstract fun onCreate()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        instance = this

        setContentView(viewId)

        if (toolbarId != null)
            findViewById<Toolbar>(toolbarId!!).let {
                setSupportActionBar(it)
            }
        onCreate()
    }

    fun hideActionBar() {
        toolbarId?.let {
            val mToolbar = findViewById<Toolbar>(toolbarId!!)

            if (mToolbarHeight == 0)
                mToolbarHeight = mToolbar.height

            if (mVaActionBar != null && mVaActionBar!!.isRunning)
                return@let

            mVaActionBar = ValueAnimator.ofInt(mToolbarHeight, 0)
            mVaActionBar?.addUpdateListener {
                ValueAnimator.AnimatorUpdateListener { animation ->
                    mToolbar.layoutParams.height = animation.animatedValue as Int
                    mToolbar.requestLayout()
                }
            }

            mVaActionBar?.addUpdateListener {
                object : AnimatorListenerAdapter() {
                    override fun onAnimationEnd(animation: Animator?) {
                        supportActionBar?.hide()
                    }
                }
            }

            mVaActionBar!!.duration = mAnimDuration.toLong()
            mVaActionBar?.start()

        }
    }

    fun showActionBar() {
        toolbarId?.let {
            val mToolbar = findViewById<Toolbar>(toolbarId!!)

            if (mVaActionBar != null && mVaActionBar!!.isRunning)
                return@let

            mVaActionBar = ValueAnimator.ofInt(0, mToolbarHeight)
            mVaActionBar?.addUpdateListener {
                ValueAnimator.AnimatorUpdateListener { animation ->
                    mToolbar.layoutParams.height = animation.animatedValue as Int
                    mToolbar.requestLayout()
                }
            }

            mVaActionBar?.addUpdateListener {
                object : AnimatorListenerAdapter() {
                    override fun onAnimationEnd(animation: Animator?) {
                        supportActionBar?.hide()
                    }
                }
            }

            mVaActionBar!!.duration = mAnimDuration.toLong()
            mVaActionBar?.start()

        }
    }

    fun disableToggle() {
        this.supportActionBar?.setDisplayHomeAsUpEnabled(false)
    }
    //	<- 모양의 뒤로가기 버튼 없애기

    fun enableToggle() {
        this.supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }
    //	<- 모양의 뒤로가기 버튼 활성

    fun setToolbarTitle(titleStr: String) {
        this.supportActionBar?.title = titleStr
    }
    // 타이틀 변경

    fun setToolbarTitle(titleId: Int) {
        this.supportActionBar?.title = getString(titleId)
    }
    // 타이틀 변경

    fun setToolbarIcon(iconRes: Int) {
        this.supportActionBar?.setHomeAsUpIndicator(iconRes)
    }
    // 툴바 아이콘

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> finish()
        }
        return super.onOptionsItemSelected(item)
    }
    // <- 버튼 누를 시 뒤로가기
}