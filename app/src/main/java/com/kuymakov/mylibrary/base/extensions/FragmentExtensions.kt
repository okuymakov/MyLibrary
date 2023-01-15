package com.kuymakov.mylibrary.base.extensions

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment


val Fragment.actionBar get() = (requireActivity() as AppCompatActivity).supportActionBar

fun Fragment.showBackButton(show: Boolean) {
    (requireActivity() as AppCompatActivity).supportActionBar?.apply {
        setDisplayHomeAsUpEnabled(show)
        setDisplayShowHomeEnabled(show)
    }
}

fun Fragment.hideKeyboard() {
    view?.let { activity?.hideKeyboard() }
}