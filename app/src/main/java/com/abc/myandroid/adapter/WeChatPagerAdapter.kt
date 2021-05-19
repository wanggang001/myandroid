package com.abc.myandroid.adapter

import android.text.Html
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.abc.myandroid.mvp.model.bean.WXChapterBean
import com.abc.myandroid.ui.fragment.WeChatFragment
import com.abc.myandroid.ui.fragment.WeChatListFragment


class WeChatPagerAdapter(
    private val list: MutableList<WXChapterBean>,
    fm: FragmentManager,
    behavior: Int
) : FragmentPagerAdapter(fm, behavior) {

    private val fragments = mutableListOf<Fragment>()
    init {
        fragments.clear()
        list.forEach {
            fragments.add(WeChatListFragment.getInstance(it.id))
        }
    }
    override fun getCount(): Int {
        return list.size
    }

    override fun getItem(position: Int): Fragment {
        return fragments[position]
    }


    override fun getPageTitle(position: Int): CharSequence? = Html.fromHtml(list[position].name)


}