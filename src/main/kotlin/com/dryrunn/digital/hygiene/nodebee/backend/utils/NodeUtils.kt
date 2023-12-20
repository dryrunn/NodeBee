package com.dryrunn.digital.hygiene.nodebee.backend.utils

import com.dryrunn.digital.hygiene.nodebee.backend.INodeStore
import com.dryrunn.digital.hygiene.nodebee.interfaces.INodeData
import com.dryrunn.digital.hygiene.nodebee.structs.Node
import com.dryrunn.digital.hygiene.nodebee.structs.extensions.incrementParentVersion

/**
 * Project: node-bee
 * Author: vermakartik on 16/12/23
 */
object NodeUtils {

    fun <U, T : INodeData> generateNodeParentVersionUpdatesWithNoQuery(
        node : Node<U, T>,
        backend : INodeStore<U, T>
    ) : List<() -> Unit> =
        node.incrementParentVersion(null)
            .map { { backend.update(node) } }

}