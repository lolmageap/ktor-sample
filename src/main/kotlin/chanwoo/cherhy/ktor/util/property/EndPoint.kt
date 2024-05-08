package chanwoo.cherhy.ktor.util.property

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
        const val GET_CHAT_HISTORY = "$CHAT/history/{room-id}"
        const val BROADCAST = "$CHAT/broadcast"
    }

    object CHATROOM {
        private const val CHAT_ROOM = "/chatroom"

        const val CREATE_CHAT_ROOM = CHAT_ROOM
    }
}