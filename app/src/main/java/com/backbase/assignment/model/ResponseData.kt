package com.backbase.assignment.model

import java.lang.Exception

sealed class ResponseData {
    data class Success(val data: Any) : ResponseData()
    data class Error(val error: Exception) : ResponseData()
}