package chanwoo.cherhy.ktor.domain.livestream

import chanwoo.cherhy.ktor.domain.customer.CustomerId
import chanwoo.cherhy.ktor.domain.customer.Customers
import chanwoo.cherhy.ktor.util.model.BaseEntity
import chanwoo.cherhy.ktor.util.model.BaseEntityClass
import chanwoo.cherhy.ktor.util.model.BaseLongIdTable
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.sql.Column

typealias LiveStreamId = Long

object LiveStreams: BaseLongIdTable("live_stream", "id") {
    val owner: Column<EntityID<CustomerId>> = reference("customer_id", Customers)
}

class LiveStream(id: EntityID<LiveStreamId>): BaseEntity(
    id = id,
    table = LiveStreams,
) {
    var owner by LiveStreams.owner
    companion object: BaseEntityClass<LiveStream>(LiveStreams)
}