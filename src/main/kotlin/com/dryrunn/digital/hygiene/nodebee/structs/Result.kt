package com.dryrunn.digital.hygiene.nodebee.structs

import com.dryrunn.digital.hygiene.nodebee.enums.OpStatus
import lombok.Getter

/**
 * Project: node-bee
 * Author: vermakartik on 16/12/23
 */
@Getter
data class Result(
    val code: OpStatus,
    val message: String
)
