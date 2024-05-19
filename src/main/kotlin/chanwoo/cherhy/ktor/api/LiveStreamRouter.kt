package chanwoo.cherhy.ktor.api

import chanwoo.cherhy.ktor.domain.chat.Connection
import chanwoo.cherhy.ktor.domain.customer.CustomerId
import chanwoo.cherhy.ktor.domain.livestream.LiveStreamService
import chanwoo.cherhy.ktor.util.EndPoint.LIVESTREAM.CALLED_LIVE_STREAM
import chanwoo.cherhy.ktor.util.EndPoint.LIVESTREAM.CALL_LIVE_STREAM
import chanwoo.cherhy.ktor.util.extension.customerId
import chanwoo.cherhy.ktor.util.extension.jwt
import chanwoo.cherhy.ktor.util.extension.pathVariable
import chanwoo.cherhy.ktor.util.property.SecurityProperty.AUTHORITY
import io.ktor.server.auth.*
import io.ktor.server.routing.*
import io.ktor.server.websocket.*
import io.ktor.websocket.*
import mu.KotlinLogging
import org.koin.ktor.ext.inject
import java.util.*
import java.util.concurrent.ConcurrentHashMap

private val connectionFactoryMap =
    Collections.synchronizedMap(
        ConcurrentHashMap<CustomerId, MutableList<Connection>>()
    )
private val logger = KotlinLogging.logger { }

fun Route.liveStream() {
    val liveStreamService: LiveStreamService by inject()

    authenticate(AUTHORITY) {
        webSocket(CALL_LIVE_STREAM) {
            val callCustomerId = call.jwt.customerId
            val calledCustomerId = call.pathVariable.customerId

            // TODO: Implement allowed check logic
            val connection = createConnection(callCustomerId, calledCustomerId)

            try {
                for (frame in incoming) {
                    frame as? Frame.Text ?: continue
                    val receiveMedia = frame.readBytes()
                    TODO("Implement media processing logic")
                }
            } catch (e: Exception) {
                logger.error { e.localizedMessage }
            } finally {
                TODO("Implement disconnect logic")
            }
        }
    }

    authenticate(AUTHORITY) {
        webSocket(CALLED_LIVE_STREAM) {
            val customerId = call.jwt.customerId
            val liveStreamId = call.pathVariable.liveStreamId

        }
    }
}


private fun DefaultWebSocketServerSession.createConnection(
    callCustomerId: CustomerId,
    calledCustomerId: CustomerId,
): Connection {
    val newConnection = Connection(this, calledCustomerId)
    connectionFactoryMap.getOrPut(callCustomerId) { mutableListOf() }.add(newConnection)
    return newConnection
}