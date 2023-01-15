package com.kuymakov.mylibrary.ui.booksreader

import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.navArgs
import com.kuymakov.mylibrary.base.BaseFragment
import com.kuymakov.mylibrary.base.extensions.actionBar
import com.kuymakov.mylibrary.base.extensions.showBackButton
import com.kuymakov.mylibrary.databinding.FragmentBooksReaderBinding
import com.kuymakov.mylibrary.ui.PdfReader

class BooksReaderFragment : BaseFragment<FragmentBooksReaderBinding>() {
    override val inflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentBooksReaderBinding
        get() = FragmentBooksReaderBinding::inflate
    private val args by navArgs<BooksReaderFragmentArgs>()
    private val pdfReader: PdfReader by lazy {
        val contentResolver = requireActivity().contentResolver
        val uri = Uri.parse(args.book.uri)
        PdfReader(uri, contentResolver)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        showBackButton(show = true)
        actionBar?.title = args.book.title
        binding.pager.adapter = PageAdapter(pdfReader)
    }

    override fun onDestroy() {
        super.onDestroy()
        pdfReader.close()
    }
}