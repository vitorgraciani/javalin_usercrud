package br.com.estudo.controller

import br.com.estudo.domain.User
import br.com.estudo.domain.UserResult
import br.com.estudo.service.UserService
import io.javalin.apibuilder.CrudHandler
import io.javalin.http.BadRequestResponse
import io.javalin.http.Context
import org.eclipse.jetty.http.HttpStatus

class UserController(private val userService : UserService) : CrudHandler {

    override fun create(ctx: Context) {
        ctx.bodyValidator<User>()
            .check({ !it.email?.isNullOrBlank() }, "E-mail is required")
            .check({ !it.username?.isNullOrBlank() }, "Username is required")
            .check({ !it.password?.isNullOrBlank() }, "password is required")
            when(val result = userService.create(ctx.body<User>())){
                is UserResult.Success -> ctx.json(result)
                is UserResult.Error -> ctx.json(result.message).status(HttpStatus.BAD_REQUEST_400)
            }
    }

    override fun delete(ctx: Context, resourceId: String) {

        ctx.json(userService.delete(resourceId))
    }

    override fun getAll(ctx: Context) {
        ctx.json(userService.getAllUsers())
    }

    override fun getOne(ctx: Context, resourceId: String) {
        userService.getUser(resourceId).apply{
            ctx.json(this ?: "User with $resourceId not exist")
        }
    }

    override fun update(ctx: Context, resourceId: String) {
        userService.updateUser(ctx.bodyAsClass(User::class.java), resourceId).apply{
            ctx.json(this)
        }
    }

}