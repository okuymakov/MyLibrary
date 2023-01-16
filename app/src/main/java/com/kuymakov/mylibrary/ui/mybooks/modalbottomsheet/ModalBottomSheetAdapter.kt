package com.kuymakov.mylibrary.ui.mybooks.modalbottomsheet

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.kuymakov.mylibrary.databinding.ModalBottomSheetItemBinding
import com.kuymakov.mylibrary.models.Tool

class ModalBottomSheetAdapter(
    private val onClick: (Tool) -> Unit,
) : RecyclerView.Adapter<ModalBottomSheetAdapter.ViewHolder>() {
    private val data = listOf(Tool.Read, Tool.Rename, Tool.Delete)

    class ViewHolder(
        binding: ModalBottomSheetItemBinding,
        private val onClick: (Tool) -> Unit,
    ) :
        RecyclerView.ViewHolder(binding.root) {
        private val toolItem = binding.toolItem
        private var tool: Tool? = null

        init {
            binding.root.setOnClickListener {
                tool?.let { tool -> onClick(tool) }
            }
        }

        fun bind(tool: Tool) {
            this.tool = tool
            toolItem.text = tool.name
            toolItem.setCompoundDrawablesWithIntrinsicBounds(tool.iconSrc, 0, 0, 0)
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            ModalBottomSheetItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding, onClick)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val tool = data[position]
        holder.bind(tool)
    }

    override fun getItemCount() = data.size
}