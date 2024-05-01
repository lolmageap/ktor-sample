package chanwoo.cherhy.ktor.config

import com.zaxxer.hikari.HikariConfig
import com.zaxxer.hikari.HikariDataSource
import io.ktor.server.application.*
import org.jetbrains.exposed.sql.Database
import chanwoo.cherhy.ktor.util.ApplicationConfigUtils.getDataSource

fun Application.configureDatabase() {
    val hikari = HikariDataSource(
        HikariConfig().apply {
            jdbcUrl = getDataSource("url")
            username = getDataSource("username")
            password = getDataSource("password")
            driverClassName = getDataSource("driver-class-name")
            maximumPoolSize = getDataSource("max-pool-size").toInt()
            isAutoCommit = false
            transactionIsolation = getDataSource("isolation-level")
            validate()
        }
    )
    Database.connect(hikari)
}