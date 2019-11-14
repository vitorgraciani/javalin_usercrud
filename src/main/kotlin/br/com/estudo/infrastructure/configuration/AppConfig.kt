package br.com.estudo.infrastructure.configuration

import com.fasterxml.jackson.databind.SerializationFeature
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import io.javalin.Javalin
import io.javalin.plugin.json.JavalinJackson
import org.eclipse.jetty.http.HttpStatus
import org.koin.core.KoinComponent
import org.koin.core.context.stopKoin
import org.koin.core.inject
import java.text.SimpleDateFormat

class AppConfig : KoinComponent {

    private val router : Router by inject()
    private val exceptionHandler : ExceptionHandler by inject()
    fun setup(): Javalin {

        return Javalin.create().also { app ->
            this.configureMapper()
            app.events {it.serverStopping{ stopKoin() } }
            router.register(app)
            exceptionHandler.register(app)
            app.get("/"){ctx ->ctx.result(HttpStatus.OK_200.toString())}
        }
    }

    private fun configureMapper() {
        val dateFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
        JavalinJackson.configure(jacksonObjectMapper()
            .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)
            .setDateFormat(dateFormat)
            .configure(SerializationFeature.WRITE_DATES_WITH_ZONE_ID, true)
        )
    }
}