package chanwoo.cherhy.plugins.config

object EndPoint {
    const val HOME = "/"

    object CUSTOMER {
        private const val CUSTOMER = "/customers"

        const val CREATE_CUSTOMER = CUSTOMER
        const val GET_CUSTOMERS = CUSTOMER
        const val GET_CUSTOMER = "$CUSTOMER/{id}"
        const val UPDATE_CUSTOMER = "$CUSTOMER/{id}"
        const val DELETE_CUSTOMER = "$CUSTOMER/{id}"
    }
}