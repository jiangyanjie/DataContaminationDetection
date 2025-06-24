package com.scottbyrns.ml.neural;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Weight Matrix.
 * 
 * @author Scott Byrns
 * Date: Nov 11, 2010
 * Time: 5:42:42 PM
 *
 * @version 1.0
 */
public class DefaultSynapseLayer implements SynapseLayer {

    private List<Synapse> synapses;

    public DefaultSynapseLayer() {
        setSynapses(new ArrayList<Synapse>());
    }

    /**
     * Add a synapse to the layer.
     *
     * @param synapse to add
     */
    public void addSynapse(Synapse synapse) {
        getSynapses().add(synapse);
    }

    /**
     * Get an iterator for the synapse vector.
     *
     * @return iterator
     */
    public Iterator<Synapse> getSynapsesIterator () {
        return getSynapses().iterator();
    }

    /**
     * Get a weight vector representing the weights of the synapses in this layer.
     *
     * @return Vector of weight values, null in case of error.
     */
    public List<Double> getWeightVector() {
        try {
			List<Double> list = new ArrayList<Double>();
            Iterator<Synapse> synapseIterator = getSynapsesIterator();

            while (synapseIterator.hasNext()) {
                list.add(synapseIterator.next().getWeight());
            }

			return list;
        }
        catch (RuntimeException e) {
            return null;
        }
    }

    /**
     * Set the weights of the synapses in this layer to the next values
     * of the provided weightVectorIterator
     *
     * @param weightVectorIterator to iterate over for new weight values.
     */
    public void setWeightVector (Iterator<Double> weightVectorIterator) {
        Iterator<Synapse> synapseIterator = getSynapsesIterator();

        while (synapseIterator.hasNext() && weightVectorIterator.hasNext()) {
            synapseIterator.next().setWeight(weightVectorIterator.next());
        }
    }

    /**
     * Get the synapse vector
     *
     * @return synapse vector
     */
    private List<Synapse> getSynapses() {
        return synapses;
    }

    /**
     * Set the synapse vector to the provided input vector.
     *
     * @param synapses new synapse vector.
     */
    private void setSynapses(List<Synapse> synapses) {
        this.synapses = synapses;
    }
}
