package com.jcisneros.tbradminapp.utils

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
    }

}