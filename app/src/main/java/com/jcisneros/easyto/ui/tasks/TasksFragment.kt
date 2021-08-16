package com.jcisneros.easyto.ui.tasks

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.jcisneros.easyto.data.datasource.local.tasks.TasksLocalDataSource
import com.jcisneros.easyto.databinding.FragmentTasksBinding
import com.jcisneros.easyto.domain.repository.tasks.TasksRepoImpl

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

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentTasksBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

//        binding.buttonFirst.setOnClickListener {
//            findNavController().navigate(R.id.action_FirstFragment_to_SecondFragment)
//        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}