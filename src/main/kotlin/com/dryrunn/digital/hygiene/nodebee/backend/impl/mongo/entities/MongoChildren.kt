package com.dryrunn.digital.hygiene.nodebee.backend.impl.mongo.entities

import com.dryrunn.digital.hygiene.nodebee.backend.impl.mongo.interfaces.IMongoNodeData
import com.dryrunn.digital.hygiene.nodebee.interfaces.INodeData
import com.dryrunn.digital.hygiene.nodebee.structs.Children

/**
 * Project: node-bee
 * Author: vermakartik on 15/12/23
 */

data class MongoChildren<U, T : IMongoNodeData>(
    val data : Map<Int, U>
) {

    fun toNodeChildren() = Children<U, T>(
        data = data
    )

    companion object {
        fun <U, T : IMongoNodeData> Children<U, T>.toMongoNodeChildren() = MongoChildren<U, T>(data = data)
    }

}