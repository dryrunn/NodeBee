package com.dryrunn.digital.hygiene.nodebee.structs.extensions

import com.dryrunn.digital.hygiene.nodebee.interfaces.INodeData
import com.dryrunn.digital.hygiene.nodebee.structs.Children

fun <U, T : INodeData> Children<U, T>.appendFirst(nodeId : U) : Children<U, T> = appendAtPos(0, nodeId)
fun <U, T : INodeData> Children<U, T>.appendLast(nodeId : U) : Children<U, T> = appendAtPos(this.data.size, nodeId)
fun <U, T : INodeData> Children<U, T>.appendAtPos(pos : Int, nodeId : U) : Children<U, T> = this.copy(
    data = this.data.run {
        val nMap = mutableMapOf<Int, U>()
        nMap[pos] = nodeId
        this.entries.stream().forEach {
            if(it.key < pos) nMap[it.key] = it.value
            else nMap[it.key + 1] = it.value
        }

        return@run nMap
    }
)

fun <U, T : INodeData> Children<U, T>.removeFirst() : Children<U, T> = removeAtPos(0)
fun <U, T : INodeData> Children<U, T>.removeLast() : Children<U, T> = removeAtPos(this.data.size - 1)
fun <U, T : INodeData> Children<U, T>.removeAtPos(pos : Int) : Children<U, T> = this.copy(
    data = this.data.run {
        return@run this.entries
            .filter { it.key == pos }
            .associate {
                if(it.key < pos) return@associate it.key to it.value
                else return@associate (it.key - 1) to it.value
            }.toMap()
    }
)
