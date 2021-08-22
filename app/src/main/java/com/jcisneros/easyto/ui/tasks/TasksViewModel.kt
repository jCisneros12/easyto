package com.jcisneros.easyto.ui.tasks

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.liveData
import com.jcisneros.easyto.domain.repository.tasks.ITasksRepo
import com.jcisneros.easyto.utils.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect

class TasksViewModel(private val repository: ITasksRepo): ViewModel() {

    //list with tasks
    val taskList = liveData(Dispatchers.IO) {
        emit(Resource.Loading())
        try {
            repository.getAllTasks().collect { tasks ->
                emit(tasks)
            }
        }catch (e: Exception){
            emit(Resource.Failure(e))
            Log.e("ERROR-TASKS", e.toString())
        }
    }

    //for get all task complete
    val taskCompleteList = liveData(Dispatchers.IO) {
        emit(Resource.Loading())
        try {
            repository.getTaskComplete().collect { tasks ->
                emit(tasks)
            }
        }catch (e: Exception){
            emit(Resource.Failure(e))
            Log.e("ERROR-TASKS", e.toString())
        }
    }

    //for get all task incomplete
    val taskIncompleteList = liveData(Dispatchers.IO) {
        emit(Resource.Loading())
        try {
            repository.getTaskIncomplete().collect { tasks ->
                emit(tasks)
            }
        }catch (e: Exception){
            emit(Resource.Failure(e))
            Log.e("ERROR-TASKS", e.toString())
        }
    }

    //method for change "task state" to complete or not
    fun completeTask(taskId: String, isComplete: Boolean) = liveData(Dispatchers.IO) {
        emit(Resource.Loading())
        try {
            emit(repository.updateTaskComplete(taskId, isComplete))
        }catch (e: Exception){
            emit(Resource.Failure(e))
            Log.e("ERROR-TASK-COMPLETE", e.toString())
        }
    }

}

class TasksViewModelFactory(private val repository: ITasksRepo): ViewModelProvider.Factory{
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return modelClass.getConstructor(ITasksRepo::class.java).newInstance(repository)
    }
}