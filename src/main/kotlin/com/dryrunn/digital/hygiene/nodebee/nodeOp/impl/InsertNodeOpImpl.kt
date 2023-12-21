package com.dryrunn.digital.hygiene.nodebee.nodeOp.impl

import com.dryrunn.digital.hygiene.nodebee.backend.INodeStore
import com.dryrunn.digital.hygiene.nodebee.context.ConfigContext
import com.dryrunn.digital.hygiene.nodebee.enums.OpType
import com.dryrunn.digital.hygiene.nodebee.interfaces.INodeData
import com.dryrunn.digital.hygiene.nodebee.interfaces.IOpFactory
import com.dryrunn.digital.hygiene.nodebee.nodeOp.INodeOp
import com.dryrunn.digital.hygiene.nodebee.nodeOp.impl.factory.InsertNodeFactory
import com.dryrunn.digital.hygiene.nodebee.structs.Node
import com.dryrunn.digital.hygiene.nodebee.structs.NodeOpResponse
import com.dryrunn.digital.hygiene.nodebee.validator.INodeValidator
import com.dryrunn.digital.hygiene.nodebee.validator.ValidatorFactory
import org.slf4j.MDC
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

/**
 * Project: node-bee
 * Author: vermakartik on 16/12/23
 */

class InsertNodeOpImpl<U, T : INodeData>(
    private val configContext: ConfigContext<U, T>,
    private val insertNodeFactory: IOpFactory<T, Node<U, T>, INodeOp<U, T>>,
    private val validator: INodeValidator<U, T>
) : INodeOp<U, T> {

    override fun operate(node: Node<U, T>) : NodeOpResponse<U, T> {
        return try {
            if(!validator.validate(node)) return NodeOpResponse.fail("Validation Failed!!")
            insertNodeFactory
                .getImpl(node)
                .operate(node)
        } catch (e : Exception) {
            NodeOpResponse.fail(e.message ?: "Error inserting node!")
        }
    }

}