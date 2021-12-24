package com.example.mylibrary.base.extensions

import android.view.View
import android.view.ViewGroup
import androidx.core.view.children

fun ViewGroup.enableAll(enable: Boolean) {
    children.forEach { view: View ->
        if (view is ViewGroup) {
            view.enableAll(enable)
        }
        view.isEnabled = enable
    }
}