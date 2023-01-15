package com.kuymakov.mylibrary.ui.mybooks

interface ClickHandler<T> {
    var onClick : ((T) -> Unit)?
    var onLongClick: ((T) -> Unit)?
}