package com.jcisneros.easyto.utils

import java.lang.Exception

/*
* Class for handling any type of object
* */

sealed class Resource<out T>{
    class Loading<out T> : Resource<T>()
    data class Success<out T>(val data: T) : Resource<T>()
    data class Failure<out T>(val exception: Exception) : Resource<T>()
}