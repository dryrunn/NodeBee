package com.dryrunn.digital.hygiene.nodebee.nodeOp.impl.delete

import com.dryrunn.digital.hygiene.nodebee.backend.INodeStore
import com.dryrunn.digital.hygiene.nodebee.backend.ITransactional
import com.dryrunn.digital.hygiene.nodebee.context.ConfigContext
import com.dryrunn.digital.hygiene.nodebee.interfaces.INodeData
import com.dryrunn.digital.hygiene.nodebee.nodeOp.INodeOp
import com.dryrunn.digital.hygiene.nodebee.nodeOp.impl.AbsBaseNodeOpImpl
import com.dryrunn.digital.hygiene.nodebee.structs.Node
import com.dryrunn.digital.hygiene.nodebee.structs.extensions.removeAtPos
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

/**
 * Project: node-bee
 * Author: vermakartik on 16/12/23
 */

class AtIndexImpl<U, T : INodeData>(
    private val configContext: ConfigContext<U, T>,
    private val backend : INodeStore<U, T>,
    transactional : ITransactional
) : AbsBaseNodeOpImpl<U, T>(
    backend,
    transactional
) {

    override fun createRequiredOps(node: Node<U, T>, ops: MutableList<() -> Unit>) {
        val parent : Node<U, T> = node.parent(backend)!!
        val previous : Node<U, T> = node.sibling.previous(backend)!!
        val next : Node<U, T> = parent.sibling.next(backend)!!

        val position = parent
            .children
            .data
            .entries
            .first { it.value == node.nodeId!! }
            .key

        val parentUpdated = parent.copy(
            children = parent.children.removeAtPos(position)
        )
        val previousUpdated = previous.copy(
            sibling = previous.sibling.copy(
                next = next.nodeId
            )
        )
        val nextUpdated = next.copy(
            sibling = next.sibling.copy(
                previous = previous.nodeId
            )
        )
        ops.add { backend.update(parentUpdated) }
        ops.add { backend.update(nextUpdated) }
        ops.add { backend.update(previousUpdated) }
    }

    companion object Util {
        fun <U, T : INodeData> canProcess(node : Node<U, T>, backend : INodeStore<U, T>) : Boolean {
            val parent = node.parent(backend)!!
            return (
                parent.children.data.size > 2 &&
                (node.sibling.previous?.run { true } ?: false) &&
                (node.sibling.next?.run { true } ?: false)
            )
        }
    }

}