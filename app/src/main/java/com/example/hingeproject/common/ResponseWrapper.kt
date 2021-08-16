package com.example.hingeproject.common

sealed class ResponseWrapper<out T>

data class SuccessResponse<out T>(val data: T): ResponseWrapper<T>()
data class ErrorResponse(val code: Int? = null, val error: String): ResponseWrapper<Nothing>()
object UnknownError: ResponseWrapper<Nothing>()
object NetworkError: ResponseWrapper<Nothing>()