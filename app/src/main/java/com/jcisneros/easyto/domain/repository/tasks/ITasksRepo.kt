package com.jcisneros.easyto.domain.repository.tasks

import com.jcisneros.easyto.data.model.TaskModel
import com.jcisneros.easyto.utils.Resource
import kotlinx.coroutines.flow.Flow

interface ITasksRepo {
    fun getAllTask(): Resource<List<TaskModel>>
    suspend fun getLocalTasks(): Flow<Resource<List<TaskModel>>>
    suspend fun getTasks(): Flow<Resource<List<TaskModel>>>
}