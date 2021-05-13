package com.abc.myandroid.api


import com.abc.myandroid.mvp.model.bean.ArticleResponseBody
import com.abc.myandroid.mvp.model.bean.HttpResult
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path


interface ApiService {

    /**
     * 获取文章列表
     * http://www.wanandroid.com/article/list/0/json
     * @param pageNum
     */
    @GET("article/list/{pageNum}/json")
    fun getArticles(@Path("pageNum") pageNum: Int): Call<HttpResult<ArticleResponseBody>>


}