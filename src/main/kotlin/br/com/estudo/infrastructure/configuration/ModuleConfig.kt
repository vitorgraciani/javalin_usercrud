package br.com.estudo.infrastructure.configuration

import br.com.estudo.controller.UserController
import br.com.estudo.infrastructure.repository.UserRepository
import br.com.estudo.service.UserService
import org.koin.dsl.module

object ModuleConfig {

    private val configModule = module {
        single { AppConfig() }
        single {  DbConfig(getProperty("jdbc_url"), getProperty("db_username"), getProperty("db_password")).getDataSource()}
        single { ExceptionHandler() }
        single { Router(get()) }
    }

     private val userModule =  module {
        single {UserController(get()) }
        single { UserService (get()) }
        single { UserRepository(get()) }
    }

    internal val allModules = listOf(configModule, userModule)
}