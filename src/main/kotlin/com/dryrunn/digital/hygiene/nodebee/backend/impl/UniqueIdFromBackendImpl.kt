package com.dryrunn.digital.hygiene.nodebee.backend.impl

import com.dryrunn.digital.hygiene.nodebee.backend.INodeStore
import com.dryrunn.digital.hygiene.nodebee.exceptions.IdNotFoundException
import com.dryrunn.digital.hygiene.nodebee.interfaces.INodeData
import com.dryrunn.digital.hygiene.nodebee.structs.Node
import org.springframework.stereotype.Component
import org.springframework.util.StringUtils

/**
 * Project: node-bee
 * Author: vermakartik on 16/12/23
 */
@Component
class UniqueIdFromBackendImpl<U, T : INodeData>(
    private val nodeStore : INodeStore<U, T>
) : INodeStore<U, T> {

    override fun insert(node: Node<U, T>): Node<U, T> {
        val newNode = nodeStore.insert(node)
        node.nodeId ?: throw IdNotFoundException()
        return newNode
    }

    override fun remove(node: Node<U, T>): Boolean = nodeStore.remove(node)

    override fun update(node: Node<U, T>): Boolean = nodeStore.update(node)

    override fun get(node: Node<U, T>): Node<U, T> = nodeStore.get(node)

    override fun getByData(node: Node<U, T>): Node<U, T> = nodeStore.getByData(node)
}