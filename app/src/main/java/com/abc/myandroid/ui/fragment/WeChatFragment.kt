package com.abc.myandroid.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.abc.myandroid.R
import com.abc.myandroid.adapter.WeChatPagerAdapter
import com.abc.myandroid.mvp.model.bean.Article
import com.abc.myandroid.mvp.model.bean.WXChapterBean
import com.abc.myandroid.mvp.presenter.WeChatPresenter
import com.google.android.material.tabs.TabLayout
import kotlinx.android.synthetic.main.fragment_wechat.*
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread


class WeChatFragment : Fragment() {

    companion object {
        fun getInstance(): WeChatFragment = WeChatFragment()
    }

    private val datas = mutableListOf<WXChapterBean>()
    private val presenter by lazy { WeChatPresenter(this) }
    private val viewPagerAdapter: WeChatPagerAdapter by lazy {
        WeChatPagerAdapter(datas, childFragmentManager, 1)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_wechat, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        presenter.requestWXChapters()
        viewPager.run {
            addOnPageChangeListener(TabLayout.TabLayoutOnPageChangeListener(tabLayout))
        }

        tabLayout.run {
            setupWithViewPager(viewPager)
            // TabLayoutHelper.setUpIndicatorWidth(tabLayout)
            addOnTabSelectedListener(TabLayout.ViewPagerOnTabSelectedListener(viewPager))
            addOnTabSelectedListener(onTabSelectedListener)
        }
    }

    private val onTabSelectedListener = object : TabLayout.OnTabSelectedListener {
        override fun onTabReselected(tab: TabLayout.Tab?) {
        }

        override fun onTabUnselected(tab: TabLayout.Tab?) {
        }

        override fun onTabSelected(tab: TabLayout.Tab?) {
            // 默认切换的时候，会有一个过渡动画，设为false后，取消动画，直接显示
            tab?.let {
                viewPager.setCurrentItem(it.position, false)
            }
        }
    }

    fun showList(chapters: MutableList<WXChapterBean>?) {
        chapters?.let {
            datas.addAll(it)
            doAsync {
                Thread.sleep(10)
                uiThread {
                    viewPager.run {
                        adapter = viewPagerAdapter
                        offscreenPageLimit = datas.size
                    }
                }
            }
        }
    }

}