package com.dryrunn.digital.hygiene.nodebee.integration.ops.insert

import com.dryrunn.digital.hygiene.nodebee.front.struct.NodeConfig
import com.dryrunn.digital.hygiene.nodebee.integration.config.INodeFrontOpConfig
import com.dryrunn.digital.hygiene.nodebee.integration.node.TextNodeData
import com.dryrunn.digital.hygiene.nodebee.interfaces.INodeData
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertDoesNotThrow
import org.w3c.dom.Text

/**
 * Project: node-bee
 * Author: vermakartik on 19/12/23
 */

class InsertOpTest {

    /**
     * Expected Tree Insert Operations:
     *
     *                                      $$ROOT$$
     *                                     /
     *                                    a1->a2->a3---------------
     *                                   /                         \
     *                                  a13->a4->a14->a5->a6       a7->a10->a11
         *                                                /         \
         *                                              a12         a8->a15->a9
     *
     */

//     TODO(validate the the next and previous mentioned belong to the mentioned parent and in the same order as mentioned)

    @Test
    fun testInsertChild_successTree() {

        val a1 = NodeConfig<Int, TextNodeData>(current = TextNodeData("a1"))
        val a2 = NodeConfig<Int, TextNodeData>(current = TextNodeData("a2"), previous = a1.current)
        val a3 = NodeConfig<Int, TextNodeData>(current = TextNodeData("a3"), previous = a2.current)
        val a4 = NodeConfig<Int, TextNodeData>(current = TextNodeData("a4"), parent = a1.current)
        val a5 = NodeConfig<Int, TextNodeData>(current = TextNodeData("a5"), previous = a4.current, parent = a1.current)
        val a6 = NodeConfig<Int, TextNodeData>(current = TextNodeData("a6"), previous = a5.current, parent = a1.current)
        val a7 = NodeConfig<Int, TextNodeData>(current = TextNodeData("a7"), parent = a3.current)
        val a8 = NodeConfig<Int, TextNodeData>(current = TextNodeData("a8"), parent = a7.current)
        val a9 = NodeConfig<Int, TextNodeData>(current = TextNodeData("a9"), parent = a7.current, previous = a8.current)
        val a10 = NodeConfig<Int, TextNodeData>(current = TextNodeData("a10"), parent = a3.current, previous = a7.current)
        val a11 = NodeConfig<Int, TextNodeData>(current = TextNodeData("a11"), parent = a3.current, previous = a10.current)
        val a12 = NodeConfig<Int, TextNodeData>(current = TextNodeData("a12"), parent = a6.current)
        val a13 = NodeConfig<Int, TextNodeData>(current = TextNodeData("a13"), parent = a1.current, next = a4.current)
        val a14 = NodeConfig<Int, TextNodeData>(current = TextNodeData("a14"), parent = a1.current, previous = a4.current, next = a5.current)
        val a15 = NodeConfig<Int, TextNodeData>(current = TextNodeData("a15"), parent = a7.current, previous = a8.current, next = a9.current)

        Assertions.assertTrue(INodeFrontOpConfig.textNode.insert(a1))
        assertNodeDataAllEquals(2, 0, 1, a1)

        Assertions.assertTrue(INodeFrontOpConfig.textNode.insert(a2))
        assertNodeDataAllEquals(3, 1, 2, a2)

        Assertions.assertTrue(INodeFrontOpConfig.textNode.insert(a3))
        assertNodeDataAllEquals(4, 2, 3, a3)

        Assertions.assertTrue(INodeFrontOpConfig.textNode.insert(a4))
        assertNodeDataAllEquals(5, 0, 4, a4)

        Assertions.assertTrue(INodeFrontOpConfig.textNode.insert(a5))
        assertNodeDataAllEquals(6, 1, 5, a5)

        Assertions.assertTrue(INodeFrontOpConfig.textNode.insert(a6))
        assertNodeDataAllEquals(7, 2, 6, a6)

        Assertions.assertTrue(INodeFrontOpConfig.textNode.insert(a7))
        assertNodeDataAllEquals(8, 0, 7, a7)

        Assertions.assertTrue(INodeFrontOpConfig.textNode.insert(a8))
        assertNodeDataAllEquals(9, 0, 8, a8)

        Assertions.assertTrue(INodeFrontOpConfig.textNode.insert(a9))
        assertNodeDataAllEquals(10, 1, 9, a9)

        Assertions.assertTrue(INodeFrontOpConfig.textNode.insert(a10))
        assertNodeDataAllEquals(11, 1, 10, a10)

        Assertions.assertTrue(INodeFrontOpConfig.textNode.insert(a11))
        assertNodeDataAllEquals(12, 2, 11, a11)

        Assertions.assertTrue(INodeFrontOpConfig.textNode.insert(a12))
        assertNodeDataAllEquals(13, 0, 12, a12)

        Assertions.assertTrue(INodeFrontOpConfig.textNode.insert(a13))
        assertNodeDataAllEquals(14, 0, 13, a13)

        Assertions.assertTrue(INodeFrontOpConfig.textNode.insert(a14))
        assertNodeDataAllEquals(15, 2, 14, a14)

        Assertions.assertTrue(INodeFrontOpConfig.textNode.insert(a15))
        assertNodeDataAllEquals(16, 1, 15, a15)
    }

    @BeforeEach
    fun initBeforeEach() {
        INodeFrontOpConfig.reInit()
    }

    /**
     * Expected Tree Insert Operations:
     *
     *                                      $$ROOT$$
     *                                     /
     *                                    a1->a2
     *                                    /
     *                                    a3->a5->a4
     *
     *
     */

