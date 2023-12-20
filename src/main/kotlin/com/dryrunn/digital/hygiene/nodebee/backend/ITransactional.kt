package com.dryrunn.digital.hygiene.nodebee.backend

import com.dryrunn.digital.hygiene.nodebee.interfaces.INodeData

/**
 * Project: node-bee
 * Author: vermakartik on 16/12/23
 */
interface ITransactional {
    fun ops(vararg op : () -> Unit)
}