package com.dryrunn.digital.hygiene.nodebee.nodeOp.impl.insert

import com.dryrunn.digital.hygiene.nodebee.backend.INodeStore
import com.dryrunn.digital.hygiene.nodebee.backend.ITransactional
import com.dryrunn.digital.hygiene.nodebee.context.ConfigContext
import com.dryrunn.digital.hygiene.nodebee.interfaces.INodeData
import com.dryrunn.digital.hygiene.nodebee.nodeOp.impl.AbsBaseNodeOpImpl
import com.dryrunn.digital.hygiene.nodebee.structs.Node
import com.dryrunn.digital.hygiene.nodebee.structs.NodeOpResponse
import com.dryrunn.digital.hygiene.nodebee.structs.extensions.appendFirst
import com.dryrunn.digital.hygiene.nodebee.structs.extensions.incrementParentVersion
import com.dryrunn.digital.hygiene.nodebee.structs.extensions.incrementVersion
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.stereotype.Component

/**
 * Project: node-bee
 * Author: vermakartik on 16/12/23
 */
class AtStartImpl<U, T : INodeData>(
    private val configContext: ConfigContext<U, T>,
    private val backend : INodeStore<U, T>,
    transactional : ITransactional
)  : AbsBaseInsertNodeOpImpl<U, T>(configContext, backend, transactional) {

    override fun createRequiredOps(node: Node<U, T>, ops: MutableList<() -> Unit>) : Node<U, T> {
        val updatedNode = super.createRequiredOps(node, ops)

        super.createRequiredOps(node, ops)
        val parent : Node<U, T> = node.parent(null)!!
        val first : Node<U, T> = parent.children.first(backend)!!

        val parentUpdated = parent.copy(
            children = parent.children.appendFirst(node.nodeId!!)
        ).incrementVersion()
        val firstUpdated = first.copy(
            sibling = first.sibling.copy(
                previous = node.nodeId
            )
        ).incrementVersion()

        ops.add { backend.updateOnExistingVersion(parent.version, parentUpdated) }
        ops.add { backend.updateOnExistingVersion(first.version, firstUpdated) }

        return updatedNode
    }

    companion object Util {
        fun <U, T : INodeData> canProcess(node : Node<U, T>, backend : INodeStore<U, T>) : Boolean {
            val parent = node.parent(backend)!!
            return (
                parent.children.data.isNotEmpty() &&
                node.sibling.next == parent.children.data[0]!!
            )
        }
    }

}