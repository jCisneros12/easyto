package com.jcisneros.easyto.domain.repository.taskdetail

import com.jcisneros.easyto.data.model.TaskModel
import com.jcisneros.easyto.utils.Resource

interface ITaskDetailRepo {
    suspend fun insertNewTask(taskModel: TaskModel): Resource<Boolean>
}