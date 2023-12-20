package com.dryrunn.digital.hygiene.nodebee.front.struct

import com.dryrunn.digital.hygiene.nodebee.interfaces.INodeData
import lombok.AllArgsConstructor
import lombok.Builder
import lombok.Getter
import lombok.NoArgsConstructor

/**
 * Project: node-bee
 * Author: vermakartik on 16/12/23
 */
@Builder
@Getter
data class NodeConfig<U, T : INodeData>(
    val current : T,
    val parent : T? = null,
    val next : T? = null,
    val previous : T? = null,
    val version : Int? = null,
)
