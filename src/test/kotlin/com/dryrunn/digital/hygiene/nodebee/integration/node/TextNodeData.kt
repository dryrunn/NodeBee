package com.dryrunn.digital.hygiene.nodebee.integration.node

import com.dryrunn.digital.hygiene.nodebee.interfaces.INodeData

/**
 * Project: node-bee
 * Author: vermakartik on 19/12/23
 */
data class TextNodeData(
    val uniqueId: String,
    val value : String? = null
) : INodeData {
    override fun getUniqueValue(): String = uniqueId
}