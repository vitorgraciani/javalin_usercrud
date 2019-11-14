package br.com.estudo.domain

sealed class UserResult<out T : Any> {
    data class Success<out T : Any>(val user : T) : UserResult<T>()
    data class Error(val message : String, val cause : Exception? = null) : UserResult<Nothing>()
}