package com.jcisneros.easyto.di

import com.jcisneros.easyto.data.datasource.interfaces.ITasksDataSource
import com.jcisneros.easyto.data.datasource.local.tasks.TasksLocalDataSource
import com.jcisneros.easyto.domain.repository.tasks.ITasksRepo
import com.jcisneros.easyto.domain.repository.tasks.TasksRepoImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityRetainedComponent
import kotlinx.coroutines.ExperimentalCoroutinesApi

@ExperimentalCoroutinesApi
@Module
@InstallIn(ActivityRetainedComponent::class)//inject for view models
abstract class ViewModelModule {

    //bind concrete implementation between ITaskRepo and TaskRepoImpl
    @Binds
    abstract fun bindTasksRepoImplement(repository: TasksRepoImpl): ITasksRepo

    @Binds
    abstract fun bindTasksLocalDataSource(datasource: TasksLocalDataSource): ITasksDataSource

}