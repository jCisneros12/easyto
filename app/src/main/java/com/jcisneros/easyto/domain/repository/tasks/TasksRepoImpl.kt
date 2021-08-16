package com.jcisneros.easyto.domain.repository.tasks

import com.jcisneros.easyto.data.datasource.interfaces.ITasksDataSource
import com.jcisneros.easyto.data.datasource.local.tasks.TasksLocalDataSource
import com.jcisneros.easyto.data.model.TaskModel
import com.jcisneros.easyto.utils.Resource

class TasksRepoImpl(private val datasource: ITasksDataSource): ITasksRepo {
    override fun getAllTask(): Resource<List<TaskModel>> {
        return datasource.getAllTasks()
    }
}