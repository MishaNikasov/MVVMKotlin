package com.my.project.firstkotlin.di

import android.content.Context
import androidx.room.Room
import com.my.project.firstkotlin.data.database.room.RecipesDatabase
import com.my.project.firstkotlin.ui.util.Constant
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideRecipeDatabase (
       @ApplicationContext app : Context
    ) = Room.databaseBuilder(
       app,
       RecipesDatabase::class.java,
       Constant.RECIPE_DB_NAME
    )
       .fallbackToDestructiveMigration()
       .build()

    @Provides
    @Singleton
    fun provideRecipesDao (db : RecipesDatabase) = db.getReceiptsDao()
//
//    @Provides
//    @Singleton
//    fun provideLocalRepo () = LocalRepo()
}