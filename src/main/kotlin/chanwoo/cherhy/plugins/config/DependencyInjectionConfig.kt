package chanwoo.cherhy.plugins.config

import chanwoo.cherhy.plugins.domain.chat.*
import chanwoo.cherhy.plugins.domain.customer.CustomerRepository
import chanwoo.cherhy.plugins.domain.customer.CustomerRepositoryImpl
import chanwoo.cherhy.plugins.domain.customer.CustomerService
import io.ktor.server.application.*
import org.koin.dsl.module
import org.koin.ktor.plugin.Koin

val dependencyInjectionModule = module {
    single<CustomerRepository> { CustomerRepositoryImpl() }
    single<ChatRepository> { ChatRepositoryImpl() }
    single<ChatRoomRepository> { ChatRoomRepositoryImpl() }
    single { CustomerService(get(), get()) }
}

fun Application.configureDependencyInjection() {
    install(Koin) {
        modules(dependencyInjectionModule)
    }
}