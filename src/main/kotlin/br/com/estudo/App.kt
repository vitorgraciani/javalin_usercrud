package br.com.estudo

import br.com.estudo.infrastructure.configuration.AppConfig
import br.com.estudo.infrastructure.configuration.ModuleConfig
import org.koin.core.context.startKoin

fun main(){
    startKoin {
        modules(ModuleConfig.allModules)
    }.fileProperties()
    AppConfig().setup().start(7000)
}

