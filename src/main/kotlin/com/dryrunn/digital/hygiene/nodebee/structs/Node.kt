package com.dryrunn.digital.hygiene.nodebee.structs

import com.dryrunn.digital.hygiene.nodebee.backend.INodeStore
import com.dryrunn.digital.hygiene.nodebee.interfaces.INodeData

/**
 * Project: node-bee
 * Author: vermakartik on 15/12/23
 */

data class Node<U, T : INodeData>(
    val nodeId : U?,
    val data : T?,
    val sibling : Sibling<U, T>,
    val parent : U?,
    val children : Children<U, T>,
    val version : Int
) {

    constructor(
        nodeId : U?,
        data : T?,
        sibling : Sibling<U, T>,
        parent : U?,
        children : Children<U, T>,
        version : Int,
        _parentNodeCache : Node<U, T>
    ) : this(nodeId, data, sibling, parent, children, version){
        _parentNode = _parentNodeCache
        _parentLoaded = true
    }

    private var _parentNode: Node<U, T>? = null
    private var _parentLoaded = false

    fun parent(backendStore: INodeStore<U, T>?): Node<U, T>? =
        backendStore?.run main@ {
            if (_parentLoaded) return _parentNode
            parent?.run {
                _parentNode = backendStore.get(withOnlyNodeId(parent))
                _parentLoaded = true
                return@main _parentNode
            } ?: run {
                _parentLoaded = true
                return@main null
            }
        } ?: _parentNode

    fun copyWithCache(
        nodeId : U? = this.nodeId,
        data : T? = this.data,
        sibling : Sibling<U, T> = this.sibling,
        parent : U? = this.parent,
        children : Children<U, T> = this.children,
        version : Int = this.version,
    ) = Node(
        nodeId = nodeId,
        data = data,
        sibling = sibling.copyWithCache(),
        parent = parent,
        children = children,
        version = version,
        _parentNodeCache = _parentNode!!
    )

    companion object NodeUtil {
        fun <U, T : INodeData> withOnlyNodeId(nodeId: U) = Node<U, T>(
            nodeId = nodeId,
            parent = null,
            sibling = Sibling(null, null),
            children = Children(mapOf()),
            data = null,
            version = 1
        )

        fun <U, T : INodeData> withNodeData(data : T) = Node<U, T>(
            nodeId = null,
            parent = null,
            sibling = Sibling(null, null),
            children = Children(mapOf()),
            data = data,
            version = 1
        )
    }

}