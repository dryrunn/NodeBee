package com.dryrunn.digital.hygiene.nodebee.validator.impl

import com.dryrunn.digital.hygiene.nodebee.interfaces.INodeData
import com.dryrunn.digital.hygiene.nodebee.structs.Node
import com.dryrunn.digital.hygiene.nodebee.validator.INodeValidator

/**
 * Project: node-bee
 * Author: vermakartik on 21/12/23
 */
class NodeDeleteParentAndSiblingValidatorImpl<U, T: INodeData> : INodeValidator<U, T> {

    /**
     *
     * Validate following relations
     *
     * 1. Parent should exist
     * 2. If siblings exist
     *      1. they should be child of the mentioned parent
     *      2. if both exists
     *          1. they should connect to each other
     *      3. If previous exists only:
     *             its next should be null
     *      4. if next exists only:
     *              its previous should be null
     *
     */

    override fun validate(node: Node<U, T>): Boolean {

        val parent = node.parent(null)!!
        val next = node.sibling.next(null)
        val previous = node.sibling.previous(null)

        return assume { node.parent != null }
            && assume { parent.children.data.containsValue(node.nodeId) }
            && assume { (node.sibling.next != null && next != null) || (node.sibling.next == null && next == null) }
            && assume { (node.sibling.previous != null && previous != null) || (node.sibling.previous == null && previous == null)  }
            && assume { previous?.run { parent.children.data.containsValue(nodeId!!) } ?: true }
            && assume { next?.run { parent.children.data.containsValue(nodeId!!) } ?: true }
            && assume { previous?.run { previous.sibling.next == node.nodeId } ?: true }
            && assume { next?.run { next.sibling.next == node } ?: true }
    }

    private fun assume(expr : () -> Boolean) :Boolean {
        return expr()
    }
}