package com.dryrunn.digital.hygiene.nodebee.nodeOp.impl.insert

import com.dryrunn.digital.hygiene.nodebee.backend.INodeStore
import com.dryrunn.digital.hygiene.nodebee.backend.ITransactional
import com.dryrunn.digital.hygiene.nodebee.context.ConfigContext
import com.dryrunn.digital.hygiene.nodebee.interfaces.INodeData
import com.dryrunn.digital.hygiene.nodebee.interfaces.IOpFactory
import com.dryrunn.digital.hygiene.nodebee.nodeOp.INodeOp
import com.dryrunn.digital.hygiene.nodebee.nodeOp.impl.AbsBaseNodeOpImpl
import com.dryrunn.digital.hygiene.nodebee.nodeOp.impl.factory.InsertNodeFactory
import com.dryrunn.digital.hygiene.nodebee.structs.Node
import com.dryrunn.digital.hygiene.nodebee.structs.NodeOpResponse
import org.slf4j.MDC
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

/**
 * Project: node-bee
 * Author: vermakartik on 16/12/23
 */

@Component
abstract class AbsBaseInsertNodeOpImpl<U, T : INodeData>(
    configContext: ConfigContext<U, T>,
    backend : INodeStore<U, T>,
    transactional : ITransactional
) : AbsBaseNodeOpImpl<U, T>(
    backend,
    transactional
) {

    override fun createRequiredOps(node: Node<U, T>, ops: MutableList<() -> Unit>) {
        ops.add { node.copy(version = node.version + 1) }
    }
}