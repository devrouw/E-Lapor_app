package com.lollipop.e_lapor.util

/**
 * Sealed class used in manage result network request
 */
sealed class ResultOfNetwork<out T> {
    data class Success<out R>(val value: R) : ResultOfNetwork<R>()
    data class Empty(val test : String) : ResultOfNetwork<Nothing>()
    data class Failure(
        val message: String?,
        val throwable: Throwable?
    ) : ResultOfNetwork<Nothing>()
}
