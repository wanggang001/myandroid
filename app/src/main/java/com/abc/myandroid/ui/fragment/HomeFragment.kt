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
    private val mContentBanner by lazy { requireView().findViewById<BGABanner>(R.id.banner) }

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
        mContentBanner.setDelegate(bannerDelegate)
    }

    /**
     * LinearLayoutManager
     */
    private val linearLayoutManager: LinearLayoutManager by lazy {
        LinearLayoutManager(activity)
    }

    private fun initAdapter() {
        article_recycler.run {
            layoutManager = linearLayoutManager
            adapter = homeAdapter
        }
        homeAdapter.loadMoreModule.setOnLoadMoreListener {
            page++
            presenter.requestArticle(page)
        }
    }


    fun showList(articleList: List<Article>?) {
        articleList?.let { datas.addAll(it) }
//        homeAdapter.notifyDataSetChanged()
        homeAdapter.loadMoreModule.loadMoreComplete()
    }

    fun showBanner(bannerList: List<Banner>?) {
        bannerDates = bannerList as ArrayList<Banner>
        mContentBanner.setAutoPlayAble(bannerList.size > 1)
        mContentBanner.setAdapter(bannerAdapter)
        mContentBanner.setData(bannerList.map { it.imagePath }, bannerList.map { it.title })

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
        list.let { datas.addAll(it!!) }
    }
}

