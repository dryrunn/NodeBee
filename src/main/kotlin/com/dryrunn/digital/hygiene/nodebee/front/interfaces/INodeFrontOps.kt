package com.dryrunn.digital.hygiene.nodebee.front.interfaces

import com.dryrunn.digital.hygiene.nodebee.backend.INodeStore
import com.dryrunn.digital.hygiene.nodebee.backend.ITransactional
import com.dryrunn.digital.hygiene.nodebee.backend.impl.UniqueIdFromBackendImpl
import com.dryrunn.digital.hygiene.nodebee.backend.impl.mongo.MongoTemplateBackendImpl
import com.dryrunn.digital.hygiene.nodebee.backend.impl.mongo.interfaces.IMongoNodeData
import com.dryrunn.digital.hygiene.nodebee.backend.impl.transactional.SequentialTransactionalImpl
import com.dryrunn.digital.hygiene.nodebee.configs.RootNodeDataConfig
import com.dryrunn.digital.hygiene.nodebee.context.ConfigContext
import com.dryrunn.digital.hygiene.nodebee.enums.OpType
import com.dryrunn.digital.hygiene.nodebee.exceptions.InsertionException
import com.dryrunn.digital.hygiene.nodebee.factory.provider.impl.NodeOpFactoryProvider
import com.dryrunn.digital.hygiene.nodebee.front.impl.NodeOpImpl
import com.dryrunn.digital.hygiene.nodebee.front.struct.NodeConfig
import com.dryrunn.digital.hygiene.nodebee.interfaces.INodeData
import com.dryrunn.digital.hygiene.nodebee.interfaces.IOpFactory
import com.dryrunn.digital.hygiene.nodebee.nodeOp.impl.IONodeFactory
import com.dryrunn.digital.hygiene.nodebee.sequenceGenerator.ISequenceGenerator
import com.dryrunn.digital.hygiene.nodebee.structs.Node
import com.dryrunn.digital.hygiene.nodebee.validator.INodeValidator
import com.dryrunn.digital.hygiene.nodebee.validator.ValidatorFactory
import org.springframework.data.mongodb.core.MongoTemplate

/**
 * Project: node-bee
 * Author: vermakartik on 16/12/23
 */

interface INodeFrontOps<U, T : INodeData> {

    fun insert(node : NodeConfig<U, T>) : Boolean
    fun remove(node : NodeConfig<U, T>) : Boolean
    fun move(from : NodeConfig<U, T>, to : NodeConfig<U, T>) : Boolean
    fun getByData(node : NodeConfig<U, T>) : NodeConfig<U, T>
    fun getChildrenForNode(node : NodeConfig<U, T>) : List<NodeConfig<U, T>>

    companion object {

        fun <U, T : INodeData> getNodeOp(
            sequenceGenerator : ISequenceGenerator<U, T>,
            configContext : ConfigContext<U, T>,
            backend: INodeStore<U, T>,
            transactional: ITransactional = SequentialTransactionalImpl(),
            validatorFactory : IOpFactory<T, OpType, INodeValidator<U, T>> = ValidatorFactory()
        ) : INodeFrontOps<U, T> {
            return NodeOpImpl(
                sequenceGenerator = sequenceGenerator,
                backend = backend,
                configContext = configContext,
                opFactory = IONodeFactory(
                    configContext = configContext,
                    factoryProvider = NodeOpFactoryProvider(
                        configContext = configContext,
                        backend = backend,
                        transactional = transactional,
                    ),
                    validatorFactory = validatorFactory
                )
            )
        }

        fun <U, T : INodeData> createNodeFrontOp(
            sequenceGenerator : ISequenceGenerator<U, T>,
            configContext : ConfigContext<U, T>,
            backend: INodeStore<U, T>,
            transactional: ITransactional = SequentialTransactionalImpl()
        ) : INodeFrontOps<U, T> {
            val op = getNodeOp(
                sequenceGenerator = sequenceGenerator,
                configContext = configContext,
                backend = backend,
                transactional = transactional,
            )
            initRootNode(backend, configContext)
            return op
        }

        fun <U, T : INodeData> getNodeConfigContext(rootNodeIdentifier : T) : ConfigContext<U, T> {
            return ConfigContext(
                rootNodeDataConfig = RootNodeDataConfig(rootNodeIdentifier)
            )
        }

        private fun <U, T : INodeData> initRootNode(backend : INodeStore<U, T>, configContext : ConfigContext<U, T>) {
            try {
                backend.getByData(Node.withNodeData(configContext.rootNodeDataConfig.rootNode.data!!))
            } catch (error : Exception) {
                println("Root Node Config Not Found.\nInserting New...")
                val nodeResult = backend.insert(Node.withNodeData(configContext.rootNodeDataConfig.rootNode.data!!))
                nodeResult.nodeId ?: throw InsertionException(configContext.rootNodeDataConfig.rootNode.data)
            }
        }

        fun <U, T: IMongoNodeData> getDefaultMongoNodeClient(
            sequenceGenerator: ISequenceGenerator<U, T>,
            mongoTemplate: MongoTemplate,
            configContext : ConfigContext<U, T>,
            transactional: ITransactional = SequentialTransactionalImpl()
        ) = createNodeFrontOp(
            configContext = configContext,
            backend = UniqueIdFromBackendImpl(MongoTemplateBackendImpl(mongoTemplate)),
            transactional = transactional,
            sequenceGenerator = sequenceGenerator
        )

    }
}