package com.jcisneros.tbradminapp.utils

import android.content.Context
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import coil.ImageLoader
import coil.request.ImageRequest
import coil.request.SuccessResult
import com.jcisneros.easyto.EasytoApplication
import java.text.SimpleDateFormat
import java.util.*

class ImageManager {
    companion object {
        //create a name of image
        fun imageName(): String {
            val formatter = SimpleDateFormat("yyyy_MM_dd_HH_mm_ss", Locale.getDefault())
            val now = Date()
            return formatter.format(now)
        }

        //convert image url to bitmap using COIL
        suspend fun bitmapToUrl(imageUrl: String, context: Context): Bitmap{
            val loading = ImageLoader(context)
            val request = ImageRequest.Builder(context).data(imageUrl).build()
            val result = (loading.execute(request) as SuccessResult).drawable
            return (result as BitmapDrawable).bitmap
        }
    }

}