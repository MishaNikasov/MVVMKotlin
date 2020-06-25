package com.my.project.firstkotlin.data.local.uimodel

import com.my.project.firstkotlin.R

data class TypeModel (
    val title : String,
    val image : Int? = null,
    val bgColor : Int? = null,
    val value : String
) {
    companion object {
        fun getTypeRecipe () : ArrayList<TypeModel> {
            val types = arrayListOf<TypeModel>()
            types.add(TypeModel("Soup", R.drawable.ic_soup, R.color.smooth_4, "soup"))
            types.add(TypeModel("Breakfast", R.drawable.ic_breakfast, R.color.smooth_3, "breakfast"))
            types.add(TypeModel("Main", R.drawable.ic_main, R.color.smooth_2, "main course"))
            types.add(TypeModel("Dessert", R.drawable.ic_dessert, R.color.smooth_1, "dessert"))
            types.add(TypeModel("Salad", R.drawable.ic_salad, R.color.smooth_5, "salad"))
            types.add(TypeModel("Drink", R.drawable.ic_drinks, R.color.smooth_6, "drink"))
            return types
        }
    }
}