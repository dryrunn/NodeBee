package com.dryrunn.digital.hygiene.nodebee.nodeOp.impl

import com.dryrunn.digital.hygiene.nodebee.backend.INodeStore
import com.dryrunn.digital.hygiene.nodebee.backend.ITransactional
import com.dryrunn.digital.hygiene.nodebee.backend.utils.NodeUtils
import com.dryrunn.digital.hygiene.nodebee.interfaces.INodeData
import com.dryrunn.digital.hygiene.nodebee.nodeOp.INodeOp
import com.dryrunn.digital.hygiene.nodebee.structs.Node
import com.dryrunn.digital.hygiene.nodebee.structs.NodeOpResponse

/**
 * Project: node-bee
 * Author: vermakartik on 16/12/23
 */
abstract class AbsBaseNodeOpImpl<U, T : INodeData>(
    private val backendImpl : INodeStore<U, T>,
    private val transactionImpl : ITransactional
) : INodeOp<U, T> {

    override fun operate(node: Node<U, T>): NodeOpResponse<U, T> {
        val ops : List<() -> Unit> = mutableListOf()
        (ops as MutableList)
            .addAll(
                NodeUtils
                   .generateNodeParentVersionUpdatesWithNoQuery(node, backendImpl)
            )
        createRequiredOps(node, ops)
        transactionImpl.ops(*ops.toTypedArray())
        return NodeOpResponse.success(node)
    }

    abstract fun createRequiredOps(
        node : Node<U, T>,
        ops : MutableList<() -> Unit>
    )

}