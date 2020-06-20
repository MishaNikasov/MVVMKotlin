package com.my.project.firstkotlin.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.my.project.firstkotlin.R
import com.my.project.firstkotlin.data.remote.data.response.Ingredient
import com.my.project.firstkotlin.data.remote.data.response.Recipe
import com.my.project.firstkotlin.databinding.ItemIngredientBinding
import java.lang.StringBuilder

class IngredientsAdapter : RecyclerView.Adapter<IngredientsAdapter.IngredientsViewHolder>(){

    private val differCallback = object : DiffUtil.ItemCallback<Ingredient?>() {
        override fun areItemsTheSame(oldItem: Ingredient, newItem: Ingredient): Boolean {
            return oldItem.id == newItem.id
        }
        override fun areContentsTheSame(oldItem: Ingredient, newItem: Ingredient): Boolean {
            return oldItem == newItem
        }
    }

    val differ = AsyncListDiffer(this, differCallback)

    class IngredientsViewHolder (private val binding : ItemIngredientBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind (ingredient: Ingredient) {

            binding.ingredientItem = ingredient

            val amount = buildString{
                append(ingredient.measures.metric.amount)
                append(" ")
                append(ingredient.measures.metric.unitShort)
            }

            binding.howMatch.text = amount
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): IngredientsViewHolder {
        val layoutInflater : LayoutInflater = LayoutInflater.from(parent.context)
        val binding : ItemIngredientBinding =
            DataBindingUtil.inflate(layoutInflater, R.layout.item_ingredient, parent, false)
        return IngredientsViewHolder(binding)
    }

    override fun onBindViewHolder(holder: IngredientsViewHolder, position: Int) {
        differ.currentList[position]?.let {
            holder.bind(it)
        }
    }

    override fun getItemCount() = differ.currentList.size
}