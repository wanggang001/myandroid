package com.abc.myandroid.api


import com.abc.myandroid.mvp.model.bean.*
import io.reactivex.Observable
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query


interface ApiService {

    /**
     * 获取轮播图
     * https://www.wanandroid.com/banner/json
     */
    @GET("banner/json")
    fun getBanners(): Call<HttpResult<List<Banner>>>

    /**
     * 获取首页置顶文章列表
     * https://www.wanandroid.com/article/top/json
     */
    @GET("article/top/json")
    fun getTopArticles(): Call<HttpResult<MutableList<Article>>>

    /**
     * 获取文章列表
     * https://www.wanandroid.com/article/list/0/json
     * @param pageNum
     */
    @GET("article/list/{pageNum}/json")
    fun getArticles(@Path("pageNum") pageNum: Int): Call<HttpResult<ArticleResponseBody>>

    /**
     * 广场列表数据
     * https://wanandroid.com/user_article/list/0/json
     * @param page 页码拼接在url上从0开始
     */
    @GET("user_article/list/{page}/json")
    fun getSquareList(@Path("page") page: Int): Call<HttpResult<ArticleResponseBody>>

    /**
     * 获取公众号列表
     * https://wanandroid.com/wxarticle/chapters/json
     */
    @GET("wxarticle/chapters/json")
    fun getWXChapters(): Call<HttpResult<MutableList<WXChapterBean>>>

    /**
     * 公众号下文列表
     * https://www.wanandroid.com/article/list/0/json?cid=408
     */
    @GET("article/list/{page}/json")
    fun getWXChaptersList(@Path("page") page: Int, @Query("cid") cid: Int): Call<HttpResult<ArticleResponseBody>>

}