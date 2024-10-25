package com.jm.data.dto

import com.jm.data.service.ServiceError

sealed class NetworkResponse<T>(val data: T?, val error: ServiceError?) {
    class Success<T>(data: T) : NetworkResponse<T>(data, null)
    class Error<T>(error: ServiceError) : NetworkResponse<T>(null, error)
}