package com.dryrunn.digital.hygiene.nodebee.nodeOp.impl

import com.dryrunn.digital.hygiene.nodebee.context.ConfigContext
import com.dryrunn.digital.hygiene.nodebee.interfaces.INodeData
import com.dryrunn.digital.hygiene.nodebee.interfaces.IOpFactory
import com.dryrunn.digital.hygiene.nodebee.nodeOp.INodeOp
import com.dryrunn.digital.hygiene.nodebee.nodeOp.impl.factory.DeleteNodeFactory
import com.dryrunn.digital.hygiene.nodebee.structs.Node
import com.dryrunn.digital.hygiene.nodebee.structs.NodeOpResponse
import com.dryrunn.digital.hygiene.nodebee.validator.INodeValidator

/**
 * Project: node-bee
 * Author: vermakartik on 16/12/23
 */

class DeleteNodeOpImpl<U, T : INodeData>(
    private val configContext: ConfigContext<U, T>,
    private val deleteNodeFactory : IOpFactory<T, Node<U, T>, INodeOp<U, T>>,
    private val validator : INodeValidator<U, T>
) : INodeOp<U, T> {

    override fun operate(node: Node<U, T>) : NodeOpResponse<U, T> {
        return try {
            if(!validator.validate(node)) return NodeOpResponse.fail("Validation Failed!")
            deleteNodeFactory
                .getImpl(node)
                .operate(node)
        } catch (e : Exception) {
            NodeOpResponse.fail(e.message ?: "Error deleting the node!")
        }
    }

}