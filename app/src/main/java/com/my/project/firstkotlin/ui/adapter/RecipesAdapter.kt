package com.my.project.firstkotlin.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.my.project.firstkotlin.R
import com.my.project.firstkotlin.data.room.model.Receipt
import com.my.project.firstkotlin.databinding.ItemReceiptBinding

class RecipesAdapter (private val clickListener : (Receipt) -> Unit) : RecyclerView.Adapter<RecipesAdapter.ReceiptViewHolder>() {

    private var receiptList : List<Receipt> = emptyList()

    class ReceiptViewHolder (private val binding: ItemReceiptBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind (receipt : Receipt, clickListener : (Receipt) -> Unit) {
            binding.title.text = receipt.title
            binding.description.text = receipt.description
            binding.root.setOnClickListener {
                clickListener(receipt)}
        }
    }

    fun setReceiptsList (receiptList: List<Receipt>) {
        this.receiptList = receiptList
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ReceiptViewHolder {
        val layoutInflater : LayoutInflater = LayoutInflater.from(parent.context)
        val binding : ItemReceiptBinding =
            DataBindingUtil.inflate(layoutInflater, R.layout.item_receipt, parent, false)
        return ReceiptViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ReceiptViewHolder, position: Int) = holder.bind(receiptList[position], clickListener)

    override fun getItemCount() : Int = receiptList.size
}