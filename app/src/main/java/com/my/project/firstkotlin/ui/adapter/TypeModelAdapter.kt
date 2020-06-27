package com.my.project.firstkotlin.ui.adapter

import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import com.my.project.firstkotlin.R
import com.my.project.firstkotlin.data.local.TypeModel
import kotlinx.android.synthetic.main.item_type_model.view.*

class TypeModelAdapter(
    private val models: List<TypeModel>,
    private val interaction: Interaction? = null
) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>()
{
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {

        return TypeViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.item_type_model,
                parent,
                false
            ),
            interaction
        )
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is TypeViewHolder -> {
                holder.bind(models[position])
            }
        }
    }

    override fun getItemCount(): Int {
        return models.size
    }

    class TypeViewHolder(itemView: View, private val interaction: Interaction?) : RecyclerView.ViewHolder(itemView) {
        fun bind(item: TypeModel) {
            itemView.setOnClickListener {
                interaction?.onItemSelected(adapterPosition, item)
            }
            itemView.text.text = item.title
            itemView.image.setImageDrawable(ContextCompat.getDrawable(itemView.context, item.image!!))
            itemView.bg.setColorFilter(ContextCompat.getColor(itemView.context, item.bgColor!!))
        }
    }

    interface Interaction {
        fun onItemSelected(position: Int, item: TypeModel)
    }
}

