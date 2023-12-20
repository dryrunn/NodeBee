package com.dryrunn.digital.hygiene.nodebee.enums

import lombok.Getter

/**
 * Project: node-bee
 * Author: vermakartik on 15/12/23
 */
@Getter
enum class OpStatus(
    private val code : String
) {
    SUCCESS("00000200"),
    FAILURE("00000500")
}