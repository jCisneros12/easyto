package com.jcisneros.easyto.data.datasource.interfaces

import com.jcisneros.easyto.data.model.TaskModel
import com.jcisneros.easyto.utils.Resource

interface ITaskDetailDataSource {

    suspend fun insertNewTask(taskModel: TaskModel)
    suspend fun getTaskById(taskId: String): Resource<TaskModel>
    suspend fun deleteTaskById(taskId: String)
    suspend fun updateTaskById(taskModel: TaskModel)
}