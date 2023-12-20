package com.dryrunn.digital.hygiene.nodebee.sequenceGenerator.impl

import com.dryrunn.digital.hygiene.nodebee.backend.INodeStore
import com.dryrunn.digital.hygiene.nodebee.interfaces.INodeData
import com.dryrunn.digital.hygiene.nodebee.sequenceGenerator.ISequenceGenerator
import com.dryrunn.digital.hygiene.nodebee.structs.Node
import java.util.concurrent.atomic.AtomicInteger

/**
 * Project: node-bee
 * Author: vermakartik on 19/12/23
 */
class InMemoryNumberSequenceGeneratorImpl<U, T : INodeData>(
    seed : Int
) : ISequenceGenerator<Int, T> {

    private val sequenceStart = AtomicInteger(seed)

    override fun next(node: Node<Int, T>): Int = sequenceStart.getAndIncrement()
}