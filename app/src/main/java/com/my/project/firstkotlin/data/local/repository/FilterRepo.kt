package com.my.project.firstkotlin.data.local.repository

import com.my.project.firstkotlin.R
import com.my.project.firstkotlin.data.local.TypeModel

class FilterRepo {

    //filter
    private var filterList : ArrayList<TypeModel> = arrayListOf()

    private var typeList : ArrayList<TypeModel> = arrayListOf()

    init {
        //set types
        typeList.add(TypeModel("Soup", R.drawable.ic_soup, R.color.smooth_4, "soup"))
        typeList.add(TypeModel("Breakfast", R.drawable.ic_breakfast, R.color.smooth_3, "breakfast"))
        typeList.add(TypeModel("Main", R.drawable.ic_main, R.color.smooth_2, "main course"))
        typeList.add(TypeModel("Dessert", R.drawable.ic_dessert, R.color.smooth_1, "dessert"))
        typeList.add(TypeModel("Salad", R.drawable.ic_salad, R.color.smooth_5, "salad"))
        typeList.add(TypeModel("Drink", R.drawable.ic_drinks, R.color.smooth_6, "drink"))
    }

    fun getTypeList() : List<TypeModel>{
        return typeList;
    }

    fun getFilterList() : List<TypeModel>{
        return filterList
    }

    fun addToFilterList(item : TypeModel) {
        if (!checkIsInFilterList(item.value)){
            filterList.add(item)
        }
    }

    fun delFromType(item : TypeModel) {
        filterList.remove(item)
    }

    private fun checkIsInFilterList(filter : String) : Boolean{
        for (item in filterList) {
            if (filter == item.value) {
                return true
            }
        }
        return false
    }
}