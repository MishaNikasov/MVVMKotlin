package com.my.project.firstkotlin.ui.adapter

import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import com.my.project.firstkotlin.R
import com.my.project.firstkotlin.data.local.TypeModel
import kotlinx.android.synthetic.main.item_filter.view.*

class FilterAdapter(
    private val listener: Listener? = null
) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>()
{

    private val differCallback = object : DiffUtil.ItemCallback<TypeModel>() {
        override fun areItemsTheSame(oldItem: TypeModel, newItem: TypeModel): Boolean {
            return oldItem.value == newItem.value
        }
        override fun areContentsTheSame(oldItem: TypeModel, newItem: TypeModel): Boolean {
            return oldItem == newItem
        }
    }

    private val differ = AsyncListDiffer(this, differCallback)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return TypeViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.item_filter,
                parent,
                false
            ),
            listener
        )
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is TypeViewHolder -> {
                holder.bind(differ.currentList[position])
            }
        }
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    class TypeViewHolder(itemView: View, private val listener: Listener?) : RecyclerView.ViewHolder(itemView) {
        fun bind(item: TypeModel) {
            itemView.setOnClickListener {
                listener?.interact(adapterPosition, item)
            }
            itemView.text.text = item.title
        }
    }

    interface Listener {
        fun interact(position: Int, item: TypeModel)
    }

    fun submitFiltersList (filters : List<TypeModel>) {
        differ.submitList(filters.toList())
    }
}

