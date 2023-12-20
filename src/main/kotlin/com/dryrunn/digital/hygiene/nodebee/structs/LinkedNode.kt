package com.dryrunn.digital.hygiene.nodebee.structs

/**
 * Project: node-bee
 * Author: vermakartik on 16/12/23
 */
data class LinkedNode(
    val nodeId : String,
    val next : LinkedNode?
)
