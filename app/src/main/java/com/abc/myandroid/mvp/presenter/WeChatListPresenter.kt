package com.abc.myandroid.mvp.presenter

import com.abc.myandroid.http.RetrofitHelper
import com.abc.myandroid.mvp.model.bean.ArticleResponseBody
import com.abc.myandroid.mvp.model.bean.HttpResult
import com.abc.myandroid.mvp.model.bean.WXChapterBean
import com.abc.myandroid.ui.fragment.WeChatListFragment
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class WeChatListPresenter(val wechatlistFragment: WeChatListFragment) {

    fun requestWXChapters(pageNum: Int,cid: Int) {

        RetrofitHelper.service.getWXChaptersList(pageNum,cid)
            .enqueue(object : Callback<HttpResult<ArticleResponseBody>> {
                override fun onResponse(
                    call: Call<HttpResult<ArticleResponseBody>>,
                    response: Response<HttpResult<ArticleResponseBody>>
                ) {
                    val list = response.body()?.data?.datas
                    wechatlistFragment.showWxList(list)
                }

                override fun onFailure(call: Call<HttpResult<ArticleResponseBody>>, t: Throwable) {
                    t.printStackTrace()
                }
            })
    }

}


