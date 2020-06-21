package com.my.project.firstkotlin.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.my.project.firstkotlin.R
import com.my.project.firstkotlin.data.remote.data.response.Recipe
import com.my.project.firstkotlin.databinding.ItemHorizontalRecipeBinding
import com.my.project.firstkotlin.databinding.ItemProgressBinding
import com.my.project.firstkotlin.databinding.ItemVerticalRecipeBinding
import com.my.project.firstkotlin.ui.util.Constant
import com.my.project.firstkotlin.ui.util.RecipeNavigator
import kotlinx.android.synthetic.main.item_progress.view.*

class RecipesAdapter (private val orientation : Int, private val recipeNavigator: RecipeNavigator? = null) : RecyclerView.Adapter<RecipesAdapter.BaseViewHolder>() {

    private var recipeModelList : ArrayList<Recipe?> = arrayListOf()

    companion object {
        const val VIEW_TYPE_ITEM = 1
        const val VIEW_TYPE_LOADING = 2
    }

    class HorizontalRecipeViewHolder (private val context: Context, private val binding: ItemHorizontalRecipeBinding) : BaseViewHolder(binding) {
        fun bind (recipe : Recipe, recipeNavigator: RecipeNavigator?) {

            val servingsTxt = "${recipe.servings} servings"
            val time = "${recipe.readyInMinutes} min"

            binding.title.text = recipe.title
            binding.servings.text = servingsTxt
            binding.time.text = time

            binding.addBtn.setOnClickListener {
                recipeNavigator?.onRecipeAdd(recipe)
            }

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

    inner class VerticalRecipeViewHolder (private val binding: ItemVerticalRecipeBinding) : BaseViewHolder(binding) {
        fun bind (recipe : Recipe) {
            binding.title.text = recipe.title
            binding.description.text = recipe.sourceUrl
        }
    }

    inner class LoadingViewHolder (private val binding: ItemProgressBinding) : BaseViewHolder(binding) {
        fun bind (orientation: Int) {
            val params = binding.root.layoutParams as ViewGroup.LayoutParams
            if (orientation == Constant.ORIENTATION_HORIZONTAL) {
                params.width = ViewGroup.LayoutParams.WRAP_CONTENT
                params.height = ViewGroup.LayoutParams.MATCH_PARENT
            } else {
                params.width = ViewGroup.LayoutParams.MATCH_PARENT
                params.height = ViewGroup.LayoutParams.WRAP_CONTENT
            }
            binding.root.layoutParams = params
        }
    }

    open class BaseViewHolder (viewDataBinding: ViewDataBinding) : RecyclerView.ViewHolder(viewDataBinding.root)

    fun setRecipesList (recipeModelList: List<Recipe>) {
        this.recipeModelList = ArrayList(recipeModelList)
        notifyDataSetChanged()
    }

    override fun getItemViewType(position: Int): Int {
        return if (recipeModelList[position] != null)
            VIEW_TYPE_ITEM
        else
            VIEW_TYPE_LOADING
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder {

        val layoutInflater : LayoutInflater = LayoutInflater.from(parent.context)

        return if (viewType == VIEW_TYPE_ITEM) {
            if (orientation == Constant.ORIENTATION_HORIZONTAL){
                val binding : ItemHorizontalRecipeBinding = DataBindingUtil.inflate(layoutInflater, R.layout.item_horizontal_recipe, parent, false)
                HorizontalRecipeViewHolder(parent.context, binding)
            } else {
                val binding : ItemVerticalRecipeBinding = DataBindingUtil.inflate(layoutInflater, R.layout.item_vertical_recipe, parent, false)
                VerticalRecipeViewHolder(binding)
            }

        } else {
            val binding : ItemProgressBinding = DataBindingUtil.inflate(layoutInflater, R.layout.item_progress, parent, false)
            LoadingViewHolder(binding)
        }

    }

    override fun onBindViewHolder(holder: BaseViewHolder, position: Int) {
        when (holder) {
            is HorizontalRecipeViewHolder -> recipeModelList[position]?.let {
                holder.bind(it, recipeNavigator)
            }
            is VerticalRecipeViewHolder -> recipeModelList[position]?.let {
                holder.bind(it)
            }
            is LoadingViewHolder -> holder.bind(orientation)
        }
    }

    fun startLoading() {
        recipeModelList.add(null)
        notifyItemInserted(recipeModelList.size - 1)
    }

    fun stopLoading() {
        recipeModelList.removeAt(recipeModelList.size - 1)
        notifyItemRemoved(recipeModelList.size)
    }

    override fun getItemCount() : Int = recipeModelList.size

}