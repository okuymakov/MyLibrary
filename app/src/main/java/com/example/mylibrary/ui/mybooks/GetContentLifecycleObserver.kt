package com.example.mylibrary.ui.mybooks

import android.net.Uri
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.ActivityResultRegistry
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityOptionsCompat
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner
import java.io.File

class GetContentLifecycleObserver(
    private val registry: ActivityResultRegistry,
    private val onGet: (Uri) -> Unit
) : DefaultLifecycleObserver {

    lateinit var getContent: ActivityResultLauncher<Array<String>>
    var pdfFile: File? = null

    override fun onCreate(owner: LifecycleOwner) {

        getContent =
            registry.register("key", owner, PickBook()) { uri ->
                uri?.let { onGet(it) }
            }
    }

    fun selectFile() {
        getContent.launch(arrayOf("application/pdf"))
    }
}