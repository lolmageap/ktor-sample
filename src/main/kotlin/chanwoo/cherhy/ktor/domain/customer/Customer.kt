package chanwoo.cherhy.ktor.domain.customer

import chanwoo.cherhy.ktor.util.model.BaseEntity
import chanwoo.cherhy.ktor.util.model.BaseEntityClass
import chanwoo.cherhy.ktor.util.model.BaseLongIdTable
import chanwoo.cherhy.ktor.util.CustomerId
import chanwoo.cherhy.ktor.util.CustomerName
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.sql.Column

object Customers: BaseLongIdTable("customer", "id") {
    val name: Column<CustomerName> = varchar("name", 50)
    val email = varchar("email", 50)
    val password = varchar("password", 50)
    val phoneNumber = varchar("phone_number", 50).nullable()
}

class Customer(id: EntityID<CustomerId>): BaseEntity(
    id = id,
    table = Customers,
) {
    var name: String by Customers.name
    var email: String by Customers.email
    var password : String by Customers.password
    var phoneNumber: String? by Customers.phoneNumber

    companion object: BaseEntityClass<Customer>(Customers)
}