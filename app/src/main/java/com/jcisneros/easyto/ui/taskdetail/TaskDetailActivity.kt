package com.jcisneros.easyto.ui.taskdetail

import android.os.Bundle
import android.widget.Toast
import com.jcisneros.easyto.ui.base.BaseTaskDetailActivity


class TaskDetailActivity : BaseTaskDetailActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //save task onClick method
        binding.fabSaveTask.setOnClickListener {
            //TODO: change toast
            Toast.makeText(this, "Task saved", Toast.LENGTH_SHORT).show()
        }


    }


}