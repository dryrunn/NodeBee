package com.dryrunn.digital.hygiene.nodebee.validator

import com.dryrunn.digital.hygiene.nodebee.enums.OpType
import com.dryrunn.digital.hygiene.nodebee.interfaces.INodeData
import com.dryrunn.digital.hygiene.nodebee.interfaces.IOpFactory
import com.dryrunn.digital.hygiene.nodebee.structs.Node
import com.dryrunn.digital.hygiene.nodebee.validator.impl.NodeDeleteParentAndSiblingValidatorImpl
import com.dryrunn.digital.hygiene.nodebee.validator.impl.NodeInsertParentAndSiblingValidatorImpl
import kotlin.reflect.KClass

/**
 * Project: node-bee
 * Author: vermakartik on 21/12/23
 */
class ValidatorFactory<U, T : INodeData> : IOpFactory<T, OpType, INodeValidator<U, T>> {

    private val instanceMap = mapOf<OpType, INodeValidator<U, T>>(
        OpType.INSERT to NodeInsertParentAndSiblingValidatorImpl(),
        OpType.DELETE to NodeDeleteParentAndSiblingValidatorImpl()
    )

    override fun getImpl(input: OpType): INodeValidator<U, T> = instanceMap[input]!!
}