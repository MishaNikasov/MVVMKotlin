package com.my.project.firstkotlin.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.my.project.firstkotlin.R
import com.my.project.firstkotlin.data.room.model.Receipt
import kotlinx.android.synthetic.main.item_receipt.view.*

class RecipesAdapter () : RecyclerView.Adapter<RecipesAdapter.ReceiptViewHolder>() {

    private var receiptList : List<Receipt>? = null

    class ReceiptViewHolder (itemView : View) : RecyclerView.ViewHolder(itemView) {
        val image : ImageView = itemView.img
        val title : TextView = itemView.title
        val description : TextView = itemView.description
    }

    fun setReceiptsList (receiptList: List<Receipt>) {
        this.receiptList = receiptList
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ReceiptViewHolder {
        val itemView : View = LayoutInflater.from(parent.context).inflate(R.layout.item_receipt, parent, false)
        return ReceiptViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ReceiptViewHolder, position: Int) {
        val currentReceipt = receiptList?.get(position)

        holder.title.text = currentReceipt?.title
        holder.description.text = currentReceipt?.description
    }

    override fun getItemCount() : Int {
        return if (receiptList != null) {
            receiptList!!.size
        } else 0
    }
}