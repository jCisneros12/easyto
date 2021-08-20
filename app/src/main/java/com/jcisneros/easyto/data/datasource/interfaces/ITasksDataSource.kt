package com.jcisneros.easyto.data.datasource.interfaces

import com.jcisneros.easyto.data.datasource.local.room.entities.TaskEntity
import com.jcisneros.easyto.data.model.TaskModel
import com.jcisneros.easyto.utils.Resource
import kotlinx.coroutines.flow.Flow

interface ITasksDataSource {
    suspend fun getAllTasks(): Flow<Resource<List<TaskModel>>>
    suspend fun insertTask(taskEntity: TaskEntity): Resource<Boolean>
    suspend fun insertTasks(data: Resource<List<TaskModel>>)
}