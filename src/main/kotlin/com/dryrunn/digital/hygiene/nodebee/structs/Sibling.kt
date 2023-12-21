package com.dryrunn.digital.hygiene.nodebee.structs

import com.dryrunn.digital.hygiene.nodebee.backend.INodeStore
import com.dryrunn.digital.hygiene.nodebee.interfaces.INodeData

/**
 * Project: node-bee
 * Author: vermakartik on 15/12/23
 */

data class Sibling<U, T : INodeData>(
    val next : U?,
    val previous : U?
) {

    constructor(
        next: U?,
        previous: U?,
        _nextNodeCache : Node<U, T>?,
        _previousNodeCache : Node<U, T>?
    ) : this(next, previous) {
        _nextNodeCache?.run {
            _nextNode = _nextNodeCache
            _nextLoaded = true
        }
        _previousNodeCache?.run {
            _previousNode = _previousNodeCache
            _previousLoaded = true
        }
    }

    fun copyWithCache(
        next: U? = this.next,
        previous: U? = this.previous
    ) = Sibling(
        next = next,
        previous = previous,
        _previousNodeCache = _previousNode,
        _nextNodeCache =  _nextNode
    )

    private var _nextNode : Node<U, T>? = null
    private  var _nextLoaded : Boolean = false
    private var _previousNode : Node<U, T>? = null
    private var _previousLoaded : Boolean = false

    fun next(backendStore: INodeStore<U, T>?): Node<U, T>? =
        backendStore?.run main@ {
            if (_nextLoaded) return _nextNode
            next?.run {
                _nextNode = backendStore.get(Node.withOnlyNodeId(next))
                _nextLoaded = true
                return@main _nextNode
            } ?: run {
                 _nextLoaded = true
                return@main null
            }
        } ?: _nextNode

    fun previous(backendStore: INodeStore<U, T>?): Node<U, T>? =
        backendStore?.run main@ {
            if (_previousLoaded) return _previousNode
            return previous?.run {
                _previousNode = backendStore.get(Node.withOnlyNodeId(previous))
                _previousLoaded = true
                return@main _previousNode
            } ?: run {
                _previousLoaded = true
                return@main null
            }
        } ?: _previousNode

}
