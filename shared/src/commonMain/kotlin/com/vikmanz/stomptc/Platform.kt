package com.vikmanz.stomptc

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform