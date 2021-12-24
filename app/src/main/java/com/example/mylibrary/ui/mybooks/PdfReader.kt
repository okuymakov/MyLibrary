package com.example.mylibrary.ui.mybooks

import android.content.ContentResolver
import android.graphics.Bitmap
import android.graphics.pdf.PdfRenderer
import android.net.Uri
import android.widget.ImageView

class PdfReader( uri: Uri, contentResolver: ContentResolver) {
    private var currentPage: PdfRenderer.Page? = null

    private val fileDescriptor = contentResolver.openFileDescriptor(uri, "r")!!
    private val pdfRenderer = PdfRenderer(fileDescriptor)
    private val pageCount = pdfRenderer.pageCount

    fun openPage(page: Int, pdfImage: ImageView) {
        if (page >= pageCount) return
        currentPage?.close()
        currentPage = pdfRenderer.openPage(page).apply {
            val bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888)
            render(bitmap, null, null, PdfRenderer.Page.RENDER_MODE_FOR_DISPLAY)
            pdfImage.setImageBitmap(bitmap)
        }
    }

    fun close() {
        currentPage?.close()
        fileDescriptor.close()
        pdfRenderer.close()
    }
}