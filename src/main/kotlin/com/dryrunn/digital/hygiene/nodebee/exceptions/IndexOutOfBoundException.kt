package com.dryrunn.digital.hygiene.nodebee.exceptions

/**
 * Project: node-bee
 * Author: vermakartik on 16/12/23
 */
class IndexOutOfBoundException(pos : Int) : BaseNodeException("${pos} is out of the available range!")