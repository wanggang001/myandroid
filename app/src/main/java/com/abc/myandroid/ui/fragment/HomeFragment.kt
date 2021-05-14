package com.abc.myandroid.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import cn.bingoogolapple.bgabanner.BGABanner
import com.abc.myandroid.R
import com.abc.myandroid.adapter.HomeAdapter
import com.abc.myandroid.mvp.model.bean.Article
import com.abc.myandroid.mvp.model.bean.Banner
import com.abc.myandroid.mvp.presenter.HomePresenter
import com.abc.myandroid.ui.activity.WebViewAcitvity
import io.reactivex.Observable
import kotlinx.android.synthetic.main.fragment_home.*


class HomeFragment: Fragment() {

    companion object {
        fun getInstance(): HomeFragment = HomeFragment()
    }

    private val datas = mutableListOf<Article>()
    private var bannerDates = ArrayList<Banner>()
    private val homeAdapter by lazy { HomeAdapter(datas) }
    private val presenter by lazy { HomePresenter(this) }

    private var page = 0
    private var mContentBanner: BGABanner? = null
    private var bannerView: View? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        bannerView = view?.findViewById(R.id.banner)

    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_home, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initBanner()
        initAdapter()
        presenter.requestBanner()
        presenter.requestTopArticle()
        presenter.requestArticle(page)
        homeAdapter.setOnItemClickListener { adapter, view, position ->
            if (datas.size != 0) {
                val data = datas[position]
                WebViewAcitvity.start(activity,data.title,data.link)
            }
        }
    }
    private fun initBanner() {


    }

    private fun initAdapter() {
       val layoutManager = LinearLayoutManager(context)
        article_recycler?.layoutManager = layoutManager
        article_recycler?.adapter = homeAdapter

        homeAdapter.loadMoreModule.setOnLoadMoreListener {
            page++
            presenter.requestArticle(page)
        }
    }


    fun showList(articleList: List<Article>?) {
        articleList?.let { datas.addAll(it) }
        homeAdapter.notifyDataSetChanged()
        homeAdapter.loadMoreModule.loadMoreComplete()
    }

    fun showBanner(bannerList: List<Banner>?) {
        bannerDates = bannerList as ArrayList<Banner>

//        val bannerFeedList = ArrayList<String>()
//        val bannerTitleList = ArrayList<String>()
//        Observable.fromIterable(bannerList)
//            .subscribe { list ->
//                bannerFeedList.add(list.imagePath)
//                bannerTitleList.add(list.title)
//            }
//        mContentBanner?.setData(bannerFeedList,bannerTitleList)
    }

    fun showTopList(list: List<Article>?) {
        list.let { datas.addAll(it!!) }
    }
}

