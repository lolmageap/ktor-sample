package chanwoo.cherhy.plugins.config

import com.zaxxer.hikari.HikariConfig
import com.zaxxer.hikari.HikariDataSource
import io.ktor.server.application.*
import org.jetbrains.exposed.sql.Database

fun Application.configureDatabase() {
    val hikari = HikariDataSource(
        HikariConfig().apply {
            jdbcUrl = environment.config.property("ktor.database.url").getString()
            username = environment.config.property("ktor.database.username").getString()
            password = environment.config.property("ktor.database.password").getString()
            driverClassName = environment.config.property("ktor.database.driverClassName").getString()
            maximumPoolSize = environment.config.property("ktor.database.maxPoolSize").getString().toInt()
            isAutoCommit = false
            transactionIsolation = environment.config.property("ktor.database.isolationLevel").getString()
            validate()
        }
    )
    Database.connect(hikari)
}