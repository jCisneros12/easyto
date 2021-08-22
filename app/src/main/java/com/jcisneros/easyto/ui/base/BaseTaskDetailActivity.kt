package com.jcisneros.easyto.ui.base

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.text.TextUtils
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
import com.bumptech.glide.Glide
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.jcisneros.easyto.R
import com.jcisneros.easyto.data.model.TaskModel
import com.jcisneros.easyto.databinding.ActivityTaskBinding

abstract class BaseTaskDetailActivity : AppCompatActivity() {

    //ViewBinding
    protected lateinit var binding: ActivityTaskBinding

    //image uri
    protected var taskImageUri: Uri? = null

    //task details
    protected lateinit var taskTitle: String
    protected var taskDescription = "No description"
    protected var taskComplete = false
    protected var taskImage = ""

    //for validate if update task
    protected var isNewTask = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTaskBinding.inflate(layoutInflater)
        setContentView(binding.root)
        title = ""

        //image option bottom sheet dialog
        val cardView = findViewById<CardView>(R.id.sheet_bottom_image_options)
        val removeOption = findViewById<LinearLayout>(R.id.dialog_option_quit_image)
        val changeOption = findViewById<LinearLayout>(R.id.dialog_option_change_image)
        val sheetBottomImageOptions = BottomSheetBehavior.from(cardView)
        //sheetBottomImageOptions.state = BottomSheetBehavior.STATE_HIDDEN
        //image onClick method
        binding.imageTask.setOnClickListener {
            if (sheetBottomImageOptions.state == BottomSheetBehavior.STATE_COLLAPSED) {
                sheetBottomImageOptions.state = BottomSheetBehavior.STATE_EXPANDED
            } else {
                sheetBottomImageOptions.state = BottomSheetBehavior.STATE_COLLAPSED
            }
        }

        ///bottom sheet options
        //change image
        changeOption.setOnClickListener {
            selectImage()
            sheetBottomImageOptions.state = BottomSheetBehavior.STATE_COLLAPSED
        }
        //remove image
        removeOption.setOnClickListener {
            binding.imageTask.visibility = View.GONE
            taskImageUri = null
            taskImage = ""
            sheetBottomImageOptions.state = BottomSheetBehavior.STATE_COLLAPSED
        }

        //bottom sheet callbacks
        sheetBottomImageOptions.addBottomSheetCallback(object :
            BottomSheetBehavior.BottomSheetCallback() {

            override fun onStateChanged(view: View, state: Int) {
                when (state) {
                    BottomSheetBehavior.STATE_EXPANDED -> {
                        binding.fabSaveTask.visibility = View.GONE
                    }
                    BottomSheetBehavior.STATE_COLLAPSED -> {
                        binding.fabSaveTask.visibility = View.VISIBLE
                    }
                }
            }
            override fun onSlide(view: View, p1: Float) {
            }
        })
    }

    //Select image from gallery and Handle permissions
    protected fun selectImage() {
        //check Android version
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.M) {
            //check if permission not is granted
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED
            ) {
                requestExternalStoragePermission()
            } else {
                selectImageFromGallery()
            }
        }
    }

    private val startForActivityGallery = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { res ->
        if (res.resultCode == RESULT_OK) {
            val imageUri = res.data?.data
            taskImageUri = imageUri
            if (taskImageUri != null) {
                binding.imageTask.visibility = View.VISIBLE
                binding.imageTask.setImageURI(taskImageUri)
            } else binding.imageTask.visibility = View.GONE
            //binding.imageTask.setImageURI(imageUri)
        }
    }

    private fun selectImageFromGallery() {
        val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI)
        intent.type = "image/*"
        startForActivityGallery.launch(intent)
    }

    protected fun getTaskDetails(): Boolean {
        if (validateFields()) {
            taskTitle = binding.textTaskTittle.text.toString()
            taskDescription = binding.textTaskBody.text.toString()
            return true
        }
        return false
    }

    private fun validateFields(): Boolean {
        if (TextUtils.isEmpty(binding.textTaskTittle.text.toString())) {
            binding.textTaskTittle.error = this.getString(R.string.required_input)
            return false
        }
        return true
    }

    protected fun fillView(taskModel: TaskModel){
        binding.textTaskTittle.setText(taskModel.title)
        binding.textTaskBody.setText(taskModel.description)
        if(taskModel.image!!.isNotEmpty()) {
            taskImage = taskModel.image!!
            binding.imageTask.visibility = View.VISIBLE
            Glide.with(this).load(taskModel.image).into(binding.imageTask)
        }
    }

    //show progress bar
    protected fun showProgressBar() {
        binding.taskProgressBar.visibility = View.VISIBLE
        binding.fabSaveTask.visibility = View.GONE
    }

    protected fun hideProgressBar() {
        binding.taskProgressBar.visibility = View.GONE
        binding.fabSaveTask.visibility = View.VISIBLE
    }

/// override methods

    //create menu options
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_task_details, menu)
        return super.onCreateOptionsMenu(menu)
    }

    //prepare menu options
    override fun onPrepareOptionsMenu(menu: Menu?): Boolean {
        //remove delete option when create new task
        if(isNewTask) menu?.removeItem(R.id.action_delete)
        return super.onPrepareOptionsMenu(menu)
    }



///Handle permission for READ_EXTERNAL_STORAGE

    private fun requestExternalStoragePermission() {
        //user denied permission
        if (ActivityCompat.shouldShowRequestPermissionRationale(
                this, Manifest.permission.READ_EXTERNAL_STORAGE
            )
        ) {
            //request permission
            ActivityCompat.requestPermissions(
                this, arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE), 10
            )
        } else {
            //request permission
            ActivityCompat.requestPermissions(
                this, arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE), 10
            )
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == 10) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                selectImageFromGallery()
            } else {
                Toast.makeText(
                    this, this.getString(R.string.permission_denied),
                    Toast.LENGTH_LONG
                ).show()
            }
        }
    }


}