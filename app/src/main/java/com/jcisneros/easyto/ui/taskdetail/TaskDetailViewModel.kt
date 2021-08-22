package com.jcisneros.easyto.ui.taskdetail

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.liveData
import com.jcisneros.easyto.data.model.TaskModel
import com.jcisneros.easyto.domain.repository.taskdetail.ITaskDetailRepo
import com.jcisneros.easyto.utils.Resource
import kotlinx.coroutines.Dispatchers
import kotlin.Exception

class TaskDetailViewModel(private val repository: ITaskDetailRepo): ViewModel() {

    fun saveTask(taskModel: TaskModel) = liveData(Dispatchers.IO){
        emit(Resource.Loading())
        try {
            emit(repository.insertNewTask(taskModel))
        }catch (e: Exception){
            emit(Resource.Failure(e))
            Log.e("VM-NEW-TASK", e.toString())
        }
    }

    fun getTaskById(taskId: String) = liveData(Dispatchers.IO) {
        emit(Resource.Loading())
        try {
            emit(repository.getTaskById(taskId))
        }catch (e: Exception){
            emit(Resource.Failure(e))
            Log.e("VM-GET-TASK", e.toString())
        }
    }

}

class TaskDetailViewModelFactory(private val repository: ITaskDetailRepo): ViewModelProvider.Factory{
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return modelClass.getConstructor(ITaskDetailRepo::class.java).newInstance(repository)
    }
}