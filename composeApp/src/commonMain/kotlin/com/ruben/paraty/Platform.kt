package com.ruben.paraty

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform