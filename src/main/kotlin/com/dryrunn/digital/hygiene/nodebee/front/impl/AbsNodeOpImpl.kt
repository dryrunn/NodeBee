package com.dryrunn.digital.hygiene.nodebee.front.impl

import com.dryrunn.digital.hygiene.nodebee.backend.INodeStore
import com.dryrunn.digital.hygiene.nodebee.context.ConfigContext
import com.dryrunn.digital.hygiene.nodebee.front.interfaces.INodeFrontOps
import com.dryrunn.digital.hygiene.nodebee.front.struct.NodeConfig
import com.dryrunn.digital.hygiene.nodebee.front.struct.extensions.toNodeConfig
import com.dryrunn.digital.hygiene.nodebee.interfaces.INodeData
import com.dryrunn.digital.hygiene.nodebee.sequenceGenerator.ISequenceGenerator
import com.dryrunn.digital.hygiene.nodebee.structs.Children
import com.dryrunn.digital.hygiene.nodebee.structs.Node
import com.dryrunn.digital.hygiene.nodebee.structs.Sibling

/**
 * Project: node-bee
 * Author: vermakartik on 17/12/23
 */
abstract class AbsNodeOpImpl<U, T : INodeData>(
    private val sequenceGenerator : ISequenceGenerator<U, T>,
    private val configContext: ConfigContext<U, T>,
    private val backend : INodeStore<U, T>,
) : INodeFrontOps<U, T>  {

    private fun queryNodeByData(data : T) : Node<U, T> = backend.getByData(Node.withNodeData(data))

    override fun insert(node: NodeConfig<U, T>): Boolean {
        val parentNode = node.parent?.run { queryNodeByData(this) } ?: queryNodeByData(configContext.rootNodeDataConfig.rootNode.data!!)
        val nextNode = node.next?.run { queryNodeByData(this) }
        val previousNode = node.previous?.run { queryNodeByData(this) }
        val newNode = Node(
            nodeId = null,
            data = node.current,
            parent = parentNode.nodeId,
            sibling = Sibling(
                next = nextNode?.nodeId,
                previous = previousNode?.nodeId,
                _previousNodeCache = previousNode,
                _nextNodeCache =  nextNode
            ),
            children = Children(mapOf()),
            version = 1,
            _parentNodeCache = parentNode
        )
        val nextId = sequenceGenerator.next(newNode)
        return insert(newNode.copyWithCache(nodeId = nextId))
    }

    override fun remove(node: NodeConfig<U, T>): Boolean {
        val currentNode = queryNodeByData(node.current)
        currentNode.parent(backend)
        currentNode.sibling.next(backend)
        currentNode.sibling.previous(backend)
        return remove(currentNode)
    }

    override fun move(from: NodeConfig<U, T>, to: NodeConfig<U, T>): Boolean {
//        run this operation in transaction
        return remove(from) && insert(to)
    }

    override fun getByData(node: NodeConfig<U, T>): NodeConfig<U, T> {
        return queryNodeByData(node.current)
            .toNodeConfig(backend)
    }

    override fun getChildrenForNode(node: NodeConfig<U, T>): List<NodeConfig<U, T>> =
        queryNodeByData(node.current).run {
            children.run {
                this.data.keys.map { this.at(it, backend)!!.toNodeConfig(null) }
            }
        }


    abstract fun insert(node : Node<U, T>) : Boolean
    abstract fun remove(node : Node<U, T>) : Boolean
}