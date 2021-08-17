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
import com.jcisneros.easyto.data.datasource.local.tasks.TasksLocalDataSource
import com.jcisneros.easyto.databinding.FragmentTasksBinding
import com.jcisneros.easyto.domain.repository.tasks.TasksRepoImpl
import com.jcisneros.easyto.utils.Resource

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class TasksFragment : Fragment() {

    //ViewBinding
    private var _binding: FragmentTasksBinding? = null
    private val binding get() = _binding!!

    //ViewModel
    private val viewModel by viewModels<TasksViewModel>{
        TasksViewModelFactory(TasksRepoImpl(TasksLocalDataSource()))
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

//        binding.buttonFirst.setOnClickListener {
//            findNavController().navigate(R.id.action_FirstFragment_to_SecondFragment)
//        }
    }

    private fun observeTasks(){
        viewModel.allTasks.observe(viewLifecycleOwner, { tasks ->
            when(tasks){
                is Resource.Loading ->{
                    //TODO: show ui for loading
                }
                is Resource.Success -> {
                    //TODO: show or not UI when list is empty
                    adapter.setListData(tasks.data)
                }
                is Resource.Failure ->{
                    //TODO: create extension function for Toast
                    Toast.makeText(requireContext(), "Ocurrio un problema", Toast.LENGTH_SHORT).show()
                    Log.e("PRODUCT", "ocurrio un error de tipo: CATEGORY-ERR")
                }
            }
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}