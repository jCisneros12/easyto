package com.jcisneros.easyto.ui.tasks

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.liveData
import com.jcisneros.easyto.domain.repository.tasks.ITasksRepo
import com.jcisneros.easyto.utils.Resource
import kotlinx.coroutines.Dispatchers

class TasksViewModel(private val repository: ITasksRepo): ViewModel() {

    val allTasks = liveData(Dispatchers.IO) {
        emit(Resource.Loading())
        try{
            emit(repository.getAllTask())
        }catch (e: Exception){
            emit(Resource.Failure(e))
            Log.e("ALL-TASK", e.toString())
        }
    }

}

class TasksViewModelFactory(private val repository: ITasksRepo): ViewModelProvider.Factory{
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return modelClass.getConstructor(ITasksRepo::class.java).newInstance(repository)
    }
}