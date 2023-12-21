package com.dryrunn.digital.hygiene.nodebee.nodeOp.impl.insert

import com.dryrunn.digital.hygiene.nodebee.backend.INodeStore
import com.dryrunn.digital.hygiene.nodebee.backend.ITransactional
import com.dryrunn.digital.hygiene.nodebee.context.ConfigContext
import com.dryrunn.digital.hygiene.nodebee.interfaces.INodeData
import com.dryrunn.digital.hygiene.nodebee.nodeOp.INodeOp
import com.dryrunn.digital.hygiene.nodebee.nodeOp.impl.AbsBaseNodeOpImpl
import com.dryrunn.digital.hygiene.nodebee.structs.Node
import com.dryrunn.digital.hygiene.nodebee.structs.NodeOpResponse
import com.dryrunn.digital.hygiene.nodebee.structs.extensions.appendAtPos
import com.dryrunn.digital.hygiene.nodebee.structs.extensions.appendFirst
import com.dryrunn.digital.hygiene.nodebee.structs.extensions.incrementVersion
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
) : AbsBaseInsertNodeOpImpl<U, T>(
    configContext,
    backend,
    transactional
) {

    override fun createRequiredOps(node: Node<U, T>, ops: MutableList<() -> Unit>) : Node<U, T> {
        val updatedNode = super.createRequiredOps(node, ops)

        val parent : Node<U, T> = node.parent(backend)!!
        val previous : Node<U, T> = node.sibling.previous(backend)!!
        val next : Node<U, T> = node.sibling.next(backend)!!

        val position = parent.children.data.entries.first { it.value == next.nodeId }.key

        val parentUpdated = parent.copy(
            children = parent.children.appendAtPos(position, node.nodeId!!)
        ).incrementVersion()
        val previousUpdated = previous.copy(
            sibling = previous.sibling.copy(
                next = node.nodeId
            )
        ).incrementVersion()
        val nextUpdated = next.copy(
            sibling = next.sibling.copy(
                previous = node.nodeId
            )
        ).incrementVersion()

        ops.add { backend.updateOnExistingVersion(parent.version, parentUpdated) }
        ops.add { backend.updateOnExistingVersion(next.version, nextUpdated) }
        ops.add { backend.updateOnExistingVersion(previous.version, previousUpdated) }

        return updatedNode
    }

    companion object Util {
        fun <U, T : INodeData> canProcess(node : Node<U, T>, backend : INodeStore<U, T>) : Boolean {
            val parent = node.parent(backend)!!
            return (
                parent.children.data.size > 1 &&
                (node.sibling.next?.run { true } ?: false)  &&
                (node.sibling.previous?.run { true } ?: false)
            )
        }
    }

}