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
import com.abc.myandroid.mvp.presenter.HomePresenter
import kotlinx.android.synthetic.main.fragment_home.*

class HomeFragment : Fragment() {

    companion object {
        fun getInstance(): HomeFragment = HomeFragment()
    }

    private val datas = mutableListOf<Article>()
    private val homeAdapter by lazy { HomeAdapter(datas) }
    private val presenter by lazy { HomePresenter(this) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
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

    }

    private fun initAdapter() {
       val layoutManager = LinearLayoutManager(context)
        article_recycler?.layoutManager = layoutManager
        article_recycler?.adapter = homeAdapter
        presenter.loadArticle(0)
    }

    fun showList(articleList: List<Article>?) {
        datas.clear()
        articleList?.let { datas.addAll(it) }
        homeAdapter.notifyDataSetChanged()
    }
}