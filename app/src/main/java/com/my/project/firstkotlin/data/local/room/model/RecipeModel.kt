package com.my.project.firstkotlin.data.local.room.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "recipes_table")
data class RecipeModel (

    var remoteId : Int,
    var title : String,
    var servings : Int,
    @ColumnInfo(name = "main_image")
    var mainImage : String,
    var timeToReady : Int,
    var sourceUrl: String

){
    @PrimaryKey(autoGenerate = true)
    var id : Int? = null
}