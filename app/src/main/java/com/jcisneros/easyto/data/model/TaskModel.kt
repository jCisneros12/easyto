package com.jcisneros.easyto.data.model

import com.jcisneros.easyto.data.datasource.local.room.entities.TaskEntity

data class TaskModel(
    val taskId: String,
    val title: String,
    val description: String,
    val isComplete: Boolean,
    val image: String? = null
)

//convert entity to model
fun toTaskModel(entitieList: List<TaskEntity>): List<TaskModel> {
    val taskModelList  = mutableListOf<TaskModel>()
    entitieList.forEach { entity ->
            val taskModel = TaskModel(
                taskId =entity.taskId,
                title = entity.title!!,
                description = entity.description!!,
                isComplete = entity.isComplete,
                image = entity.image
            )
        taskModelList.add(taskModel)
    }
    return taskModelList
}

//convert model to entity
fun toTaskEntity(modelList: List<TaskModel>): List<TaskEntity> {
    val taskEntityList  = mutableListOf<TaskEntity>()
    modelList.forEach { model ->
        val taskEntity = TaskEntity(
            taskId = model.taskId,
            title = model.title,
            description = model.description,
            isComplete = model.isComplete,
            image = model.image
        )
        taskEntityList.add(taskEntity)
    }
    return taskEntityList
}

//convert TaskModel to TaskEntity

fun modelTaskToEntity(taskModel: TaskModel): TaskEntity{
    return TaskEntity(
        taskId = taskModel.taskId,
        title = taskModel.title,
        description = taskModel.description,
        isComplete = taskModel.isComplete,
        image = taskModel.image
    )
}