package com.kuymakov.mylibrary.base.extensions

import android.content.ContentResolver
import android.net.Uri
import android.provider.OpenableColumns

fun Uri.toFileName(contentResolver: ContentResolver): String {
    var result: String? = null
    if (scheme == "content") {
        val cursor = contentResolver.query(this, null, null, null, null)
        cursor.use { c ->
            if (c != null && c.moveToFirst()) {
                result =
                    c.getString(c.getColumnIndexOrThrow(OpenableColumns.DISPLAY_NAME))
                        .substringBeforeLast('.')
            }
        }
    }
    return result ?: ""
}