package com.abc.myandroid.utils

import android.content.Context
import android.widget.ImageView
import com.abc.myandroid.R
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.request.RequestOptions


object ImageLoader {

    /**
     * 加载图片
     * @param context
     * @param url
     * @param iv
     */
    fun load(context: Context?, url: String?, iv: ImageView?) {

            iv?.apply {
                Glide.with(context!!).clear(iv)
                val options = RequestOptions()
                        .diskCacheStrategy(DiskCacheStrategy.DATA)
                        .placeholder(R.drawable.bg_placeholder)
                Glide.with(context)
                        .load(url)
                        .transition(DrawableTransitionOptions().crossFade())
                        .apply(options)
                        .into(iv)
            }


    }

}