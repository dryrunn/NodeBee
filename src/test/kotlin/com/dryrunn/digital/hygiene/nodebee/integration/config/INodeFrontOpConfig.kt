package com.dryrunn.digital.hygiene.nodebee.integration.config

import com.dryrunn.digital.hygiene.nodebee.front.interfaces.INodeFrontOps
import com.dryrunn.digital.hygiene.nodebee.integration.backend.MapBackendImpl
import com.dryrunn.digital.hygiene.nodebee.integration.node.TextNodeData
import com.dryrunn.digital.hygiene.nodebee.sequenceGenerator.impl.BackendStoreSequenceGeneratorImpl

/**
 * Project: node-bee
 * Author: vermakartik on 18/12/23
 */
object INodeFrontOpConfig {

    val backendImpl = MapBackendImpl()

    val textNode = INodeFrontOps.createNodeFrontOp<Int, TextNodeData>(
        backend = backendImpl,
        sequenceGenerator = BackendStoreSequenceGeneratorImpl(backendImpl),
        configContext = INodeFrontOps.getNodeConfigContext(
            TextNodeData(
                uniqueId =  "\$\$ROOT\$\$",
                value = "This is root node."
            )
        )
    )


}