package com.dryrunn.digital.hygiene.nodebee.integration.backend

import com.dryrunn.digital.hygiene.nodebee.backend.INodeStore
import com.dryrunn.digital.hygiene.nodebee.integration.node.TextNodeData
import com.dryrunn.digital.hygiene.nodebee.sequenceGenerator.impl.InMemoryNumberSequenceGeneratorImpl
import com.dryrunn.digital.hygiene.nodebee.structs.Node
/**
 * Project: node-bee
 * Author: vermakartik on 18/12/23
 */
class MapBackendImpl() : INodeStore<Int, TextNodeData> {

    private val seq = InMemoryNumberSequenceGeneratorImpl<Int, TextNodeData>(0)
    val mapBackend = mutableMapOf<Int, Node<Int, TextNodeData>>()

    override fun insert(node: Node<Int, TextNodeData>): Node<Int, TextNodeData> {
        return node.copy(nodeId = seq.next(node))
            .run {
                mapBackend[this.nodeId!!] = this
                return@run this
            }
    }

    override fun remove(node: Node<Int, TextNodeData>): Boolean {
        this.mapBackend.remove(node.nodeId)
        return true
    }

    override fun update(node: Node<Int, TextNodeData>): Boolean {
        this.mapBackend.remove(node.nodeId)
        this.mapBackend[node.nodeId!!] = node
        return true
    }

    override fun get(node: Node<Int, TextNodeData>): Node<Int, TextNodeData> {
        return this.mapBackend[node.nodeId]!!
    }

    override fun getByData(node: Node<Int, TextNodeData>): Node<Int, TextNodeData> {
        return this.mapBackend.values.find { it.data?.getUniqueValue() == node.data!!.getUniqueValue() }!!
    }

    override fun updateOnExistingVersion(existingVersion: Int, node: Node<Int, TextNodeData>): Boolean {
        return when(this.mapBackend[node.nodeId]!!.version == existingVersion) {
            true -> update(node)
            false -> throw Exception("Version mismatch")
        }
    }
}

