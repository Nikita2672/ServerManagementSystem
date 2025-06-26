package org.example.servermanagementsystem.advise

import org.example.servermanagementsystem.dto.response.ErrorResponse
import org.example.servermanagementsystem.exception.AbstractServerManageException
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler
import java.lang.reflect.InvocationTargetException

@ControllerAdvice
class GlobalExceptionHandler: ResponseEntityExceptionHandler() {

    @ExceptionHandler(AbstractServerManageException::class)
    fun handleCustomException(ex: AbstractServerManageException, model: Model): ResponseEntity<ErrorResponse> {
        val httpStatus = getHttpStatusFromException(ex)
        return ResponseEntity(ErrorResponse(message = ex.message), httpStatus)
    }

    private fun getHttpStatusFromException(ex: Throwable): HttpStatus {
        return try {
            val responseStatusAnnotation = ex.javaClass.getAnnotation(ResponseStatus::class.java)
            responseStatusAnnotation?.value ?: HttpStatus.INTERNAL_SERVER_ERROR
        } catch (e: NoSuchMethodException) {
            HttpStatus.INTERNAL_SERVER_ERROR
        } catch (e: InvocationTargetException) {
            HttpStatus.INTERNAL_SERVER_ERROR
        }
    }
}