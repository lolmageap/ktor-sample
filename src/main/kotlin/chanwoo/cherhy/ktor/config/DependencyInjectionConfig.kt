package chanwoo.cherhy.ktor.config

import chanwoo.cherhy.ktor.domain.chat.*
import chanwoo.cherhy.ktor.domain.customer.CustomerRepository
import chanwoo.cherhy.ktor.domain.customer.CustomerRepositoryImpl
import chanwoo.cherhy.ktor.domain.customer.CustomerService
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