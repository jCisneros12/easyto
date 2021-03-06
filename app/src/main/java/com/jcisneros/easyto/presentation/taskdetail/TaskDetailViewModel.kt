package com.jcisneros.easyto.presentation.taskdetail

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.liveData
import com.jcisneros.easyto.data.model.TaskModel
import com.jcisneros.easyto.domain.repository.taskdetail.ITaskDetailRepo
import com.jcisneros.easyto.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import javax.inject.Inject
import kotlin.Exception

@HiltViewModel
class TaskDetailViewModel @Inject constructor(private val repository: ITaskDetailRepo) : ViewModel() {

    fun saveTask(taskModel: TaskModel) = liveData(Dispatchers.IO) {
        emit(Resource.Loading())
        try {
            emit(repository.insertNewTask(taskModel))
        } catch (e: Exception) {
            emit(Resource.Failure(e))
            Log.e("VM-NEW-TASK", e.toString())
        }
    }

    fun getTaskById(taskId: String) = liveData(Dispatchers.IO) {
        emit(Resource.Loading())
        try {
            emit(repository.getTaskById(taskId))
        } catch (e: Exception) {
            emit(Resource.Failure(e))
            Log.e("VM-GET-TASK", e.toString())
        }
    }

    fun deleteTaskById(taskId: String) = liveData(Dispatchers.IO) {
        emit(Resource.Loading())
        try {
            emit(repository.deleteTaskById(taskId))
        } catch (e: Exception) {
            emit(Resource.Failure(e))
            Log.e("VM-DELETE-TASK", e.toString())
        }
    }

    fun updateTaskById(taskId: String, taskModel: TaskModel) = liveData(Dispatchers.IO) {
        emit(Resource.Loading())
        try {
            emit(repository.updateTaskById(taskId, taskModel))
        } catch (e: Exception) {
            emit(Resource.Failure(e))
            Log.e("VM-UPDATE-TASK", e.toString())
        }
    }

}
