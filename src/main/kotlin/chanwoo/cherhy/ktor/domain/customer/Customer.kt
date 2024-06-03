package chanwoo.cherhy.ktor.domain.customer

import chanwoo.cherhy.ktor.util.model.BaseEntity
import chanwoo.cherhy.ktor.util.model.BaseEntityClass
import chanwoo.cherhy.ktor.util.model.BaseLongIdTable
import org.jetbrains.exposed.dao.id.EntityID

object Customers: BaseLongIdTable("customer", "id") {
    val name = varchar("name", 50)
    val email = varchar("email", 50)
    val password = varchar("password", 50)
    val phoneNumber = varchar("phone_number", 50).nullable()
}

class Customer(id: EntityID<CustomerId>): BaseEntity(
    id = id.unwrap(),
    table = Customers,
) {
    var name by Customers.name
    var email by Customers.email
    var password by Customers.password
    var phoneNumber by Customers.phoneNumber

    companion object: BaseEntityClass<Customer>(Customers)
}
@JvmInline
value class CustomerId(val value: Long): Comparable<CustomerId> {
    override fun compareTo(
        other: CustomerId,
    ) = value.compareTo(other.value)
    companion object {
        @JvmStatic
        fun of(
            value: Long,
        ) = CustomerId(value)
    }
}

private fun EntityID<CustomerId>.unwrap() = EntityID(value.value, Customers)

@JvmInline
value class CustomerName(
    val value: String,
) {
    companion object {
        @JvmStatic
        fun of(
            value: String,
        ) = CustomerName(value)
    }
}