package com.dryrunn.digital.hygiene.nodebee.validator

import com.dryrunn.digital.hygiene.nodebee.interfaces.INodeData
import com.dryrunn.digital.hygiene.nodebee.structs.Node

/**
 * Project: node-bee
 * Author: vermakartik on 21/12/23
 */
interface INodeValidator<U, T: INodeData> {
    fun validate(node : Node<U, T>) : Boolean
}