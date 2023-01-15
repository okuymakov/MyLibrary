package com.kuymakov.mylibrary.ui.mybooks.lifecycleobserver

import android.net.Uri
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.ActivityResultRegistry
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner
import com.kuymakov.mylibrary.ui.mybooks.contract.PickBook

class GetContentLifecycleObserver(
    private val registry: ActivityResultRegistry,
    private val onPick: (Uri) -> Unit
) : DefaultLifecycleObserver {

    private lateinit var getContent: ActivityResultLauncher<Array<String>>

    override fun onCreate(owner: LifecycleOwner) {
        getContent =
            registry.register("BookPicker", owner, PickBook()) { uri ->
                uri?.let { onPick(it) }
            }
    }

    fun pickFile() {
        getContent.launch(arrayOf("application/pdf"))
    }
}