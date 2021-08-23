package com.jcisneros.easyto.ui.tasks

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.*
import androidx.fragment.app.Fragment
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.jcisneros.easyto.R
import com.jcisneros.easyto.data.datasource.local.room.database.EasytoRoomDataBase
import com.jcisneros.easyto.data.datasource.local.tasks.TasksLocalDataSource
import com.jcisneros.easyto.data.datasource.firebase.tasks.TasksFirebaseDataSource
import com.jcisneros.easyto.data.model.TaskModel
import com.jcisneros.easyto.databinding.FragmentTasksBinding
import com.jcisneros.easyto.domain.repository.tasks.TasksRepoImpl
import com.jcisneros.easyto.ui.taskdetail.TaskDetailActivity
import com.jcisneros.easyto.utils.Resource


class TasksFragment : Fragment(), TasksAdapter.OnCategoryClickListener {

    //ViewBinding
    private var _binding: FragmentTasksBinding? = null
    private val binding get() = _binding!!

    //ViewModel
    private val viewModel by viewModels<TasksViewModel> {
        TasksViewModelFactory(
            TasksRepoImpl(
                TasksLocalDataSource(
                    EasytoRoomDataBase.getDataBase(requireContext().applicationContext).taskDao()
                ),
                TasksFirebaseDataSource()
            )
        )
    }

    //Recycler view adapter
    private val adapter: TasksAdapter by lazy {
        TasksAdapter(requireContext(), this)
    }

    private var tasksList = listOf<TaskModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentTasksBinding.inflate(inflater, container, false)
        setHasOptionsMenu(true)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //adapter
        binding.recyclerTasks.layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerTasks.adapter = adapter

        //observe task list
        observeTasks()

        //set search task method
        binding.editTxtSearch.addTextChangedListener(
            object: TextWatcher {
                override fun afterTextChanged(s: Editable?) {
                    searchTask(s.toString())
                }
                override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
            }
        )

    }

    private fun observeTasks() {
        viewModel.taskList.observe(viewLifecycleOwner, { tasks ->
            when (tasks) {
                is Resource.Loading -> {
                    binding.tasksProgressBar.visibility = View.VISIBLE
                }
                is Resource.Success -> {
                    binding.tasksProgressBar.visibility = View.GONE
                    tasksList = tasks.data
                    adapter.setListData(tasksList)
                    if (tasks.data.isEmpty()) binding.txtEmptyTasks.visibility = View.VISIBLE
                    else binding.txtEmptyTasks.visibility = View.GONE
                    adapter.notifyDataSetChanged()
                }
                is Resource.Failure -> {
                    //TODO: create extension function for Toast
                    Toast.makeText(requireContext(), "Ocurrio un problema", Toast.LENGTH_SHORT)
                        .show()
                    Log.e("TASKS-ERR", "ocurrio un error: ${tasks.exception}")
                }
            }
        })
    }

    private fun searchTask(title: String){
        val searchLit = mutableListOf<TaskModel>()
        if(tasksList.isNotEmpty()){
            tasksList.forEach { task ->
                if(task.title!!.lowercase().contains(title.lowercase()))
                    searchLit.add(task)
            }
            adapter.setListData(searchLit)
            adapter.notifyDataSetChanged()
        }
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    /// Recyclerview methods
    //onClick method for recycler items
    override fun onEditClick(taskModel: TaskModel) {
        val intent = Intent(requireContext(), TaskDetailActivity::class.java)
        //put Id of task to get Task data from db
        intent.putExtra(requireContext().getString(R.string.task_id), taskModel.taskId)
        startActivity(intent)
    }

    //when click in checkbox, task is complete or vice versa
    override fun onCompleteTask(task: TaskModel) {
        viewModel.completeTask(task.taskId, !task.isComplete).observe(requireActivity(), {
            when (it) {
                is Resource.Failure -> {
                    Toast.makeText(
                        requireContext(), requireContext().getString(R.string.error_msg),
                        Toast.LENGTH_SHORT).show()
                }
            }
        })
    }

    ///Menu options for app bar

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_main, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_task_complete ->{
                //getTaskComplete()
            }
            R.id.action_tasks_incomplete ->{
                //getTaskIncomplete()
            }
        }
        return super.onOptionsItemSelected(item)
    }

}