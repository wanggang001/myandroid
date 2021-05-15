package com.abc.myandroid.ui.activity

import android.os.Bundle
import android.util.SparseArray
import android.view.KeyEvent
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.abc.myandroid.R
import com.abc.myandroid.ext.showToast
import com.abc.myandroid.ui.fragment.HomeFragment
import com.abc.myandroid.ui.fragment.SquareFragment
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {

    companion object {
        private const val INDEX_HOMEPAGE = 0
        private const val INDEX_PROJECT = 1
        private const val INDEX_SQUARE = 2
        private const val INDEX_WE_CHAT = 3
        private const val INDEX_ME = 4
    }

    private val mFragmentSparseArray = SparseArray<Fragment?>()
    private var mCurrentFragment: Fragment? = null
    private var mLastFragment: Fragment? = null
    private var mLastIndex = -1
    private var mExitTime: Long = 0


    var mBottomNavigationView: BottomNavigationView? = null

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        // recreate时保存当前页面位置
        outState.putInt("index", mLastIndex)

    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        // 恢复recreate前的页面
        mLastIndex = savedInstanceState.getInt("index")
        switchFragment(mLastIndex)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        mBottomNavigationView = findViewById(R.id.navigation_bottom)
        initBottomNavigation()
        // 判断当前是recreate还是新启动
        if (savedInstanceState == null) {
            switchFragment(INDEX_HOMEPAGE)
        }
    }

    private fun initBottomNavigation() {
        mBottomNavigationView?.setOnNavigationItemSelectedListener { menuItem: MenuItem ->
            when (menuItem.itemId) {
                R.id.menu_home -> {
                    switchFragment(INDEX_HOMEPAGE)
                    return@setOnNavigationItemSelectedListener true
                }
//                R.id.menu_project -> {
//                    switchFragment(INDEX_PROJECT)
//                    return@setOnNavigationItemSelectedListener true
//                }
                R.id.menu_square -> {
                    switchFragment(INDEX_SQUARE)
                    return@setOnNavigationItemSelectedListener true
                }
//                R.id.menu_wechat -> {
//                    switchFragment(INDEX_WE_CHAT)
//                    return@setOnNavigationItemSelectedListener true
//                }
//                R.id.menu_me -> {
//                    switchFragment(INDEX_ME)
//                    return@setOnNavigationItemSelectedListener true
//                }
                else -> return@setOnNavigationItemSelectedListener false
            }
        }
//        showBadgeView(4, 6)
    }

    private fun switchFragment(index: Int) {
        val fragmentManager = supportFragmentManager
        val transaction = fragmentManager.beginTransaction()
        // 将当前显示的fragment和上一个需要隐藏的fragment分别加上tag, 并获取出来
        // 给fragment添加tag,这样可以通过findFragmentByTag找到存在的fragment，不会出现重复添加
        mCurrentFragment = fragmentManager.findFragmentByTag("fragment$index")
        mLastFragment = fragmentManager.findFragmentByTag("fragment$mLastIndex")
        // 如果位置不同
        if (index != mLastIndex) {
            if (mLastFragment != null) {
                transaction.hide(mLastFragment!!)
            }
            if (mCurrentFragment == null) {
                mCurrentFragment = getFragment(index)
                transaction.add(R.id.container, mCurrentFragment!!, "fragment$index")
            } else {
                transaction.show(mCurrentFragment!!)
            }
        }

        // 如果位置相同或者新启动的应用
        if (index == mLastIndex) {
            if (mCurrentFragment == null) {
                mCurrentFragment = getFragment(index)
                transaction.add(R.id.container, mCurrentFragment!!, "fragment$index")
            }
        }
        transaction.commit()
        mLastIndex = index
    }

    private fun getFragment(index: Int): Fragment? {
        var fragment = mFragmentSparseArray[index]
        if (fragment == null) {
            when (index) {
                INDEX_HOMEPAGE -> fragment = HomeFragment.getInstance()
//                INDEX_PROJECT -> fragment = ProjectFragment.getInstance()
                INDEX_SQUARE -> fragment = SquareFragment.getInstance()
//                INDEX_WE_CHAT -> fragment = WeChatFragment.getInstance()
//                INDEX_ME -> fragment = MeFragment.getInstance()
                else -> {

                }
            }
            mFragmentSparseArray.put(index, fragment)
        }
        return fragment
    }


    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (System.currentTimeMillis().minus(mExitTime) <= 2000) {
                finish()
            } else {
                mExitTime = System.currentTimeMillis()
                showToast(getString(R.string.exit_tip))
            }
            return true
        }
        return super.onKeyDown(keyCode, event)
    }

    /**
     * 设置显示的未读数字
     *
     * @param index      当前bottom index
     * @param showNumber 显示的未读数值
     */
//    private fun showBadgeView(index: Int, showNumber: Int) {
//            val menuView = mBottomNavigationView!!.getChildAt(0) as BottomNavigationMenuView
//            if (index < menuView.childCount) {
//                val view = menuView.getChildAt(index)
//                val icon = view.findViewById<View>(com.google.android.material.R.id.icon)
//                val iconWidth = icon.width
//                val tabWidth = view.width / 2
//                val spaceWidth = tabWidth - iconWidth
//                val qBadgeView = QBadgeView(this)
//                qBadgeView.bindTarget(view)
//                    .setGravityOffset((spaceWidth + 50).toFloat(), 13f, false).badgeNumber = showNumber
//                qBadgeView.setOnDragStateChangedListener { dragState, badge, targetView -> qBadgeView.clearAnimation() }
//            }
//
//    }

    override fun onDestroy() {
        super.onDestroy()
    }
}