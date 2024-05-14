package chanwoo.cherhy.ktor.config

import com.zaxxer.hikari.HikariConfig
import com.zaxxer.hikari.HikariDataSource
import io.ktor.server.application.*
import org.jetbrains.exposed.sql.Database
import chanwoo.cherhy.ktor.util.ApplicationConfigUtils.getDataSource
import chanwoo.cherhy.ktor.util.property.DataSourceProperty.DRIVER_CLASS_NAME
import chanwoo.cherhy.ktor.util.property.DataSourceProperty.ISOLATION_LEVEL
import chanwoo.cherhy.ktor.util.property.DataSourceProperty.MAX_POOL_SIZE
import chanwoo.cherhy.ktor.util.property.DataSourceProperty.PASSWORD
import chanwoo.cherhy.ktor.util.property.DataSourceProperty.USERNAME
import chanwoo.cherhy.ktor.util.property.DataSourceProperty.URL

fun Application.configureDatabase() {
    val hikari = HikariDataSource(
        HikariConfig().apply {
            jdbcUrl = getDataSource(URL)
            username = getDataSource(USERNAME)
            password = getDataSource(PASSWORD)
            driverClassName = getDataSource(DRIVER_CLASS_NAME)
            maximumPoolSize = getDataSource(MAX_POOL_SIZE).toInt()
            isAutoCommit = false
            transactionIsolation = getDataSource(ISOLATION_LEVEL)
            validate()
        }
    )
    Database.connect(hikari)
}