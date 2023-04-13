package com.dipumba.ytsocialapp

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform