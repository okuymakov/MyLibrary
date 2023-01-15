package com.kuymakov.mylibrary.ui.mybooks.modalbottomsheet

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.kuymakov.mylibrary.base.BaseBottomSheetFragment
import com.kuymakov.mylibrary.databinding.ModalBottomSheetBinding
import com.kuymakov.mylibrary.models.Tool
import com.kuymakov.mylibrary.ui.mybooks.MyBooksViewModel
import com.kuymakov.mylibrary.ui.mybooks.dialog.EditBookDialog

class ModalBottomSheet : BaseBottomSheetFragment<ModalBottomSheetBinding>() {
    companion object {
        const val TAG = "ModalBottomSheet"
    }

    private val args by navArgs<ModalBottomSheetArgs>()
    private val viewModel: MyBooksViewModel by activityViewModels()
    override val inflater: (LayoutInflater, ViewGroup?, Boolean) -> ModalBottomSheetBinding
        get() = ModalBottomSheetBinding::inflate

    private val navController by lazy {
        findNavController()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        requireDialog().window?.let { it.navigationBarColor = Color.TRANSPARENT }
        val adapter = ModalBottomSheetAdapter(::onClick)
        binding.tools.adapter = adapter
    }

    private fun onClick(tool: Tool) {
        when (tool) {
            Tool.Delete -> {
                viewModel.deleteBooks(args.book)
                dismiss()
            }
            Tool.Read -> {
                val action =
                    ModalBottomSheetDirections.actionModalBottomSheetToBooksReaderFragment(args.book.copy())
                navController.navigate(action)
            }
            Tool.Rename -> {
                EditBookDialog(args.book.copy()).show(parentFragmentManager, EditBookDialog.TAG)
                dismiss()
            }
        }
    }
}