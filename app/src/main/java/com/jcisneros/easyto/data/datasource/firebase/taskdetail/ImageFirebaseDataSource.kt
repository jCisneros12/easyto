package com.jcisneros.easyto.data.datasource.firebase.taskdetail

import android.net.Uri
import com.google.firebase.storage.FirebaseStorage
import com.jcisneros.tbradminapp.utils.ImageManager
import kotlinx.coroutines.tasks.await

class ImageFirebaseDataSource {

    //upload image (Firebase Storage) and return url
    suspend fun  uploadImage(imageUri: Uri): String{
        //url of image upload
        var imageUrl: String = ""
        //is true when upload file successful
        var uploadSuccess = false

        ///Upload image to Cloud Storage
        val imageName = ImageManager.imageName()
        val storageReference = FirebaseStorage.getInstance().getReference("taskImage/$imageName")
        val uploadResult = storageReference.putFile(imageUri).await()

        if(uploadResult.task.isSuccessful){
            val requestUpload = storageReference.downloadUrl.await()
            if(requestUpload!=null){
                imageUrl = java.lang.String.valueOf(requestUpload)
                uploadSuccess = true
            }
        }

        return if (uploadSuccess) imageUrl
        else return ""
    }

}