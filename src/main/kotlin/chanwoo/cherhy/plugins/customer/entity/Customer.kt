package chanwoo.cherhy.plugins.customer.entity

//import chanwoo.cherhy.plugins.util.BaseEntity
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.Id


@Entity
data class Customer (

    @Id @GeneratedValue
    val id: Long?,

    var name: String,

    val email: String,

    var password : String,

    var phoneNumber: String?,

)
//    : BaseEntity()
