package com.dryrunn.digital.hygiene.nodebee.sequenceGenerator

import com.dryrunn.digital.hygiene.nodebee.front.struct.NodeConfig
import com.dryrunn.digital.hygiene.nodebee.interfaces.INodeData
import com.dryrunn.digital.hygiene.nodebee.structs.Node

/**
 * Project: node-bee
 * Author: vermakartik on 19/12/23
 */
interface ISequenceGenerator<U, T : INodeData> {
    fun next(node : Node<U, T>) : U
}