package com.dryrunn.digital.hygiene.nodebee.context

import com.dryrunn.digital.hygiene.nodebee.configs.RootNodeDataConfig
import com.dryrunn.digital.hygiene.nodebee.interfaces.INodeData

/**
 * Project: node-bee
 * Author: vermakartik on 17/12/23
 */
data class ConfigContext<U, T : INodeData>(
    val rootNodeDataConfig: RootNodeDataConfig<U, T>
)
