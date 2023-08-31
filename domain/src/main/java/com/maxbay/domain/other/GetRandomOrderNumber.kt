package com.maxbay.domain.other

object GetRandomOrderNumber {
    fun get(): Int {
        return (1..Int.MAX_VALUE).random()
    }
}