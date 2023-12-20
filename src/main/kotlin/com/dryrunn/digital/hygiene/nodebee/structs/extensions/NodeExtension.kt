package com.dryrunn.digital.hygiene.nodebee.structs.extensions

import com.dryrunn.digital.hygiene.nodebee.backend.INodeStore
import com.dryrunn.digital.hygiene.nodebee.interfaces.INodeData
import com.dryrunn.digital.hygiene.nodebee.structs.Children
import com.dryrunn.digital.hygiene.nodebee.structs.Node

fun <U, T : INodeData> Node<U, T>.getParents(backend : INodeStore<U, T>?) : List<Node<U, T>> {

    val parentNodes = mutableListOf<Node<U, T>>()
    var current : Node<U, T>? = this
    while (current != null) {
        current = current.parent(backend)
        if(current != null) {
            parentNodes.add(current)
        }
    }

    return parentNodes
}

fun <U, T : INodeData> Node<U, T>.incrementVersion() : Node<U, T> =
    this.copy(version = this.version + 1)


fun <U, T : INodeData> Node<U, T>.incrementParentVersion(backend : INodeStore<U, T>?) : List<Node<U, T>> =
    getParents(backend)
        .map { it.incrementVersion() }

