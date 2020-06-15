package com.my.project.firstkotlin.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.my.project.firstkotlin.R
import com.my.project.firstkotlin.data.remote.data.response.Recipe
import com.my.project.firstkotlin.databinding.ItemHorizontalRecipeBinding

class HotRecipesAdapter : RecyclerView.Adapter<HotRecipesAdapter.RecipeViewHolder>() {

    private var recipeModelList : List<Recipe> = emptyList()

    class RecipeViewHolder (private val binding: ItemHorizontalRecipeBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind (recipe : Recipe) {
            binding.title.text = recipe.title
        }
    }

    fun setHotRecipesList (recipeModelList: List<Recipe>) {
        this.recipeModelList = recipeModelList
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecipeViewHolder {
        val layoutInflater : LayoutInflater = LayoutInflater.from(parent.context)
        val binding : ItemHorizontalRecipeBinding =
            DataBindingUtil.inflate(layoutInflater, R.layout.item_horizontal_recipe, parent, false)
        return RecipeViewHolder(binding)
    }

    override fun onBindViewHolder(holder: RecipeViewHolder, position: Int) = holder.bind(recipeModelList[position])

    override fun getItemCount() : Int = recipeModelList.size
}