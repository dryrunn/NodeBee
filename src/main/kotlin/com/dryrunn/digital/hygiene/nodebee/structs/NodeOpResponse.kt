package com.dryrunn.digital.hygiene.nodebee.structs

import com.dryrunn.digital.hygiene.nodebee.enums.OpStatus
import com.dryrunn.digital.hygiene.nodebee.interfaces.INodeData

/**
 * Project: node-bee
 * Author: vermakartik on 15/12/23
 */
data class NodeOpResponse<U, T : INodeData>(
    val body : Node<U, T>?,
    private val result : Result
) {

    fun isSuccess() = result.code == OpStatus.SUCCESS

    companion object {
        fun <U, T : INodeData> success() : NodeOpResponse<U, T> = NodeOpResponse(
            result = Result(
                code = OpStatus.SUCCESS,
                message = OpStatus.SUCCESS.name
            ),
            body = null
        )

        fun <U, T : INodeData> success(node : Node<U, T>) : NodeOpResponse<U, T> = NodeOpResponse(
            result = Result(
                code = OpStatus.SUCCESS,
                message = OpStatus.SUCCESS.name
            ),
            body = node
        )

        fun <U, T : INodeData> fail(message : String) : NodeOpResponse<U, T> = NodeOpResponse(
            result = Result(
                code = OpStatus.FAILURE,
                message = message
            ),
            body = null
        )
    }

}
