package com.kuymakov.mylibrary.ui.mybooks

import android.net.Uri
import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.view.ActionMode
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.kuymakov.mylibrary.R
import com.kuymakov.mylibrary.base.BaseFragment
import com.kuymakov.mylibrary.base.extensions.toFileName
import com.kuymakov.mylibrary.databinding.FragmentMyBooksBinding
import com.kuymakov.mylibrary.models.MyBook
import com.kuymakov.mylibrary.ui.main.MainFragment
import com.kuymakov.mylibrary.ui.mybooks.adapter.MyBooksAdapter
import com.kuymakov.mylibrary.ui.mybooks.lifecycleobserver.GetContentLifecycleObserver
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch


@AndroidEntryPoint
class MyBooksFragment : BaseFragment<FragmentMyBooksBinding>(), ActionMode.Callback,
    ClickHandler<MyBook> {
    override val inflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentMyBooksBinding
        get() = FragmentMyBooksBinding::inflate
    private val viewModel: MyBooksViewModel by activityViewModels()
    private val navController by lazy { findNavController() }
    private var observer: GetContentLifecycleObserver? = null
    private var actionMode: ActionMode? = null
    private var isActionMode = false
    private val adapter by lazy {
        MyBooksAdapter(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        observer = GetContentLifecycleObserver(requireActivity().activityResultRegistry, ::addBook)
        lifecycle.addObserver(observer!!)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.fab.setOnClickListener {
            observer?.pickFile()
        }

        binding.myBooksList.adapter = adapter

        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.books.collect { books ->
                    adapter.updateData(books)
                }
            }
        }
        navController.addOnDestinationChangedListener { _, _, _ ->
            actionMode?.finish()
        }
    }

    private fun addBook(uri: Uri) {
        val title = uri.toFileName(contentResolver = requireActivity().contentResolver)
        val book = MyBook(title, uri.toString())
        if (!adapter.isExist(book)) {
            viewModel.addBooks(book)
        } else {
            Toast.makeText(context, "Книга уже добавлена", Toast.LENGTH_SHORT).show()
        }
    }

    private fun deleteBooks(books: List<MyBook>) {
        viewModel.deleteBooks(*books.toTypedArray())
    }


    private fun onClick(el: MyBook) {
        if (isActionMode) {
            adapter.selectBook(el, select = !el.isSelected)
            if (adapter.allSelectedItems.isEmpty()) actionMode?.finish()
        } else if (navController.currentDestination?.id != R.id.modalBottomSheet) {
            val action =
                MyBooksFragmentDirections.actionMyBooksFragmentToMyBooksModalBottomSheet(el.copy())
            navController.navigate(action)
        }
    }

    private fun onLongClick(el: MyBook) {
        if (!isActionMode) {
            val activity = requireActivity() as AppCompatActivity
            actionMode = activity.startSupportActionMode(this)
            adapter.selectBook(el, select = !el.isSelected)
        }
    }

    override fun onCreateActionMode(mode: ActionMode?, menu: Menu?): Boolean {
        (requireParentFragment().requireParentFragment() as MainFragment).hideBottomNav()
        isActionMode = true
        mode!!.menuInflater.inflate(R.menu.contextual_menu, menu)
        return true
    }

    override fun onPrepareActionMode(mode: ActionMode?, menu: Menu?): Boolean {
        binding.fab.hide()
        onLongClick = null
        return false
    }

    override fun onActionItemClicked(mode: ActionMode?, item: MenuItem?) = when (item?.itemId) {
        R.id.delete -> {
            val books = adapter.allSelectedItems
            deleteBooks(books)
            actionMode?.finish()
            true
        }
        else -> false
    }

    override fun onDestroyActionMode(mode: ActionMode?) {
        binding.fab.show()
        adapter.selectAll(select = false)
        onLongClick = ::onLongClick
        isActionMode = false
        (requireParentFragment().requireParentFragment() as MainFragment).showBottomNav()
    }


    override var onClick: ((MyBook) -> Unit)? = ::onClick
    override var onLongClick: ((MyBook) -> Unit)? = ::onLongClick
}

