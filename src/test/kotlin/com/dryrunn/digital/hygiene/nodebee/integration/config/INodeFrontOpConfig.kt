package com.dryrunn.digital.hygiene.nodebee.integration.config

import com.dryrunn.digital.hygiene.nodebee.front.interfaces.INodeFrontOps
import com.dryrunn.digital.hygiene.nodebee.front.struct.NodeConfig
import com.dryrunn.digital.hygiene.nodebee.integration.backend.MapBackendImpl
import com.dryrunn.digital.hygiene.nodebee.integration.node.TextNodeData
import com.dryrunn.digital.hygiene.nodebee.sequenceGenerator.impl.BackendStoreSequenceGeneratorImpl

/**
 * Project: node-bee
 * Author: vermakartik on 18/12/23
 */
object INodeFrontOpConfig {

    fun reInit() {
        _backend = MapBackendImpl()
        _textNode = INodeFrontOps.createNodeFrontOp<Int, TextNodeData>(
            backend = backendImpl,
            sequenceGenerator = BackendStoreSequenceGeneratorImpl(backendImpl),
            configContext = INodeFrontOps.getNodeConfigContext(parent.current)
        )
    }

    private var _backend = MapBackendImpl()
    val backendImpl
        get() = _backend

    private var _parent = NodeConfig<Int, TextNodeData>(
        current = TextNodeData(
            uniqueId =  "\$\$ROOT\$\$",
            value = "This is root node."
        )
    )

    val parent = NodeConfig<Int, TextNodeData>(
        current = TextNodeData(
            uniqueId =  "\$\$ROOT\$\$",
            value = "This is root node."
        )
    )

    private var _textNode = INodeFrontOps.createNodeFrontOp<Int, TextNodeData>(
        backend = backendImpl,
        sequenceGenerator = BackendStoreSequenceGeneratorImpl(backendImpl),
        configContext = INodeFrontOps.getNodeConfigContext(parent.current)
    )
    val textNode
        get() = _textNode


}