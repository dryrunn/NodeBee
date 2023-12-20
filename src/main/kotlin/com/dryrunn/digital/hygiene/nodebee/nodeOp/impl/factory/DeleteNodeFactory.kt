package com.dryrunn.digital.hygiene.nodebee.nodeOp.impl.factory

import com.dryrunn.digital.hygiene.nodebee.backend.INodeStore
import com.dryrunn.digital.hygiene.nodebee.backend.ITransactional
import com.dryrunn.digital.hygiene.nodebee.context.ConfigContext
import com.dryrunn.digital.hygiene.nodebee.interfaces.INodeData
import com.dryrunn.digital.hygiene.nodebee.nodeOp.INodeOp
import com.dryrunn.digital.hygiene.nodebee.interfaces.IOpFactory
import com.dryrunn.digital.hygiene.nodebee.nodeOp.impl.delete.AtIndexImpl
import com.dryrunn.digital.hygiene.nodebee.nodeOp.impl.delete.AtLastImpl
import com.dryrunn.digital.hygiene.nodebee.nodeOp.impl.delete.AtStartImpl
import com.dryrunn.digital.hygiene.nodebee.nodeOp.impl.delete.SingleOnlyImpl
import com.dryrunn.digital.hygiene.nodebee.structs.Node
import kotlin.reflect.KClass

/**
 * Project: node-bee
 * Author: vermakartik on 16/12/23
 */
class DeleteNodeFactory<U, T : INodeData>(
    private val configContext: ConfigContext<U, T>,
    private val backendImpl: INodeStore<U, T>,
    transactional : ITransactional
) : IOpFactory<T, Node<U, T>, INodeOp<U, T>> {

    private val instanceMap = mapOf<KClass<*>, INodeOp<U, T>>(
        SingleOnlyImpl::class to SingleOnlyImpl(configContext, backendImpl, transactional),
        AtStartImpl::class to AtStartImpl(configContext, backendImpl, transactional),
        AtLastImpl::class to AtLastImpl(configContext, backendImpl, transactional),
        AtIndexImpl::class to AtIndexImpl(configContext, backendImpl, transactional)
    )

    private val conditionMatcher : List<Pair<((Node<U, T>) -> Boolean), KClass<*>>> = mutableListOf()

    init {
        (conditionMatcher as MutableList).apply {
            add(Pair({ SingleOnlyImpl.canProcess(it, backendImpl) }, SingleOnlyImpl::class))
            add(Pair({ AtStartImpl.canProcess(it, backendImpl) }, AtStartImpl::class))
            add(Pair({ AtLastImpl.canProcess(it, backendImpl) }, AtLastImpl::class))
            add(Pair({ AtIndexImpl.canProcess(it, backendImpl) }, AtIndexImpl::class))
        }
    }

    override fun getImpl(input : Node<U, T>) : INodeOp<U, T> {
        return instanceMap[conditionMatcher.first { it.first(input) }.second]!!
    }

}