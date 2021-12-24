package com.example.mylibrary.ui.mybooks

import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import com.example.mylibrary.base.BaseFragment
import com.example.mylibrary.databinding.FragmentMyBooksBinding
import android.provider.OpenableColumns
import android.view.*
import androidx.appcompat.view.ActionMode
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import com.example.mylibrary.R
import com.example.mylibrary.base.extensions.enableAll
import com.example.mylibrary.models.MyBook
import com.example.mylibrary.ui.MainActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import timber.log.Timber

@AndroidEntryPoint
class MyBooksFragment : BaseFragment<FragmentMyBooksBinding>() {
    override val inflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentMyBooksBinding
        get() = FragmentMyBooksBinding::inflate
    val viewModel: MyBooksViewModel by viewModels()
    private var observer: GetContentLifecycleObserver? = null
    private var actionMode: ActionMode? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        observer = GetContentLifecycleObserver(requireActivity().activityResultRegistry, ::addBook)
        lifecycle.addObserver(observer!!)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.fab.setOnClickListener {
            observer?.selectFile()
        }
        val adapter = MyBooksAdapter(::getPdfReader, ::onLongClick)
        binding.myBooksList.adapter = adapter
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.books.collect { books ->
                    adapter.updateData(books)
                }
            }
        }

        findNavController().addOnDestinationChangedListener {_,_,_ ->
            actionMode?.finish()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
    }

    private fun addBook(
        uri: Uri
    ) {
        val name = getFileName(uri) ?: ""
        val book = MyBook(name, uri.toString())
        viewModel.addBooks(book)
    }

    private fun deleteBook(book: MyBook) {
        viewModel.deleteBooks(book)
    }

    private fun onLongClick(book: MyBook) {

        val callback = object : ActionMode.Callback {

            override fun onCreateActionMode(mode: ActionMode?, menu: Menu?): Boolean {
                requireActivity().menuInflater.inflate(R.menu.contextual_menu, menu)
                return true
            }

            override fun onPrepareActionMode(mode: ActionMode?, menu: Menu?): Boolean {
                binding.myBooksList.enableAll(false)
                binding.fab.hide()
                return false
            }

            override fun onActionItemClicked(mode: ActionMode?, item: MenuItem?): Boolean {
                return when (item?.itemId) {
                    R.id.delete -> {
                        deleteBook(book)
                        actionMode?.finish()
                        true
                    }
                    else -> false
                }
            }

            override fun onDestroyActionMode(mode: ActionMode?) {
                binding.myBooksList.enableAll(true)
                binding.fab.show()
            }
        }
        val activity = requireActivity() as MainActivity
        actionMode = activity.startSupportActionMode(callback)
    }


    private fun getPdfReader(sUri: String): PdfReader {
        val uri = Uri.parse(sUri)
        val contentResolver = requireActivity().contentResolver
        contentResolver.takePersistableUriPermission(
            uri,
            Intent.FLAG_GRANT_READ_URI_PERMISSION
        )
        return PdfReader(uri, contentResolver)
    }

    private fun getFileName(uri: Uri): String? {
        var result: String? = null
        if (uri.scheme == "content") {
            val cursor = requireActivity().contentResolver.query(uri, null, null, null, null)
            cursor.use { cursor ->
                if (cursor != null && cursor.moveToFirst()) {
                    result =
                        cursor.getString(cursor.getColumnIndexOrThrow(OpenableColumns.DISPLAY_NAME))
                }
            }
        }
        if (result == null) {
            result = uri.path
            val cut = result!!.lastIndexOf('/')
            if (cut != -1) {
                result = result?.substring(cut + 1)
            }
        }
        return result
    }
}

