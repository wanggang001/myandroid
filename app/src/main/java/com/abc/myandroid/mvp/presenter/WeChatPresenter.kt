package com.abc.myandroid.mvp.presenter

import com.abc.myandroid.http.RetrofitHelper
import com.abc.myandroid.mvp.model.bean.HttpResult
import com.abc.myandroid.mvp.model.bean.WXChapterBean
import com.abc.myandroid.ui.fragment.WeChatFragment
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class WeChatPresenter(val wechatFragment: WeChatFragment) {

    fun requestWXChapters() {

        RetrofitHelper.service.getWXChapters()
            .enqueue(object : Callback<HttpResult<MutableList<WXChapterBean>>> {
                override fun onResponse(
                    call: Call<HttpResult<MutableList<WXChapterBean>>>,
                    response: Response<HttpResult<MutableList<WXChapterBean>>>
                ) {
                    val list = response.body()?.data
                    wechatFragment.showList(list)
                }

                override fun onFailure(
                    call: Call<HttpResult<MutableList<WXChapterBean>>>,
                    t: Throwable
                ) {
                    t.printStackTrace()
                }
            })
    }

}


