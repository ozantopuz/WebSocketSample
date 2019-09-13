package com.app.websocketsample.core.extension

import com.app.websocketsample.data.entity.Mock

fun String.isMessageFormat(): Boolean {
    val regex = """(\d)-(\w+)""".toRegex()
    return this.matches(regex)
}

fun String.createMockObject() : Mock {
    val hyphen = '-'
    return Mock(this.substringBefore(hyphen).trim().toInt(), this.substringAfter(hyphen).trim())
}
