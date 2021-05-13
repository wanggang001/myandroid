package com.abc.myandroid.mvp.presenter

import com.abc.myandroid.http.RetrofitHelper
import com.abc.myandroid.mvp.model.bean.ArticleResponseBody
import com.abc.myandroid.mvp.model.bean.HttpResult
import com.abc.myandroid.ui.fragment.HomeFragment
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class HomePresenter(val homeFragment: HomeFragment) {


    fun loadArticle(pageNum: Int) {
        RetrofitHelper.service.getArticles(pageNum).enqueue(object : Callback<HttpResult<ArticleResponseBody>> {
            override fun onResponse(
                call: Call<HttpResult<ArticleResponseBody>>,
                response: Response<HttpResult<ArticleResponseBody>>
            ) {
                val list = response.body()?.data?.datas
                homeFragment.showList(list)
            }

            override fun onFailure(call: Call<HttpResult<ArticleResponseBody>>, t: Throwable) {
               t.printStackTrace()
            }
        })

    }


}