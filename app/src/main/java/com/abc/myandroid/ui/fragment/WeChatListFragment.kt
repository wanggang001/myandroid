package com.abc.myandroid.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.abc.myandroid.R
import com.abc.myandroid.adapter.WeChatPagerListAdapter
import com.abc.myandroid.constant.Constant
import com.abc.myandroid.mvp.model.bean.Article
import com.abc.myandroid.mvp.presenter.WeChatListPresenter
import com.abc.myandroid.ui.activity.WebViewAcitvity
import com.abc.myandroid.widget.SpaceItemDecoration
import kotlinx.android.synthetic.main.fragment_home.*


class WeChatListFragment : Fragment() {

    companion object {
        fun getInstance(cid: Int): WeChatListFragment {
            val fragment = WeChatListFragment()
            val args = Bundle()
            args.putInt(Constant.CONTENT_CID_KEY, cid)
            fragment.arguments = args
            return fragment
        }
    }

    private val datas = mutableListOf<Article>()
    private val wechatlistAdapter by lazy { WeChatPagerListAdapter(datas) }
    private val presenter by lazy { WeChatListPresenter(this) }

    private var page = 0
    private var cid = 408

    /**
     * RecyclerView Divider
     */
    private val recyclerViewItemDecoration by lazy {
        activity?.let {
            SpaceItemDecoration(it)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_wechatlist, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initAdapter()
        cid = arguments?.getInt(Constant.CONTENT_CID_KEY) ?: 0
        presenter.requestWXChapters(page, cid)
        wechatlistAdapter.setOnItemClickListener { adapter, view, position ->
            if (datas.size != 0) {
                val data = datas[position]
                WebViewAcitvity.start(activity, data.title, data.link)
            }
        }
    }

    private val linearLayoutManager: LinearLayoutManager by lazy {
        LinearLayoutManager(activity)
    }

    private fun initAdapter() {
        article_recycler.run {
            layoutManager = linearLayoutManager
            adapter = wechatlistAdapter
            recyclerViewItemDecoration?.let { addItemDecoration(it) }
        }
        wechatlistAdapter.loadMoreModule.setOnLoadMoreListener {
            page++
            presenter.requestWXChapters(page, cid)
        }
    }

    fun showWxList(list: MutableList<Article>?) {
        list?.let { datas.addAll(it) }
        wechatlistAdapter.loadMoreModule.loadMoreComplete()
    }

}

