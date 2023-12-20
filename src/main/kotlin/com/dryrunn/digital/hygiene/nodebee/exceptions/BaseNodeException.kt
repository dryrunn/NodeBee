package com.dryrunn.digital.hygiene.nodebee.exceptions

/**
 * Project: node-bee
 * Author: vermakartik on 16/12/23
 */
sealed class BaseNodeException(
    message: String
) : Exception(
    message
) {
    constructor() : this("")
    constructor(exception: Exception) : this(exception.message ?: "")
}