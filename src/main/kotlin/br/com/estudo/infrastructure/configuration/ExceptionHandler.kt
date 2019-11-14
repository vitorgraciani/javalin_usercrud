package br.com.estudo.infrastructure.configuration

import io.javalin.Javalin
import io.javalin.http.BadRequestResponse
import org.eclipse.jetty.http.HttpStatus
import java.lang.NullPointerException

internal data class ErrorResponse(val errors: Map<String, List<String?>>)

class ExceptionHandler {

    fun register(app : Javalin){
        app.exception(NullPointerException::class.java){ e, ctx ->
            val error = ErrorResponse(mapOf("Erro inesperado" to listOf(e.message ?: "Erro inesperado")))
            ctx.json(error).status(HttpStatus.INTERNAL_SERVER_ERROR_500)
        }
        app.exception(BadRequestResponse::class.java){ e, ctx ->
            val error = ErrorResponse(mapOf("Requisicao Invalida" to listOf(e.message ?: "Erro inesperado")))
            ctx.json(error).status(HttpStatus.BAD_REQUEST_400)
        }
        app.exception(Exception::class.java){ e, ctx ->
            println(e)
            val error = ErrorResponse(mapOf("Exception generica" to listOf(e.message ?: "Erro inesperado")))
            ctx.json(error).status(HttpStatus.INTERNAL_SERVER_ERROR_500)
        }
    }
}