package com.app.websocketsample.core.extension

import com.app.websocketsample.data.entity.Mock
import java.util.*

fun String.isMessageFormat(): Boolean {
    val regex = """(\d)-(\w+)""".toRegex()
    return this.matches(regex)
}

fun String.isLogout(): Boolean {
    val logout = "Logout"
    return (this == logout || this == logout.toLowerCase(Locale.ENGLISH) || this == logout.toUpperCase(Locale.ENGLISH))
}

fun String.createMockObject() : Mock {
    val hyphen = '-'
    return Mock(this.substringBefore(hyphen).trim().toInt(), this.substringAfter(hyphen).trim())
}
