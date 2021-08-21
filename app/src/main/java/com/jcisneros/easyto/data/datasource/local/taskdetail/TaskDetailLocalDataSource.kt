package com.jcisneros.easyto.data.datasource.local.taskdetail

import com.jcisneros.easyto.data.datasource.interfaces.ITaskDetailDataSource
import com.jcisneros.easyto.data.datasource.local.room.dao.TaskDao
import com.jcisneros.easyto.data.model.TaskModel
import com.jcisneros.easyto.data.model.modelTaskToEntity
import com.jcisneros.easyto.utils.Resource

class TaskDetailLocalDataSource(private val taskDao: TaskDao): ITaskDetailDataSource {

    override suspend fun insertNewTask(taskModel: TaskModel){
        val taskEntity = modelTaskToEntity(taskModel)
        taskDao.insertTask(taskEntity)
    }
}