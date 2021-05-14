package com.abc.myandroid.mvp.presenter

import com.abc.myandroid.http.RetrofitHelper
import com.abc.myandroid.mvp.model.bean.ArticleResponseBody
import com.abc.myandroid.mvp.model.bean.Banner
import com.abc.myandroid.mvp.model.bean.HttpResult
import com.abc.myandroid.ui.fragment.HomeFragment
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class HomePresenter(val homeFragment: HomeFragment) {


    fun requestArticle(pageNum: Int) {
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

     fun requestBanner() {
        RetrofitHelper.service.getBanners().enqueue(object : Callback<HttpResult<List<Banner>>> {
            override fun onResponse(
                call: Call<HttpResult<List<Banner>>>,
                response: Response<HttpResult<List<Banner>>>
            ) {
                val bannerDate = response.body()?.data
//                homeFragment.showBanner(bannerDate)
            }

            override fun onFailure(call: Call<HttpResult<List<Banner>>>, t: Throwable) {
                t.printStackTrace()
            }
        })
    }


}


