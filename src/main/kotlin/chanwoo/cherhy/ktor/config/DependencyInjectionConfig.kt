package chanwoo.cherhy.ktor.config

import chanwoo.cherhy.ktor.domain.chat.*
import chanwoo.cherhy.ktor.domain.customer.CustomerRepository
import chanwoo.cherhy.ktor.domain.customer.CustomerRepositoryImpl
import chanwoo.cherhy.ktor.domain.customer.CustomerService
import chanwoo.cherhy.ktor.usecase.CreateChatRoomUseCase
import chanwoo.cherhy.ktor.usecase.LoginUseCase
import chanwoo.cherhy.ktor.usecase.SignupUseCase
import chanwoo.cherhy.ktor.util.JwtManager
import io.ktor.server.application.*
import org.koin.dsl.module
import org.koin.ktor.plugin.Koin

val dependencyInjectionModule = module {
    single { CreateChatRoomUseCase(get(), get(), get()) }
    single { LoginUseCase(get(), get(), get()) }
    single { SignupUseCase(get(), get()) }

    single<CustomerRepository> { CustomerRepositoryImpl() }
    single<ChatRepository> { ChatRepositoryImpl() }
    single<ChatRoomRepository> { ChatRoomRepositoryImpl() }

    single { CustomerService(get()) }
    single { ChatRoomService(get()) }

    single { JwtManager() }
}

fun Application.configureDependencyInjection() {
    install(Koin) {
        modules(dependencyInjectionModule)
    }
}