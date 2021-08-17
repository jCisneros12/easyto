package com.jcisneros.easyto.ui.tasks

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.jcisneros.easyto.data.model.TaskModel
import com.jcisneros.easyto.databinding.ItemTaskBinding

class TasksAdapter(
    private val context: Context
) : RecyclerView.Adapter<TasksAdapter.TasksViewHolder>() {

    //tasks list
    private var taskList: List<TaskModel> = arrayListOf()

    fun setListData(data: List<TaskModel>){
        taskList = data
    }

    //create item for recycler list
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TasksViewHolder {
        val itemBinding = ItemTaskBinding.inflate(
            LayoutInflater.from(context), parent, false
        )
        return TasksViewHolder(itemBinding)
    }

    //bind item with Task data
    override fun onBindViewHolder(holder: TasksViewHolder, position: Int) {
        val task: TaskModel = taskList[position]
        holder.bindView(task)
    }

    override fun getItemCount(): Int {
        return if (taskList.isNotEmpty()) taskList.size
        else 0
    }

    inner class TasksViewHolder(
        private val binding: ItemTaskBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bindView(task: TaskModel) {
            binding.checkTask.text = task.title
            binding.checkTask.isChecked = task.isComplete
//            if(task.image!=null){
//                binding.imageTask.visibility = View.VISIBLE
//                binding.imageTask.setImageURI(task.image)
//            }
        }
    }
}