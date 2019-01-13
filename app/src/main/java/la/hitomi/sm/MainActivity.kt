package la.hitomi.sm

import android.annotation.SuppressLint
import android.support.design.widget.TabLayout
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentStatePagerAdapter
import android.support.v4.view.ViewPager
import android.view.View
import android.widget.Toast

class MainActivity : BaseActivity() {

    override var viewId: Int = R.layout.activity_main
    override var toolbarId: Int? = R.id.toolbar
    private lateinit var toast: Toast
    private var backKeyPressedTime: Long = 200
    private var mViewPager: ViewPager? = null

    @SuppressLint("ShowToast")
    override fun onCreate() {
        showActionBar()

        toast = Toast.makeText(this, getString(R.string.press_back_button_one_more), Toast.LENGTH_SHORT)

        mViewPager = findViewById(R.id.viewPager)
        mViewPager!!.adapter = PagerAdapter(supportFragmentManager)
        mViewPager!!.currentItem = 1

        val tabLayout = findViewById<View>(R.id.tabs) as TabLayout
        tabLayout.setupWithViewPager(mViewPager)

        // set icons
        tabLayout.getTabAt(0)!!.setIcon(R.drawable.calendar)
        tabLayout.getTabAt(1)!!.setIcon(R.drawable.supervisor_)
        tabLayout.getTabAt(2)!!.setIcon(R.drawable.question_answer)
        tabLayout.getTabAt(3)!!.setIcon(R.drawable.money)
        mViewPager!!.addOnPageChangeListener(TabLayout.TabLayoutOnPageChangeListener(tabLayout))
        tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab) = tab.select()

            override fun onTabUnselected(tab: TabLayout.Tab) { }

            override fun onTabReselected(tab: TabLayout.Tab) { }
        })

        /* viewPager?.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
             override fun onPageScrollStateChanged(state: Int) {
             }
             override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {
             }
             override fun onPageSelected(position: Int) {
             }
         })*/
    }

    override fun onBackPressed() {
        val delay = 500
        when {
            System.currentTimeMillis() > backKeyPressedTime + delay -> {
                backKeyPressedTime = System.currentTimeMillis()
                toast.show()
                return
            }
            System.currentTimeMillis() <= backKeyPressedTime + delay -> {
                toast.cancel()
                this.finish()
            }
        }

    }

    inner class PagerAdapter(supportFragmentManager: FragmentManager) : FragmentStatePagerAdapter(supportFragmentManager) {

        override fun getItem(position: Int): Fragment? {

            return when (position) {
                0 ->
                    CalendarFragment()
                1 ->
                    PeopleFragment()
                2 ->
                    ChatFragment()
                3 ->
                    SettingFragment()
                else ->
                    null
            }
        }

        override fun getCount(): Int = 4
    }
}
