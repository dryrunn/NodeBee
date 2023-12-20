package com.dryrunn.digital.hygiene.nodebee.nodeOp.impl.insert

import com.dryrunn.digital.hygiene.nodebee.backend.INodeStore
import com.dryrunn.digital.hygiene.nodebee.backend.ITransactional
import com.dryrunn.digital.hygiene.nodebee.context.ConfigContext
import com.dryrunn.digital.hygiene.nodebee.interfaces.INodeData
import com.dryrunn.digital.hygiene.nodebee.nodeOp.INodeOp
import com.dryrunn.digital.hygiene.nodebee.nodeOp.impl.AbsBaseNodeOpImpl
import com.dryrunn.digital.hygiene.nodebee.structs.Node
import com.dryrunn.digital.hygiene.nodebee.structs.NodeOpResponse
import com.dryrunn.digital.hygiene.nodebee.structs.extensions.appendFirst
import com.dryrunn.digital.hygiene.nodebee.structs.extensions.appendLast
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

/**
 * Project: node-bee
 * Author: vermakartik on 16/12/23
 */

class AtLastImpl<U, T : INodeData>(
    private val configContext: ConfigContext<U, T>,
    private val backend : INodeStore<U, T>,
    private val transactional : ITransactional
) : AbsBaseNodeOpImpl<U, T>(backend, transactional) {

    override fun createRequiredOps(node: Node<U, T>, ops: MutableList<() -> Unit>) {
        val parent : Node<U, T> = node.parent(backend)!!
        val last : Node<U, T> = parent.children.last(backend)!!

        val parentUpdated = parent.copy(
            children = parent.children.appendLast(node.nodeId!!)
        )
        val lastUpdated = last.copy(
            sibling = last.sibling.copy(
                next = node.nodeId
            )
        )

        ops.add { backend.update(parentUpdated) }
        ops.add { backend.update(lastUpdated) }
    }

    companion object Util {
        fun <U, T : INodeData> canProcess(node : Node<U, T>, backend : INodeStore<U, T>) : Boolean {
            val parent = node.parent(backend)!!
            return (
                parent.children.data.size == 1 &&
                (node.sibling.previous ?: "") == parent.children.data[0]!!
            )
        }
    }

}