package com.dryrunn.digital.hygiene.nodebee.factory.provider

import com.dryrunn.digital.hygiene.nodebee.interfaces.INodeData
import com.dryrunn.digital.hygiene.nodebee.interfaces.IOpFactory

/**
 * Project: node-bee
 * Author: vermakartik on 17/12/23
 */
interface IFactoryProvider<T : INodeData, IF, I, O> {
    fun provide(inputs : IF) : IOpFactory<T, I, O>
}