package com.dryrunn.digital.hygiene.nodebee.front.struct.extensions

import com.dryrunn.digital.hygiene.nodebee.backend.INodeStore
import com.dryrunn.digital.hygiene.nodebee.front.struct.NodeConfig
import com.dryrunn.digital.hygiene.nodebee.interfaces.INodeData
import com.dryrunn.digital.hygiene.nodebee.structs.Node

/**
 * Project: node-bee
 * Author: vermakartik on 19/12/23
 */

fun <U, T : INodeData> Node<U, T>.toNodeConfig(backend: INodeStore<U, T>?) : NodeConfig<U, T> {
    return NodeConfig(
        current = data!!,
        next = sibling.next(backend)?.data,
        previous = sibling.previous(backend)?.data,
        parent = parent(backend)?.data,
        version = version
    )
}