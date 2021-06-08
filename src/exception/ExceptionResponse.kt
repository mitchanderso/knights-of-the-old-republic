package com.ld.learning.exception

import io.ktor.http.*

data class ExceptionResponse (val message: String, val statusCode: HttpStatusCode) {
}