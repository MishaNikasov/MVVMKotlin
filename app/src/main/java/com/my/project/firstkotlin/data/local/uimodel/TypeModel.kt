package com.my.project.firstkotlin.data.local.uimodel

import com.my.project.firstkotlin.R

data class TypeModel (
    val title : String,
    val image : Int,
    val bgColor : Int
) {
    companion object {
        fun getTypeRecipe () : ArrayList<TypeModel> {
            val types = arrayListOf<TypeModel>()
            types.add(TypeModel("Soup", R.drawable.ic_soup, R.color.smooth_4))
            types.add(TypeModel("Breakfast", R.drawable.ic_breakfast, R.color.smooth_3))
            types.add(TypeModel("Main", R.drawable.ic_main, R.color.smooth_2))
            types.add(TypeModel("Dessert", R.drawable.ic_dessert, R.color.smooth_1))
            types.add(TypeModel("Salad", R.drawable.ic_salad, R.color.smooth_5))
            types.add(TypeModel("Drink", R.drawable.ic_drinks, R.color.smooth_6))
            return types
        }
    }
}