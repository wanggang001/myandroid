package com.abc.myandroid.ui.activity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.appcompat.app.AppCompatActivity
import com.abc.myandroid.R
import com.abc.myandroid.constant.Constant


class WebViewAcitvity : AppCompatActivity() {

    private var webView: WebView? = null

    private val title by lazy { intent.getStringExtra(Constant.TITLE_KEY) }
    private val url by lazy { intent.getStringExtra(Constant.URL_KEY) }

    companion object {

        fun start(context: Context?, title: String, url: String, bundle: Bundle? = null) {
            Intent(context, WebViewAcitvity::class.java).run {
                putExtra(Constant.TITLE_KEY, title)
                putExtra(Constant.URL_KEY, url)
                context?.startActivity(this, bundle)
            }
        }

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_webview)
        setTitle(title)
        initView()
    }

    private fun initView() {
        webView = WebView(this)
        webView?.apply {
            settings.domStorageEnabled = true
            settings.javaScriptEnabled = true
        }
        url?.let { webView?.loadUrl(it) }
        webView!!.webViewClient = object : WebViewClient() {

            override fun shouldOverrideUrlLoading(view: WebView, url: String): Boolean {
                view.loadUrl(url)
                return true
            }
        }
        setContentView(webView)
    }

}