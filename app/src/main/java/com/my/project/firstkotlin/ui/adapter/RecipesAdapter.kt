package com.my.project.firstkotlin.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.my.project.firstkotlin.R
import com.my.project.firstkotlin.data.room.model.Recipe
import com.my.project.firstkotlin.databinding.ItemRecipeBinding

class RecipesAdapter (private val clickListener : (Recipe) -> Unit) : RecyclerView.Adapter<RecipesAdapter.RecipeViewHolder>() {

    private var recipeList : List<Recipe> = emptyList()

    class RecipeViewHolder (private val binding: ItemRecipeBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind (recipe : Recipe, clickListener : (Recipe) -> Unit) {
            binding.title.text = recipe.title
            binding.description.text = recipe.description
            binding.root.setOnClickListener {
                clickListener(recipe)}
        }
    }

    fun setRecipesList (recipeList: List<Recipe>) {
        this.recipeList = recipeList
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecipeViewHolder {
        val layoutInflater : LayoutInflater = LayoutInflater.from(parent.context)
        val binding : ItemRecipeBinding =
            DataBindingUtil.inflate(layoutInflater, R.layout.item_recipe, parent, false)
        return RecipeViewHolder(binding)
    }

    override fun onBindViewHolder(holder: RecipeViewHolder, position: Int) = holder.bind(recipeList[position], clickListener)

    override fun getItemCount() : Int = recipeList.size
}