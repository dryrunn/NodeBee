package com.dryrunn.digital.hygiene.nodebee.backend.impl.mongo

import com.dryrunn.digital.hygiene.nodebee.backend.INodeStore
import com.dryrunn.digital.hygiene.nodebee.backend.impl.mongo.entities.MongoNode
import com.dryrunn.digital.hygiene.nodebee.backend.impl.mongo.entities.MongoNode.Companion.toMongoNode
import com.dryrunn.digital.hygiene.nodebee.backend.impl.mongo.interfaces.IMongoNodeData
import com.dryrunn.digital.hygiene.nodebee.structs.Node
import org.bson.Document
import org.springframework.data.mongodb.core.MongoTemplate
import org.springframework.data.mongodb.core.query.Criteria
import org.springframework.data.mongodb.core.query.Query
import org.springframework.data.mongodb.core.query.Update

/**
 * Project: node-bee
 * Author: vermakartik on 16/12/23
 */
open class MongoTemplateBackendImpl<U, T : IMongoNodeData>(
    private val mongoTemplate : MongoTemplate
) : INodeStore<U, T> {

    override fun insert(node: Node<U, T>): Node<U, T> {
        return mongoTemplate.insert(node.toMongoNode()).toNode()
    }

    override fun remove(node: Node<U, T>): Boolean {
        return mongoTemplate.remove(node.toMongoNode()).run { deletedCount > 0 }
    }

    override fun update(node: Node<U, T>): Boolean {
        val mongoNode = node.toMongoNode()
        mongoTemplate.findAndModify(
            Query().apply {
                addCriteria(Criteria.where("nodeId").`is`(mongoNode.nodeId!!))
            },
            Update.fromDocument(Document().apply { mongoTemplate.converter.write(mongoNode, this) }),
            MongoNode::class.java
        )
        return true
    }

    @Suppress("UNCHECKED_CAST")
    override fun get(node: Node<U, T>): Node<U, T> {
        return (
            mongoTemplate.findOne(
                Query().apply {
                    addCriteria(Criteria.where("nodeId").`is`(node.nodeId!!))
                },
                MongoNode::class.java
            ) as MongoNode<U, T>
        ).toNode()
    }

    @Suppress("UNCHECKED_CAST")
    override fun getByData(node: Node<U, T>): Node<U, T> {
        return (
            mongoTemplate.findOne(
                Query().apply {
                    addCriteria(Criteria.where(node.data!!.getUniqueIdName()).`is`(node.data.getUniqueValue()))
                },
                MongoNode::class.java
            ) as MongoNode<U, T>
        ).toNode()
    }
}