package com.dryrunn.digital.hygiene.nodebee.backend.impl.mongo.entities

import com.dryrunn.digital.hygiene.nodebee.backend.INodeStore
import com.dryrunn.digital.hygiene.nodebee.backend.impl.mongo.interfaces.IMongoNodeData
import com.dryrunn.digital.hygiene.nodebee.interfaces.INodeData
import com.dryrunn.digital.hygiene.nodebee.structs.Children
import com.dryrunn.digital.hygiene.nodebee.structs.Node
import com.dryrunn.digital.hygiene.nodebee.structs.Sibling
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document

/**
 * Project: node-bee
 * Author: vermakartik on 15/12/23
 */

data class MongoSibling<U, T : IMongoNodeData>(
    val next : U?,
    val previous : U?
) {

    fun toNodeSibling() = Sibling<U, T>(
        next = next,
        previous = previous
    )

    companion object {
        fun <U, T : IMongoNodeData> Sibling<U, T>.toMongoNodeSibling() = MongoSibling<U, T>(
            next = next,
            previous = previous
        )
    }
}