package chanwoo.cherhy.ktor.domain.livestream

import chanwoo.cherhy.ktor.domain.customer.Customers
import chanwoo.cherhy.ktor.util.model.BaseEntity
import chanwoo.cherhy.ktor.util.model.BaseEntityClass
import chanwoo.cherhy.ktor.util.model.BaseLongIdTable
import org.jetbrains.exposed.dao.id.EntityID

object LiveStreams: BaseLongIdTable("live_stream", "id") {
    val owner = reference("customer_id", Customers)
}

class LiveStream(
    id: EntityID<LiveStreamId>,
): BaseEntity(
    id = id.unwrap(),
    table = LiveStreams,
) {
    var owner by LiveStreams.owner
    companion object: BaseEntityClass<LiveStream>(LiveStreams)
}

@JvmInline
value class LiveStreamId(
    val value: Long,
): Comparable<LiveStreamId> {
    override fun compareTo(
        other: LiveStreamId,
    ) = value.compareTo(other.value)
    companion object {
        fun of(
            value: Long,
        ) = LiveStreamId(value)
    }
}

private fun EntityID<LiveStreamId>.unwrap() = EntityID(value.value, LiveStreams)