package com.jcisneros.easyto.ui.taskdetail

import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import com.jcisneros.easyto.data.datasource.firebase.taskdetail.TaskDetailFirebaseDataSource
import com.jcisneros.easyto.data.datasource.local.room.database.EasytoRoomDataBase
import com.jcisneros.easyto.data.datasource.local.taskdetail.TaskDetailLocalDataSource
import com.jcisneros.easyto.data.model.TaskModel
import com.jcisneros.easyto.domain.repository.taskdetail.TaskDetailRepoImpl
import com.jcisneros.easyto.ui.base.BaseTaskDetailActivity
import com.jcisneros.easyto.utils.Resource


class TaskDetailActivity : BaseTaskDetailActivity() {

    private val viewModel by viewModels<TaskDetailViewModel> {
        TaskDetailViewModelFactory(
            TaskDetailRepoImpl(
                TaskDetailFirebaseDataSource(),
                TaskDetailLocalDataSource(
                    EasytoRoomDataBase.getDataBase(this.applicationContext).taskDao()
                )
            )
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //save task onClick method
        binding.fabSaveTask.setOnClickListener {
            //save task
            saveTask()
        }

    }

    private fun saveTask(){
        if (getTaskDetails()){
            val taskModel = TaskModel(
                taskId = "tempId",
                title = taskTitle,
                description = taskDescription,
                isComplete = taskComplete,
                image = "",
                imageUri = taskImageUri)

            viewModel.saveTask(taskModel).observe(this, {
                when(it){
                    is Resource.Loading ->{
                        //TODO: show any loading ui
                    }
                    is Resource.Success ->{
                        //TODO: change toast
                        Toast.makeText(this, "Guardado", Toast.LENGTH_SHORT).show()
                        finish()
                    }
                    is Resource.Failure ->{
                        Toast.makeText(this, "Ocurrió un problema", Toast.LENGTH_SHORT).show()
                    }
                }
            })
        }

    }
}