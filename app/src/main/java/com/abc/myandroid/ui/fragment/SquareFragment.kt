package com.abc.myandroid.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.abc.myandroid.R
import com.abc.myandroid.adapter.HomeAdapter
import com.abc.myandroid.mvp.model.bean.Article
import com.abc.myandroid.mvp.presenter.SquarePresenter
import com.abc.myandroid.ui.activity.WebViewAcitvity
import com.abc.myandroid.widget.SpaceItemDecoration
import com.scwang.smart.refresh.footer.ClassicsFooter
import com.scwang.smart.refresh.header.ClassicsHeader
import com.scwang.smart.refresh.layout.api.RefreshLayout
import kotlinx.android.synthetic.main.fragment_square.*


class SquareFragment : Fragment() {

    companion object {
        fun getInstance(): SquareFragment = SquareFragment()
    }

    private val datas = mutableListOf<Article>()
    private val presenter by lazy { SquarePresenter(this) }
    private val homeAdapter by lazy { HomeAdapter(datas) }
    private var page = 0

    private val refreshLayout by lazy { requireView().findViewById(R.id.refreshLayout) as RefreshLayout }

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
        return inflater.inflate(R.layout.fragment_square, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initAdapter()
        presenter.requestSquare(page)

        refreshLayout.setRefreshHeader(ClassicsHeader(activity))
        refreshLayout.setRefreshFooter(ClassicsFooter(activity))

        //下拉刷新
        refreshLayout.setOnRefreshListener {
            page = 0
            datas.clear()
            presenter.requestSquare(page)
        }
        //加载更多
        refreshLayout.setOnLoadMoreListener {
            page++
            presenter.requestSquare(page)
        }
    }

    private fun initAdapter() {
        val layoutManager = LinearLayoutManager(context)
        recyclerView?.layoutManager = layoutManager
        recyclerView?.adapter = homeAdapter

        recyclerView.apply { recyclerViewItemDecoration?.let { addItemDecoration(it) } }
        homeAdapter.setOnItemClickListener { adapter, view, position ->
            if (datas.size != 0) {
                val data = datas[position]
                WebViewAcitvity.start(activity, data.title, data.link)
            }
        }
//        homeAdapter.loadMoreModule.setOnLoadMoreListener {
//            page++
//            presenter.requestSquare(page)
//        }
    }

    fun showList(list: MutableList<Article>?) {
        list?.let { datas.addAll(it) }
        homeAdapter.notifyDataSetChanged()
//        homeAdapter.loadMoreModule.loadMoreComplete()
        refreshLayout.finishRefresh()  //结束刷新
        refreshLayout.finishLoadMore() //结束加载
    }


}