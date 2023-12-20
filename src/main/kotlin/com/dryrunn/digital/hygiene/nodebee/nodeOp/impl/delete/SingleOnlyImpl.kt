package com.dryrunn.digital.hygiene.nodebee.nodeOp.impl.delete

import com.dryrunn.digital.hygiene.nodebee.backend.INodeStore
import com.dryrunn.digital.hygiene.nodebee.backend.ITransactional
import com.dryrunn.digital.hygiene.nodebee.context.ConfigContext
import com.dryrunn.digital.hygiene.nodebee.interfaces.INodeData
import com.dryrunn.digital.hygiene.nodebee.nodeOp.INodeOp
import com.dryrunn.digital.hygiene.nodebee.nodeOp.impl.AbsBaseNodeOpImpl
import com.dryrunn.digital.hygiene.nodebee.structs.Children
import com.dryrunn.digital.hygiene.nodebee.structs.Node
import com.dryrunn.digital.hygiene.nodebee.structs.NodeOpResponse
import com.dryrunn.digital.hygiene.nodebee.structs.extensions.appendFirst
import com.dryrunn.digital.hygiene.nodebee.structs.extensions.removeFirst
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import org.springframework.stereotype.Repository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

/**
 * Project: node-bee
 * Author: vermakartik on 16/12/23
 */
class SingleOnlyImpl<U, T : INodeData>(
    private val configContext: ConfigContext<U, T>,
    private val backend : INodeStore<U, T>,
    transactional: ITransactional
) : AbsBaseNodeOpImpl<U, T>(
    backend,
    transactional
) {
    override fun createRequiredOps(node: Node<U, T>, ops: MutableList<() -> Unit>) {
        val parent : Node<U, T> = node.parent(backend)!!
        val parentUpdated = parent.copy(
            children = parent.children.removeFirst()
        )

        ops.add { backend.update(parentUpdated) }
        ops.add { backend.remove(node) }
    }

    companion object Util {
        fun <U, T : INodeData> canProcess(node : Node<U, T>, backend : INodeStore<U, T>) : Boolean {
            val parent = node.parent(backend)!!
            return parent.children.data.size == 1
        }
    }
}