package com.dryrunn.digital.hygiene.nodebee.sequenceGenerator.impl

import com.dryrunn.digital.hygiene.nodebee.backend.INodeStore
import com.dryrunn.digital.hygiene.nodebee.interfaces.INodeData
import com.dryrunn.digital.hygiene.nodebee.sequenceGenerator.ISequenceGenerator
import com.dryrunn.digital.hygiene.nodebee.structs.Node

/**
 * Project: node-bee
 * Author: vermakartik on 19/12/23
 */
class BackendStoreSequenceGeneratorImpl<U, T : INodeData>(
    private val backend : INodeStore<U, T>
) : ISequenceGenerator<U, T> {

    override fun next(node: Node<U, T>): U {
        val nodeWithId = backend.insert(node)
        return nodeWithId.nodeId!!
    }
}