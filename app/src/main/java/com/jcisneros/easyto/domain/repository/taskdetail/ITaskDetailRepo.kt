package com.jcisneros.easyto.domain.repository.taskdetail

import com.jcisneros.easyto.data.model.TaskModel
import com.jcisneros.easyto.utils.Resource

interface ITaskDetailRepo {
    suspend fun insertNewTask(taskModel: TaskModel): Resource<Boolean>
    suspend fun getTaskById(taskId: String): Resource<TaskModel>
    suspend fun deleteTaskById(taskId: String): Resource<Boolean>
    suspend fun updateTaskById(taskId: String, taskModel: TaskModel): Resource<Boolean>
}