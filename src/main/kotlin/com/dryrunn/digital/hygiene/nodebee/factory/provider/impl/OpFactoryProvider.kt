package com.dryrunn.digital.hygiene.nodebee.factory.provider.impl

import com.dryrunn.digital.hygiene.nodebee.backend.INodeStore
import com.dryrunn.digital.hygiene.nodebee.backend.ITransactional
import com.dryrunn.digital.hygiene.nodebee.context.ConfigContext
import com.dryrunn.digital.hygiene.nodebee.enums.OpType
import com.dryrunn.digital.hygiene.nodebee.factory.provider.IFactoryProvider
import com.dryrunn.digital.hygiene.nodebee.interfaces.INodeData
import com.dryrunn.digital.hygiene.nodebee.interfaces.IOpFactory
import com.dryrunn.digital.hygiene.nodebee.nodeOp.INodeOp
import com.dryrunn.digital.hygiene.nodebee.nodeOp.impl.factory.DeleteNodeFactory
import com.dryrunn.digital.hygiene.nodebee.nodeOp.impl.factory.InsertNodeFactory
import com.dryrunn.digital.hygiene.nodebee.structs.Node

/**
 * Project: node-bee
 * Author: vermakartik on 17/12/23
 */
class NodeOpFactoryProvider<U, T : INodeData>(
    configContext: ConfigContext<U, T>,
    backend : INodeStore<U, T>,
    transactional: ITransactional
) : IFactoryProvider<T, OpType, Node<U, T>, INodeOp<U, T>> {

    private val instanceMap = mapOf(
        OpType.INSERT to InsertNodeFactory(configContext, backend, transactional),
        OpType.DELETE to DeleteNodeFactory(configContext, backend, transactional)
    )

    override fun provide(inputs: OpType): IOpFactory<T, Node<U, T>, INodeOp<U, T>> {
        return instanceMap[inputs]!!
    }
}