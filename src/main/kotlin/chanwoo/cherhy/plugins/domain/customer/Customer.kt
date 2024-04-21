package chanwoo.cherhy.plugins.domain.customer

import chanwoo.cherhy.plugins.util.BaseEntity
import chanwoo.cherhy.plugins.util.BaseEntityClass
import chanwoo.cherhy.plugins.util.BaseLongIdTable
import org.jetbrains.exposed.dao.LongEntityClass
import org.jetbrains.exposed.dao.id.EntityID

object Customers: BaseLongIdTable("customer", "id") {
    val name = varchar("name", 50)
    val email = varchar("email", 50)
    val password = varchar("password", 50)
    val phoneNumber = varchar("phone_number", 50).nullable()
}

class Customer(id: EntityID<Long>): BaseEntity(
    id = id,
    table = Customers,
) {
    var name: String by Customers.name
    var email: String by Customers.email
    var password : String by Customers.password
    var phoneNumber: String? by Customers.phoneNumber

    companion object: BaseEntityClass<Customer>(Customers)
}