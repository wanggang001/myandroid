package com.abc.myandroid.ui.activity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.PersistableBundle
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.appcompat.app.AppCompatActivity
import com.abc.myandroid.R
import com.abc.myandroid.constant.Constant


class WebViewAcitvity : AppCompatActivity() {
    private var webView: WebView? = null
    private val title = ""
    private val url = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_webview)

    }

    override fun onCreate(savedInstanceState: Bundle?, persistentState: PersistableBundle?) {
        super.onCreate(savedInstanceState, persistentState)
        initView()
    }

    private fun initView() {
        webView = WebView(this)
        webView?.apply {
            settings.domStorageEnabled = true
            settings.javaScriptEnabled = true
        }
        webView!!.webViewClient = object : WebViewClient() {
            //设置在webView点击打开的新网页在当前界面显示,而不跳转到新的浏览器中
            override fun shouldOverrideUrlLoading(view: WebView, url: String): Boolean {
                view.loadUrl(url)
                return true
            }
        }

        webView!!.loadUrl(url) //调用loadUrl方法为WebView加入链接
        setContentView(webView) //调用Activity提供的setContentView将webView显示出来
    }

}