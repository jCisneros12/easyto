package com.jcisneros.easyto.ui.taskdetail

import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import com.jcisneros.easyto.R
import com.jcisneros.easyto.data.datasource.firebase.taskdetail.TaskDetailFirebaseDataSource
import com.jcisneros.easyto.data.datasource.local.room.database.EasytoRoomDataBase
import com.jcisneros.easyto.data.datasource.local.taskdetail.TaskDetailLocalDataSource
import com.jcisneros.easyto.data.model.TaskModel
import com.jcisneros.easyto.domain.repository.taskdetail.TaskDetailRepoImpl
import com.jcisneros.easyto.ui.base.BaseTaskDetailActivity
import com.jcisneros.easyto.utils.Resource

/*
* All ui logic is in BaseTaskDetailActivity
* */

class TaskDetailActivity : BaseTaskDetailActivity() {

    //ViewModel
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

    //for validate if update task
    private var isNewTask = true
    private lateinit var taskId: String


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //check if put data (task id)
        if (intent.extras!=null){
            isNewTask = false
            //fill task detail activity from task data in local db
            val idFromIntent = intent?.getStringExtra(this.getString(R.string.task_id))
            fillActivityData(idFromIntent!!)
        }

        //save task onClick method
        binding.fabSaveTask.setOnClickListener {
            //save task
            saveTask()
        }

    }

    private fun saveTask() {
        if (getTaskDetails()) {
            val taskModel = TaskModel(
                taskId = "tempId",
                title = taskTitle,
                description = taskDescription,
                isComplete = taskComplete,
                image = "",
                imageUri = taskImageUri
            )

            viewModel.saveTask(taskModel).observe(this, {
                when (it) {
                    is Resource.Loading -> {
                        showProgressBar()
                    }
                    is Resource.Success -> {
                        hideProgressBar()
                        if (it.data) {
                            Toast.makeText(this, this.getString(R.string.save_success),
                                Toast.LENGTH_SHORT).show()
                            finish()
                        }else{
                            Toast.makeText(this, this.getString(R.string.save_failure),
                                Toast.LENGTH_SHORT).show()
                            finish()
                        }
                    }

                    is Resource.Failure -> {
                        Toast.makeText(this, "Ocurrió un problema", Toast.LENGTH_SHORT).show()
                    }
                }
            })
        }
    }

    private fun fillActivityData(idTask: String){
        //get id of task
        taskId = idTask
        //get task data from VW
        viewModel.getTaskById(taskId).observe(this, { task ->
            when(task){
                is Resource.Loading ->{
                    showProgressBar()
                }
                is Resource.Success ->{
                    hideProgressBar()
                    fillView(task.data)
                }
                is Resource.Failure ->{}
            }
        })

    }

    private fun getTaskById(idTask: String){
        //...
    }
}