package com.jcisneros.easyto.domain.repository.tasks

import com.jcisneros.easyto.data.model.TaskModel
import com.jcisneros.easyto.utils.Resource
import kotlinx.coroutines.flow.Flow

interface ITasksRepo {
    suspend fun getLocalTasks(): Flow<Resource<List<TaskModel>>>
    suspend fun getAllTasks(): Flow<Resource<List<TaskModel>>>
}