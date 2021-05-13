package com.abc.myandroid.ext

import android.content.Context
import androidx.fragment.app.Fragment
import com.abc.myandroid.widget.CustomToast

fun Fragment.showToast(content: String) {
    CustomToast(this?.activity?.applicationContext, content).show()
}

fun Context.showToast(content: String) {
    CustomToast(this, content).show()
}