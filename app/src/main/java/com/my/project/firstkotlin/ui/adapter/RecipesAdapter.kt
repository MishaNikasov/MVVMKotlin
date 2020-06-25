package com.my.project.firstkotlin.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import androidx.core.text.HtmlCompat
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.my.project.firstkotlin.R
import com.my.project.firstkotlin.data.remote.data.response.Recipe
import com.my.project.firstkotlin.databinding.ItemHorizontalRecipeBinding
import com.my.project.firstkotlin.databinding.ItemVerticalRecipeBinding
import com.my.project.firstkotlin.ui.util.Constant

class RecipesAdapter (
    private val orientation : Int,
    private val recipeNavigator: RecipeNavigator? = null
)
    : RecyclerView.Adapter<RecipesAdapter.BaseViewHolder>()
{

    private val differCallback = object : DiffUtil.ItemCallback<Recipe>() {
        override fun areItemsTheSame(oldItem: Recipe, newItem: Recipe): Boolean {
            return oldItem.id == newItem.id
        }
        override fun areContentsTheSame(oldItem: Recipe, newItem: Recipe): Boolean {
            return oldItem == newItem
        }
    }

    private val differ = AsyncListDiffer(this, differCallback)

    inner class HorizontalRecipeViewHolder (private val context: Context, private val binding: ItemHorizontalRecipeBinding) : BaseViewHolder(binding) {
        fun bind (recipe : Recipe, recipeNavigator: RecipeNavigator?) {

            val servingsTxt = "${recipe.servings} servings"
            val time = "${recipe.readyInMinutes} min"

            binding.title.text = recipe.title
            binding.servings.text = servingsTxt
            binding.time.text = time

            val url = "https://spoonacular.com/recipeImages/${recipe.image}"

            Glide
                .with(context)
                .load(url)
                .centerCrop()
                .dontAnimate()
                .placeholder(R.drawable.recipe_holder)
                .into(binding.image)

            binding.root.setOnClickListener {
                recipeNavigator?.onRecipeClick(recipe)
            }
        }
    }

    inner class VerticalRecipeViewHolder (private val context: Context, private val binding: ItemVerticalRecipeBinding) : BaseViewHolder(binding) {
        fun bind (recipe : Recipe) {

            binding.root.animation = AnimationUtils.loadAnimation(
                binding.root.context,
                R.anim.item_add
            )

            val servingsTxt = "${recipe.servings} servings"
            val time = "${recipe.readyInMinutes} min"

            binding.title.text = recipe.title
            binding.servings.text = servingsTxt
            binding.time.text = time
            binding.summary.text = HtmlCompat.fromHtml(recipe.summary.toString(), HtmlCompat.FROM_HTML_MODE_LEGACY)

            Glide
                .with(context)
                .load(recipe.image)
                .centerCrop()
                .dontAnimate()
                .placeholder(R.drawable.recipe_holder)
                .into(binding.image)

            binding.root.setOnClickListener {
                recipeNavigator?.onRecipeClick(recipe)
            }
        }
    }

    open class BaseViewHolder (viewDataBinding: ViewDataBinding) : RecyclerView.ViewHolder(viewDataBinding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder {

        val layoutInflater : LayoutInflater = LayoutInflater.from(parent.context)

        return if (orientation == Constant.ORIENTATION_HORIZONTAL){
            val binding : ItemHorizontalRecipeBinding = DataBindingUtil.inflate(layoutInflater, R.layout.item_horizontal_recipe, parent, false)
            HorizontalRecipeViewHolder(parent.context, binding)
        } else {
            val binding : ItemVerticalRecipeBinding = DataBindingUtil.inflate(layoutInflater, R.layout.item_vertical_recipe, parent, false)
            VerticalRecipeViewHolder(parent.context, binding)
        }
    }

    override fun onBindViewHolder(holder: BaseViewHolder, position: Int) {
        when (holder) {
            is HorizontalRecipeViewHolder -> differ.currentList[position].let {
                holder.bind(it, recipeNavigator)
            }
            is VerticalRecipeViewHolder -> differ.currentList[position].let {
                holder.bind(it)
            }
        }
    }

    override fun getItemCount() : Int = differ.currentList.size

    fun submitRecipesList (recipeModelList: List<Recipe>) {
        differ.submitList(recipeModelList)
    }

    interface RecipeNavigator {
        fun onRecipeClick (recipe : Recipe)
    }
}