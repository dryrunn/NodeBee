package com.dryrunn.digital.hygiene.nodebee.nodeOp.impl

import com.dryrunn.digital.hygiene.nodebee.context.ConfigContext
import com.dryrunn.digital.hygiene.nodebee.enums.OpType
import com.dryrunn.digital.hygiene.nodebee.factory.provider.IFactoryProvider
import com.dryrunn.digital.hygiene.nodebee.interfaces.INodeData
import com.dryrunn.digital.hygiene.nodebee.interfaces.IOpFactory
import com.dryrunn.digital.hygiene.nodebee.nodeOp.INodeOp
import com.dryrunn.digital.hygiene.nodebee.structs.Node
import com.dryrunn.digital.hygiene.nodebee.validator.INodeValidator

/**
 * Project: node-bee
 * Author: vermakartik on 17/12/23
 */
class IONodeFactory<U, T : INodeData>(
    private val configContext: ConfigContext<U, T>,
    private val factoryProvider: IFactoryProvider<T, OpType, Node<U, T>, INodeOp<U, T>>,
    private val validatorFactory: IOpFactory<T, OpType, INodeValidator<U, T>>
) : IOpFactory<T, OpType, INodeOp<U, T>> {

    private val instanceMap = mapOf(
        OpType.INSERT to InsertNodeOpImpl(configContext, factoryProvider.provide(OpType.INSERT), validatorFactory.getImpl(OpType.INSERT)),
        OpType.DELETE to DeleteNodeOpImpl(configContext, factoryProvider.provide(OpType.DELETE), validatorFactory.getImpl(OpType.DELETE))
    )

    override fun getImpl(input: OpType): INodeOp<U, T> {
        return instanceMap[input]!!
    }
}