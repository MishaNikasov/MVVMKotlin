package com.my.project.firstkotlin.di

import androidx.hilt.lifecycle.ViewModelInject
import com.my.project.firstkotlin.data.local.repository.FilterRepo
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import dagger.hilt.android.components.FragmentComponent
import dagger.hilt.android.components.ViewComponent
import dagger.hilt.android.components.ViewWithFragmentComponent
import dagger.hilt.android.scopes.FragmentScoped
import dagger.hilt.android.scopes.ViewScoped
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
object LocalDataModule {

    @Provides
    fun provideLocalRepo () = FilterRepo()

}