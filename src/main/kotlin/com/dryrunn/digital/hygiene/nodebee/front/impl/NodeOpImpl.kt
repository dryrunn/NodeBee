package com.dryrunn.digital.hygiene.nodebee.front.impl

import com.dryrunn.digital.hygiene.nodebee.backend.INodeStore
import com.dryrunn.digital.hygiene.nodebee.context.ConfigContext
import com.dryrunn.digital.hygiene.nodebee.enums.OpType
import com.dryrunn.digital.hygiene.nodebee.factory.provider.IFactoryProvider
import com.dryrunn.digital.hygiene.nodebee.front.interfaces.INodeFrontOps
import com.dryrunn.digital.hygiene.nodebee.front.struct.NodeConfig
import com.dryrunn.digital.hygiene.nodebee.interfaces.INodeData
import com.dryrunn.digital.hygiene.nodebee.interfaces.IOpFactory
import com.dryrunn.digital.hygiene.nodebee.nodeOp.INodeOp
import com.dryrunn.digital.hygiene.nodebee.sequenceGenerator.ISequenceGenerator
import com.dryrunn.digital.hygiene.nodebee.structs.Node
import com.dryrunn.digital.hygiene.nodebee.structs.extensions.getParents

/**
 * Project: node-bee
 * Author: vermakartik on 17/12/23
 */
class NodeOpImpl<U, T : INodeData>(
    sequenceGenerator : ISequenceGenerator<U, T>,
    configContext: ConfigContext<U, T>,
    backend : INodeStore<U, T>,
    private val opFactory : IOpFactory<T, OpType, INodeOp<U, T>>
) : AbsNodeOpImpl<U, T>(sequenceGenerator, configContext, backend)  {

    override fun insert(node: Node<U, T>): Boolean = opFactory
        .getImpl(OpType.INSERT)
        .operate(node)
        .isSuccess()

    override fun remove(node: Node<U, T>): Boolean = opFactory
        .getImpl(OpType.DELETE)
        .operate(node)
        .isSuccess()
}