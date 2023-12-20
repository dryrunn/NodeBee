package com.dryrunn.digital.hygiene.nodebee.structs

import com.dryrunn.digital.hygiene.nodebee.interfaces.INodeData

/**
 * Project: node-bee
 * Author: vermakartik on 16/12/23
 */
data class NodeContext<U, T : INodeData>(
    val node : Node<U, T>,
    val parent : Node<U, T>
)