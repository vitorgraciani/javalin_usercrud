package br.com.estudo.infrastructure.configuration

import br.com.estudo.controller.UserController
import io.javalin.Javalin
import io.javalin.apibuilder.ApiBuilder.crud
import io.javalin.apibuilder.ApiBuilder.path

class Router (private val userController : UserController) {

    fun register(app : Javalin){
        app.routes {
            crud("user/:user-id", userController)
        }
    }
}