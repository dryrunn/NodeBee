package com.dryrunn.digital.hygiene.nodebee.interfaces

/**
 * Project: node-bee
 * Author: vermakartik on 17/12/23
 */
interface IOpFactory<T : INodeData, I, O> {
    fun getImpl(input : I) : O
}