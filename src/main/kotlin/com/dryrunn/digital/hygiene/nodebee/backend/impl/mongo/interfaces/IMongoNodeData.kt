package com.dryrunn.digital.hygiene.nodebee.backend.impl.mongo.interfaces

import com.dryrunn.digital.hygiene.nodebee.interfaces.INodeData

/**
 * Project: node-bee
 * Author: vermakartik on 19/12/23
 */
interface IMongoNodeData : INodeData {
    fun getUniqueIdName(): String
}