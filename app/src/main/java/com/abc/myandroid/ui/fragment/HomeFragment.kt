package com.abc.myandroid.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import cn.bingoogolapple.bgabanner.BGABanner
import com.abc.myandroid.R
import com.abc.myandroid.adapter.HomeAdapter
import com.abc.myandroid.mvp.model.bean.Article
import com.abc.myandroid.mvp.model.bean.Banner
import com.abc.myandroid.mvp.presenter.HomePresenter
import com.abc.myandroid.ui.activity.WebViewAcitvity
import com.abc.myandroid.utils.ImageLoader
import com.abc.myandroid.widget.SpaceItemDecoration
import com.scwang.smart.refresh.footer.ClassicsFooter
import com.scwang.smart.refresh.header.ClassicsHeader
import com.scwang.smart.refresh.layout.api.RefreshLayout
import kotlinx.android.synthetic.main.fragment_home.*


class HomeFragment : Fragment() {

    companion object {
        fun getInstance(): HomeFragment = HomeFragment()
    }

    private val datas = mutableListOf<Article>()
    private val homeAdapter by lazy { HomeAdapter(datas) }
    private val presenter by lazy { HomePresenter(this) }

    private var page = 0
    private var bannerDates = ArrayList<Banner>()
    private var mContentBanner: BGABanner? = null
    private val bannerView by lazy {
        layoutInflater.inflate(R.layout.item_home_banner, null).apply {
            mContentBanner = findViewById(R.id.banner)
            mContentBanner?.setDelegate(bannerDelegate)
        }
    }
    private val refreshLayout by lazy { requireView().findViewById(R.id.refreshLayout) as RefreshLayout }


    private val recyclerViewItemDecoration by lazy {
        activity?.let {
            SpaceItemDecoration(it)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_home, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initAdapter()
        presenter.requestBanner()
        presenter.requestTopArticle()
        presenter.requestArticle(page)
        homeAdapter.setOnItemClickListener { adapter, view, position ->
            if (datas.size != 0) {
                val data = datas[position]
                WebViewAcitvity.start(activity, data.title, data.link)
            }
        }

        homeAdapter.run {
            addHeaderView(bannerView)
        }
        refreshLayout.setRefreshHeader(ClassicsHeader(activity))
        refreshLayout.setRefreshFooter(ClassicsFooter(activity))
        //下拉刷新
        refreshLayout.setOnRefreshListener {
            page = 0
            datas.clear()
            presenter.requestBanner()
            presenter.requestTopArticle()
            presenter.requestArticle(page)
        }
        //加载更多
        refreshLayout.setOnLoadMoreListener {
            page++
            presenter.requestArticle(page)
        }
    }

    /**
     * LinearLayoutManager
     */
    private val linearLayoutManager: LinearLayoutManager by lazy {
        LinearLayoutManager(activity)
    }

    private fun initAdapter() {
        recyclerView.run {
            layoutManager = linearLayoutManager
            adapter = homeAdapter
            recyclerViewItemDecoration?.let { addItemDecoration(it) }
        }
//        homeAdapter.loadMoreModule.setOnLoadMoreListener {
//            page++
//            presenter.requestArticle(page)
//        }
    }


    fun showList(articleList: List<Article>?) {
        articleList?.let { datas.addAll(it) }
        homeAdapter.notifyDataSetChanged()
//        homeAdapter.loadMoreModule.loadMoreComplete()
        refreshLayout.finishRefresh()  //结束刷新
        refreshLayout.finishLoadMore() //结束加载
    }

    fun showBanner(bannerList: List<Banner>?) {
        bannerDates = bannerList as ArrayList<Banner>
        if (bannerList.isNotEmpty()) {
            mContentBanner?.visibility = View.VISIBLE
        }
        mContentBanner?.setAutoPlayAble(bannerList.size > 1)
        mContentBanner?.setAdapter(bannerAdapter)
        mContentBanner?.setData(bannerList.map { it.imagePath }, bannerList.map { it.title })

    }

    private val bannerAdapter: BGABanner.Adapter<ImageView, String> by lazy {
        BGABanner.Adapter<ImageView, String> { bgaBanner, imageView, feedImageUrl, position ->
            ImageLoader.load(activity, feedImageUrl, imageView)
        }
    }

    private val bannerDelegate =
        BGABanner.Delegate<ImageView, String> { banner, imageView, model, position ->
            if (bannerDates.size > 0) {
                val data = bannerDates[position]
                WebViewAcitvity.start(activity, data.title, data.url)
            }
        }

    fun showTopList(list: List<Article>?) {
        list?.let {
            datas.addAll(0, it)
            homeAdapter.notifyDataSetChanged()
        }
    }
}

