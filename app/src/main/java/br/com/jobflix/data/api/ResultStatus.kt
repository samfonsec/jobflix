package br.com.jobflix.data.api

sealed class ResultStatus<out T : Any> {
    class Success<out T : Any>(val data: T) : ResultStatus<T>()
    class Error(val exception: Throwable) : ResultStatus<Nothing>()
}