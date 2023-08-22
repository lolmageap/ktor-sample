package chanwoo.cherhy.plugins.customer.service

import chanwoo.cherhy.plugins.customer.entity.Customer
import chanwoo.cherhy.plugins.customer.request.CustomerRequest
import chanwoo.cherhy.plugins.customer.response.CustomerResponse
import com.linecorp.kotlinjdsl.QueryFactory
import com.linecorp.kotlinjdsl.listQuery
import com.linecorp.kotlinjdsl.querydsl.expression.column
import com.linecorp.kotlinjdsl.singleQuery

object CustomerService {

    lateinit var queryFactory : QueryFactory

    fun getCustomerById(id: Long): CustomerResponse {

        val customer : Customer = queryFactory.singleQuery {
            select(entity(Customer::class))
            from(entity(Customer::class))
            where(
                column(Customer::id).equal(id),
            )
        } ?: throw Exception("에러")

        return CustomerResponse.of(customer)
//        return CustomerRequest(id = id, name = "kim", email = "chan@woo.com")
    }

}