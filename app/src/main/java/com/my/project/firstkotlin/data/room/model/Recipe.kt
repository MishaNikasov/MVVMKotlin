package com.my.project.firstkotlin.data.room.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "recipes_table")
data class Recipe (

    @PrimaryKey(autoGenerate = true)
    var id : Int? = 0,
    var title : String,
    var description : String,
    @ColumnInfo(name = "main_image")
    var mainImage : Int,
    var images : Int,
    var price : Double

)