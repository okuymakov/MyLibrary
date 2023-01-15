package com.kuymakov.mylibrary.ui.mybooks.dialog

import android.annotation.SuppressLint
import android.app.Dialog
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.activityViewModels
import com.kuymakov.mylibrary.R
import com.kuymakov.mylibrary.databinding.DialogEditBookBinding
import com.kuymakov.mylibrary.models.MyBook
import com.kuymakov.mylibrary.ui.mybooks.MyBooksViewModel

class EditBookDialog(private val book: MyBook) : DialogFragment() {
    companion object {
        const val TAG = "EditBookDialog"
    }
    private val viewModel: MyBooksViewModel by activityViewModels()
    private var _binding: DialogEditBookBinding? = null
    private val binding get() = _binding!!

    @SuppressLint("InflateParams")
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val builder = AlertDialog.Builder(requireActivity())
        val inflater = requireActivity().layoutInflater
        _binding = DialogEditBookBinding.inflate(inflater, null, false)
        //inflater.inflate(R.layout.dialog_edit_book, null)
        with(builder) {
            setTitle(R.string.edit_book_title)
            setView(binding.root)
            binding.editBook.setText(book.title)
            setPositiveButton(R.string.ok) { _, _ ->
                requireDialog().dismiss()
                book.title = binding.editBook.text.toString()
                viewModel.updateBook(book)

            }
            setNegativeButton(R.string.cancel) { _, _ ->
                requireDialog().cancel()
            }
        }
        return builder.create()

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}