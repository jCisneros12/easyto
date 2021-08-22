package com.jcisneros.easyto.ui.tasks

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.jcisneros.easyto.data.model.TaskModel
import com.jcisneros.easyto.databinding.ItemTaskBinding

class TasksAdapter(
    private val context: Context,
    private val itemClickListener: OnCategoryClickListener
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

    //interface for implements onClick methods
    interface OnCategoryClickListener{
        fun onEditClick(taskModel: TaskModel)
        fun onCompleteTask(task: TaskModel)
        //add more methods here...
    }

    inner class TasksViewHolder(
        private val binding: ItemTaskBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bindView(task: TaskModel) {
            //onClick listeners
            binding.textTaskTittleCard.setOnClickListener { itemClickListener.onEditClick(task) }
            binding.checkTask.setOnClickListener { itemClickListener.onCompleteTask(task) }
            //set data to item view
            binding.textTaskTittleCard.text = task.title
            binding.checkTask.isChecked = task.isComplete
            if(task.image!!.isNotEmpty()){
                Glide.with(context).load(task.image).into(binding.imageTask)
                binding.imageTask.visibility = View.VISIBLE
            }else binding.imageTask.visibility = View.GONE
        }
    }
}