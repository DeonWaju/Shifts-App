package com.example.shiftstestapplication.utils

/**
 * Created by Gideon Olarewaju on 21/11/2022.
 */
sealed class Resource<T>(val data: T? = null, val message: String? = null){
    class Success<T>(data: T): Resource<T>(data)
    class Error<T>(message: String, data: T? = null): Resource<T>(data)
    class Loading<T>(data: T? = null): Resource<T>(data)
}
