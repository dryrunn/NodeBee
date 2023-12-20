package com.dryrunn.digital.hygiene.nodebee.configs

import com.dryrunn.digital.hygiene.nodebee.interfaces.INodeData
import com.dryrunn.digital.hygiene.nodebee.structs.Node

/**
 * Project: node-bee
 * Author: vermakartik on 17/12/23
 */
data class RootNodeDataConfig<U, T : INodeData>(
    private val _rootNodeIdentifier: T
) {
    val rootNode = Node.withNodeData<U, T>(_rootNodeIdentifier)
}
