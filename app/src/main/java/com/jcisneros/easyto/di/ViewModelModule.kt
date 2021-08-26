package com.jcisneros.easyto.di

import com.jcisneros.easyto.data.datasource.firebase.auth.AuthFirebaseDataSource
import com.jcisneros.easyto.data.datasource.firebase.auth.IAuthDataSource
import com.jcisneros.easyto.data.datasource.interfaces.ITaskDetailDataSource
import com.jcisneros.easyto.data.datasource.interfaces.ITasksDataSource
import com.jcisneros.easyto.data.datasource.local.auth.AuthLocalDataSourceImpl
import com.jcisneros.easyto.data.datasource.local.auth.IAuthLocalDataSource
import com.jcisneros.easyto.data.datasource.local.taskdetail.TaskDetailLocalDataSource
import com.jcisneros.easyto.data.datasource.local.tasks.TasksLocalDataSource
import com.jcisneros.easyto.domain.repository.auth.AuthRepoImpl
import com.jcisneros.easyto.domain.repository.auth.IAuthRepo
import com.jcisneros.easyto.domain.repository.taskdetail.ITaskDetailRepo
import com.jcisneros.easyto.domain.repository.taskdetail.TaskDetailRepoImpl
import com.jcisneros.easyto.domain.repository.tasks.ITasksRepo
import com.jcisneros.easyto.domain.repository.tasks.TasksRepoImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityRetainedComponent
import kotlinx.coroutines.ExperimentalCoroutinesApi

/*
* this module is used for hilt to inject dependencies to all view models
* */

@ExperimentalCoroutinesApi
@Module
@InstallIn(ActivityRetainedComponent::class)//inject for view models
abstract class ViewModelModule {

    //tasks
    @Binds
    abstract fun bindTasksRepoImplement(repository: TasksRepoImpl): ITasksRepo

    @Binds
    abstract fun bindTasksLocalDataSource(datasource: TasksLocalDataSource): ITasksDataSource

    //Auth
    @Binds
    abstract fun bindAuthFirebaseDataSource(datasource: AuthFirebaseDataSource): IAuthDataSource

    @Binds
    abstract fun bindAuthLocalDataSource(datasource: AuthLocalDataSourceImpl): IAuthLocalDataSource

    @Binds
    abstract fun bindAuthRepoImplement(repository: AuthRepoImpl): IAuthRepo

    //task detail
    @Binds
    abstract fun bindTaskDetailLocalDataSource(datasource: TaskDetailLocalDataSource): ITaskDetailDataSource

    @Binds
    abstract fun bindTaskDetailRepoImplement(repository: TaskDetailRepoImpl): ITaskDetailRepo

}