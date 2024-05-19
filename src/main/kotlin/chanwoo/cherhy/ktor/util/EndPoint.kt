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
        const val GET_CHAT_HISTORY = "$CHAT/history/{chat-room-id}"
        const val BROADCAST = "$CHAT/broadcast"
    }

    object CHATROOM {
        private const val CHAT_ROOM = "/chatroom"

        const val CREATE_CHAT_ROOM = CHAT_ROOM
    }

    object VIDEO {
        private const val VIDEO = "/videos"

        const val GET_VIDEO = "$VIDEO/{video-id}"
        const val GET_VIDEOS = VIDEO
        const val UPLOAD_VIDEO = VIDEO
        const val UPDATE_VIDEO = "$VIDEO/{video-id}"
        const val DELETE_VIDEO = "$VIDEO/{video-id}"
    }

    object LIVESTREAM {
        private const val LIVE_STREAM = "/live-stream"

        const val CALLED_LIVE_STREAM = "$LIVE_STREAM/{live-stream-id}"
        const val CALL_LIVE_STREAM = "$LIVE_STREAM/{customer-id}"
    }
}