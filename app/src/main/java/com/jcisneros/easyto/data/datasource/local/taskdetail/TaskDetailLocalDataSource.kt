package com.jcisneros.easyto.data.datasource.local.taskdetail

import com.jcisneros.easyto.data.datasource.interfaces.ITaskDetailDataSource
import com.jcisneros.easyto.data.datasource.local.room.dao.TaskDao
import com.jcisneros.easyto.data.model.TaskModel
import com.jcisneros.easyto.data.model.entityTaskToModel
import com.jcisneros.easyto.data.model.modelTaskToEntity
import com.jcisneros.easyto.data.model.toTaskModel
import com.jcisneros.easyto.utils.Resource
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.cancel
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.collect

class TaskDetailLocalDataSource(private val taskDao: TaskDao): ITaskDetailDataSource {

    override suspend fun insertNewTask(taskModel: TaskModel){
        val taskEntity = modelTaskToEntity(taskModel)
        taskDao.insertTask(taskEntity)
    }

    override suspend fun getTaskById(taskId: String): Resource<TaskModel> {
        val taskModel = entityTaskToModel(taskDao.getTaskById(taskId))
        return Resource.Success(taskModel)
    }

    override suspend fun deleteTaskById(taskId: String) {
        taskDao.deleteTaskById(taskId)
    }

    override suspend fun updateTaskById(taskModel: TaskModel) {
        taskDao.updateTaskById(modelTaskToEntity(taskModel))
    }

}
