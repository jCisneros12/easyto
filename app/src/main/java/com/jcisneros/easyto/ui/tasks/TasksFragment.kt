package com.jcisneros.easyto.ui.tasks

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.jcisneros.easyto.data.datasource.local.room.database.EasytoRoomDataBase
import com.jcisneros.easyto.data.datasource.local.tasks.TasksLocalDataSource
import com.jcisneros.easyto.data.datasource.firebase.tasks.TasksFirebaseDataSource
import com.jcisneros.easyto.databinding.FragmentTasksBinding
import com.jcisneros.easyto.domain.repository.tasks.TasksRepoImpl
import com.jcisneros.easyto.utils.Resource


class TasksFragment : Fragment() {

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
                TasksFirebaseDataSource(requireContext())
            )
        )
    }


    //Recycler view adapter
    private val adapter: TasksAdapter by lazy {
        TasksAdapter(requireContext())
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentTasksBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //adapter
        binding.recyclerTasks.layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerTasks.adapter = adapter

        observeTasks()

    }

    private fun observeTasks() {
        viewModel.taskList.observe(viewLifecycleOwner, { tasks ->
            when (tasks) {
                is Resource.Loading -> {
                    //TODO: show ui for loading
                    binding.tasksProgressBar.visibility = View.VISIBLE
                }
                is Resource.Success -> {
                    //TODO: show or not UI when list is empty
                    binding.tasksProgressBar.visibility = View.GONE
                    adapter.setListData(tasks.data)
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

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}