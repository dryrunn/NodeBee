package com.dryrunn.digital.hygiene.nodebee.backend.impl.transactional

import com.dryrunn.digital.hygiene.nodebee.backend.ITransactional

/**
 * Project: node-bee
 * Author: vermakartik on 18/12/23
 */
class SequentialTransactionalImpl : ITransactional {
    override fun ops(vararg op: () -> Unit) = op.forEach { it() }
}