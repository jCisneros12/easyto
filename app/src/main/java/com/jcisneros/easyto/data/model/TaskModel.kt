package com.jcisneros.easyto.data.model

data class TaskModel(
    val title: String,
    val description: String,
    val isComplete: Boolean,
    val image: String? = null
)
