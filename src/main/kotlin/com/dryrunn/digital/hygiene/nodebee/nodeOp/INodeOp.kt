package com.dryrunn.digital.hygiene.nodebee.nodeOp

import com.dryrunn.digital.hygiene.nodebee.interfaces.INodeData
import com.dryrunn.digital.hygiene.nodebee.structs.Node
import com.dryrunn.digital.hygiene.nodebee.structs.NodeContext
import com.dryrunn.digital.hygiene.nodebee.structs.NodeOpResponse

/**
 * Project: node-bee
 * Author: vermakartik on 15/12/23
 */
interface INodeOp<U, T : INodeData> {
    fun operate(node : Node<U, T>) : NodeOpResponse<U, T>
}