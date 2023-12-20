package com.dryrunn.digital.hygiene.nodebee.backend.impl.mongo.entities

import com.dryrunn.digital.hygiene.nodebee.backend.INodeStore
import com.dryrunn.digital.hygiene.nodebee.backend.impl.mongo.entities.MongoChildren.Companion.toMongoNodeChildren
import com.dryrunn.digital.hygiene.nodebee.backend.impl.mongo.entities.MongoSibling.Companion.toMongoNodeSibling
import com.dryrunn.digital.hygiene.nodebee.backend.impl.mongo.interfaces.IMongoNodeData
import com.dryrunn.digital.hygiene.nodebee.interfaces.INodeData
import com.dryrunn.digital.hygiene.nodebee.structs.Children
import com.dryrunn.digital.hygiene.nodebee.structs.Node
import com.dryrunn.digital.hygiene.nodebee.structs.Sibling
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.index.Indexed
import org.springframework.data.mongodb.core.mapping.Document

/**
 * Project: node-bee
 * Author: vermakartik on 15/12/23
 */

@Document("node")
data class MongoNode<U, T : IMongoNodeData>(
    @Id
    @Indexed(unique = true)
    val nodeId : U?,
    val data : T?,
    val sibling : MongoSibling<U, T>,
    val parent : U?,
    val children : MongoChildren<U, T>,
    val version : Int
) {

    fun toNode() : Node<U, T> = Node(
        nodeId = nodeId,
        data = data,
        parent = parent,
        sibling = sibling.toNodeSibling(),
        children = children.toNodeChildren(),
        version = version
    )

    companion object {
        fun <U, T : IMongoNodeData> Node<U, T>.toMongoNode() = MongoNode(
            nodeId = nodeId,
            data = data,
            parent = parent,
            sibling = sibling.toMongoNodeSibling(),
            children = children.toMongoNodeChildren(),
            version = version
        )
    }

}