    /**
     *
     *  Conditions to be evaluated for next/previous/parent
     *
     */


    @Test
    fun testInsertChild_ParentDoesNotContainsChild() {

        val a1 = NodeConfig<Int, TextNodeData>(current = TextNodeData("a1"))
        val a2 = NodeConfig<Int, TextNodeData>(current = TextNodeData("a2"), previous = a1.current)
        val a3 = NodeConfig<Int, TextNodeData>(current = TextNodeData("a3"), parent = a1.current)

        Assertions.assertTrue(INodeFrontOpConfig.textNode.insert(a1))
        assertNodeDataAllEquals(2, 0, 1, a1)

        Assertions.assertTrue(INodeFrontOpConfig.textNode.insert(a2))
        assertNodeDataAllEquals(3, 1, 2, a2)

        Assertions.assertTrue(INodeFrontOpConfig.textNode.insert(a3))
        assertNodeDataAllEquals(4, 2, 3, a3)

    }


    /**
     *
     * Check if a node has the same siblings and position in parent as expected
     *               --parent--
     *               /
     *            Start->...<previous-$node$-next> ->...->end
     *                              (nodeData)
     *
     */
    private fun assertNodeDataAllEquals(
        mapSize: Int,
        parentPosition : Int,
        key: Int,
        nodeConfig: NodeConfig<Int, TextNodeData>
    ) {
//        1. Check if nodes data matches the data expected
        assertNodeDataCurrentEquals(mapSize, key, nodeConfig)

        val currentNode = INodeFrontOpConfig.backendImpl.mapBackend[key]!!

//        2. Check if the nodes parent, siblings are as expected stored
//          if null then null should be stored else the expected value
        Assertions.assertNotNull(currentNode.parent)
        nodeConfig.next?.let {
            Assertions.assertNotNull(currentNode.sibling.next)
        } ?: Assertions.assertNull(currentNode.sibling.next)
        nodeConfig.previous?.let {
            Assertions.assertNotNull(currentNode.sibling.previous)
        } ?: Assertions.assertNull(currentNode.sibling.previous)

//        confirm parent contains the node at the expected position
        nodeConfig.parent?.let {
            assertNodeDataCurrentEquals(mapSize, currentNode.parent!!, NodeConfig(current = it))
            assertParentChildrenRelationship(parentPosition, currentNode.parent!!, key)
        } ?: {
            assertNodeDataCurrentEquals(mapSize, 0, INodeFrontOpConfig.parent)
            assertParentChildrenRelationship(parentPosition, 0, key)
        }

//        confirm previous contains the next as the current Node and the data is as expected
        currentNode.sibling.previous?.let {
            assertNodeDataCurrentEquals(mapSize, it, NodeConfig(nodeConfig.previous!!))
            assertPreviousSiblingRelationShip(it, key)
        }

//        confirm next contains the previous as the current Node and the data is as expected
        currentNode.sibling.next?.let {
            assertNodeDataCurrentEquals(mapSize, it, NodeConfig(nodeConfig.next!!))
            assertNextSiblingRelationShip(it, key)
        }

    }

    private fun assertPreviousSiblingRelationShip(previousKey : Int, currentKey : Int) {
        val previousNode = INodeFrontOpConfig.backendImpl.mapBackend[previousKey]!!
        val currentNode = INodeFrontOpConfig.backendImpl.mapBackend[currentKey]!!

        Assertions.assertEquals(currentKey, previousNode.sibling.next)
        Assertions.assertEquals(previousKey, currentNode.sibling.previous)
    }

    private fun assertNextSiblingRelationShip(nextKey : Int, currentKey : Int) {
        val nextNode = INodeFrontOpConfig.backendImpl.mapBackend[nextKey]!!
        val currentNode = INodeFrontOpConfig.backendImpl.mapBackend[currentKey]!!

        Assertions.assertEquals(currentKey, nextNode.sibling.previous)
        Assertions.assertEquals(nextKey, currentNode.sibling.next)
    }

    private fun assertParentChildrenRelationship(position : Int, parentKey : Int, childKey : Int) {
        val parentNode = INodeFrontOpConfig.backendImpl.mapBackend[parentKey]!!
        Assertions.assertTrue(parentNode.children.data.containsKey(position))
        Assertions.assertTrue(parentNode.children.data[position] == childKey)
    }

    private fun assertNodeDataCurrentEquals(mapSize : Int, key : Int, nodeConfig : NodeConfig<Int, TextNodeData>) {
        Assertions.assertTrue(INodeFrontOpConfig.backendImpl.mapBackend.containsKey(key))
        Assertions.assertEquals(mapSize, INodeFrontOpConfig.backendImpl.mapBackend.size)
        Assertions.assertEquals(key, INodeFrontOpConfig.backendImpl.mapBackend[key]!!.nodeId)
        Assertions.assertEquals(nodeConfig.current.getUniqueValue(), INodeFrontOpConfig.backendImpl.mapBackend[key]!!.data!!.getUniqueValue())
    }

    private fun verifyContainsNodes(vararg nodeData : String) {
        assertDoesNotThrow { nodeData.map { n -> INodeFrontOpConfig.backendImpl.mapBackend.entries.first { it.value.data!!.getUniqueValue() == n } } }
    }

    companion object {


        @JvmStatic
        @BeforeAll
        fun initTree(): Unit {
            INodeFrontOpConfig.textNode.getByData(INodeFrontOpConfig.textNode.getByData(NodeConfig(current = TextNodeData("\$\$ROOT\$\$"))))
        }
    }

}