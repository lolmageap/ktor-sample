package chanwoo.cherhy.ktor.extension

import java.util.*

fun Base64.Encoder.encode(password: String) =
    Base64.getEncoder().encodeToString(password.toByteArray())!!