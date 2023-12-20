package com.dryrunn.digital.hygiene.nodebee.backend

import com.dryrunn.digital.hygiene.nodebee.interfaces.INodeData
import com.dryrunn.digital.hygiene.nodebee.structs.Node

/**
 * Project: node-bee
 * Author: vermakartik on 16/12/23
 */
interface INodeStore<U, T : INodeData> {
    fun insert(node : Node<U, T>) : Node<U, T>
    fun remove(node : Node<U, T>) : Boolean
    fun update(node : Node<U, T>) : Boolean
    fun get(node : Node<U, T>) : Node<U, T>
    fun getByData(node : Node<U, T>) : Node<U, T>
}