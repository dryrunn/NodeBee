package com.dryrunn.digital.hygiene.nodebee.structs

import com.dryrunn.digital.hygiene.nodebee.backend.INodeStore
import com.dryrunn.digital.hygiene.nodebee.interfaces.INodeData
import org.springframework.data.mongodb.core.aggregation.ArrayOperators.In
import org.yaml.snakeyaml.nodes.NodeId
import java.util.LinkedList

/**
 * Project: node-bee
 * Author: vermakartik on 15/12/23
 */

data class Children<U, T : INodeData>(
    val data : Map<Int, U>
) {
    private val childNodeMap: MutableMap<Int, Node<U, T>> = mutableMapOf()

    fun at(pos : Int, backendStore: INodeStore<U, T>?): Node<U, T>? =
        backendStore?.run main@ {
            if (childNodeMap.containsKey(pos)) return childNodeMap[pos]
            if(pos < 0 || pos >= data.size) throw IndexOutOfBoundsException(pos)
            childNodeMap[pos] = backendStore.get(Node.withOnlyNodeId(data[pos]!!))
            return@main childNodeMap[pos]
        } ?: run {
            if(childNodeMap.containsKey(pos)) return@run childNodeMap[pos]
            return@run null
        }

    fun first(backendStore: INodeStore<U, T>?): Node<U, T>? = at(0, backendStore)
    fun last(backendStore: INodeStore<U, T>?): Node<U, T>? = at(data.size - 1, backendStore)


}
