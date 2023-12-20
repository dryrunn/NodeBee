package com.dryrunn.digital.hygiene.nodebee.configs

import com.dryrunn.digital.hygiene.nodebee.backend.INodeStore
import com.dryrunn.digital.hygiene.nodebee.backend.impl.UniqueIdFromBackendImpl
import com.dryrunn.digital.hygiene.nodebee.interfaces.INodeData
import lombok.Getter
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Primary

/**
 * Project: node-bee
 * Author: vermakartik on 16/12/23
 */
class BackendConfig<U, T : INodeData>(
    private val backend : INodeStore<U, T>
)

