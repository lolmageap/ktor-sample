package chanwoo.cherhy.ktor.util

object EndPoint {
    const val HOME = "/"

    object CUSTOMER {
        private const val CUSTOMER = "/customers"

        const val SIGN_UP = "$CUSTOMER/signup"
        const val GET_ME = "$CUSTOMER/me"
        const val UPDATE_CUSTOMER = CUSTOMER
        const val DELETE_CUSTOMER = CUSTOMER
        const val LOGIN = "$CUSTOMER/login"
    }

    object CHAT {
        private const val CHAT = "/chat"

        const val ECHO = CHAT
    }

    object CHAT_ROOM {
        private const val CHAT_ROOM = "/chat-room"

        const val CREATE_CHAT_ROOM = CHAT_ROOM
    }
}