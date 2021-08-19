package com.jcisneros.easyto.ui.taskdetail

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.LinearLayout
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.jcisneros.easyto.R
import com.jcisneros.easyto.base.BaseTaskDetailActivity
import com.jcisneros.easyto.databinding.ActivityTaskBinding
import com.jcisneros.easyto.databinding.SheetBottomImageOptionsBinding


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