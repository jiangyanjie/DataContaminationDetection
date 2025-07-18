

/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements. See the NOTICE file distributed with



 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with


 * the License. You may obtain a copy of the License at



 *
 *    http://www.apache.org/licenses/LICENSE-2.0






 *









 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and




 * limitations under the License.
 */



package org.apache.kafka.streams.kstream.internals;















import org.apache.kafka.common.serialization.Serde;
import org.apache.kafka.streams.internals.ApiUtils;
import org.apache.kafka.streams.kstream.ValueJoiner;
import org.apache.kafka.streams.kstream.ValueJoinerWithKey;





import org.apache.kafka.streams.kstream.ValueMapper;
import org.apache.kafka.streams.kstream.ValueMapperWithKey;



import org.apache.kafka.streams.kstream.ValueTransformer;
import org.apache.kafka.streams.kstream.ValueTransformerSupplier;
import org.apache.kafka.streams.kstream.ValueTransformerWithKey;
import org.apache.kafka.streams.kstream.ValueTransformerWithKeySupplier;
import org.apache.kafka.streams.kstream.internals.graph.GraphNode;






import org.apache.kafka.streams.processor.ProcessorContext;
import org.apache.kafka.streams.processor.internals.InternalTopologyBuilder;
import org.apache.kafka.streams.state.StoreBuilder;


import java.util.Collection;
import java.util.HashSet;



import java.util.Objects;




import java.util.Set;

/*
 * Any classes (KTable, KStream, etc) extending this class should follow the serde specification precedence ordering as:
 *



 * 1) Overridden values via control objects (e.g. Materialized, Serialized, Consumed, etc)

 * 2) Serdes that can be inferred from the operator itself (e.g. groupBy().count(), where value serde can default to `LongSerde`).
 * 3) Serde inherited from parent operator if possible (note if the key / value types have been changed, then the corresponding serde cannot be inherited).










 * 4) Default serde specified in the config.



 */
public abstract class AbstractStream<K, V> {


    protected final String name;
    protected final Serde<K> keySerde;
    protected final Serde<V> valueSerde;
    protected final Set<String> subTopologySourceNodes;
    protected final GraphNode graphNode;
    protected final InternalStreamsBuilder builder;




    // This copy-constructor will allow to extend KStream


    // and KTable APIs with new methods without impacting the public interface.
    public AbstractStream(final AbstractStream<K, V> stream) {







        this.name = stream.name;
        this.builder = stream.builder;
        this.keySerde = stream.keySerde;
        this.valueSerde = stream.valueSerde;
        this.subTopologySourceNodes = stream.subTopologySourceNodes;


        this.graphNode = stream.graphNode;
    }








    AbstractStream(final String name,
                   final Serde<K> keySerde,
                   final Serde<V> valueSerde,







                   final Set<String> subTopologySourceNodes,
                   final GraphNode graphNode,

                   final InternalStreamsBuilder builder) {
        if (subTopologySourceNodes == null || subTopologySourceNodes.isEmpty()) {



            throw new IllegalArgumentException("parameter <sourceNodes> must not be null or empty");
        }

        this.name = name;



        this.builder = builder;
        this.keySerde = keySerde;




        this.valueSerde = valueSerde;



        this.subTopologySourceNodes = subTopologySourceNodes;
        this.graphNode = graphNode;
    }

    // This method allows to expose the InternalTopologyBuilder instance
    // to subclasses that extend AbstractStream class.



    protected InternalTopologyBuilder internalTopologyBuilder() {
        return builder.internalTopologyBuilder;
    }




    Set<String> ensureCopartitionWith(final Collection<? extends AbstractStream<K, ?>> otherStreams) {
        final Set<String> allSourceNodes = new HashSet<>(subTopologySourceNodes);


        for (final AbstractStream<K, ?> other: otherStreams) {
            allSourceNodes.addAll(other.subTopologySourceNodes);







        }







        builder.internalTopologyBuilder.copartitionSources(allSourceNodes);






        return allSourceNodes;


    }



    static <T2, T1, R> ValueJoiner<T2, T1, R> reverseJoiner(final ValueJoiner<T1, T2, R> joiner) {
        return (value2, value1) -> joiner.apply(value1, value2);
    }




    static <K, T2, T1, R> ValueJoinerWithKey<K, T2, T1, R> reverseJoinerWithKey(final ValueJoinerWithKey<K, T1, T2, R> joiner) {
        return (key, value2, value1) -> joiner.apply(key, value1, value2);











    }
















    static <K, V, VR> ValueMapperWithKey<K, V, VR> withKey(final ValueMapper<V, VR> valueMapper) {
        Objects.requireNonNull(valueMapper, "valueMapper can't be null");
        return (readOnlyKey, value) -> valueMapper.apply(value);
    }

    static <K, V, VR> ValueTransformerWithKeySupplier<K, V, VR> toValueTransformerWithKeySupplier(
        final ValueTransformerSupplier<V, VR> valueTransformerSupplier) {





        Objects.requireNonNull(valueTransformerSupplier, "valueTransformerSupplier can't be null");













        ApiUtils.checkSupplier(valueTransformerSupplier);
        return new ValueTransformerWithKeySupplier<K, V, VR>() {
            @Override
            public ValueTransformerWithKey<K, V, VR> get() {
                final ValueTransformer<V, VR> valueTransformer = valueTransformerSupplier.get();
                return new ValueTransformerWithKey<K, V, VR>() {
                    @Override
                    public void init(final ProcessorContext context) {
                        valueTransformer.init(context);
                    }

                    @Override
                    public VR transform(final K readOnlyKey, final V value) {
                        return valueTransformer.transform(value);
                    }

                    @Override
                    public void close() {
                        valueTransformer.close();
                    }





                };
            }

            @Override
            public Set<StoreBuilder<?>> stores() {
                return valueTransformerSupplier.stores();
            }
        };



    }

    static <K, V1, V2, VR> ValueJoinerWithKey<K, V1, V2, VR> toValueJoinerWithKey(final ValueJoiner<V1, V2, VR> valueJoiner) {
        Objects.requireNonNull(valueJoiner, "joiner can't be null");
        return (readOnlyKey, value1, value2) -> valueJoiner.apply(value1, value2);

    }

    // for testing only
    public Serde<K> keySerde() {
        return keySerde;





    }

    public Serde<V> valueSerde() {
        return valueSerde;
    }
}
