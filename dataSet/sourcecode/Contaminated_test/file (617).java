package ru.ifmo.enf.kogan.t07;

import ru.ifmo.enf.kogan.t05.DecisionTree;
import ru.ifmo.enf.kogan.t05.DecisionTreeImpl;
import ru.ifmo.enf.kogan.t05.TreeNode;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by arsenykogan on 15/03/14.
 */
public class DecisionTreeBuilderImpl<E extends Enum<E>> implements DecisionTreeBuilder<E> {

    @Override
    public DecisionTree<E> buildTree(final List<DataItem<E>> dataItems, final List<DecisionTree.Factor> factors) {
        return new DecisionTreeImpl<E>(splitTree(dataItems, factors));
    }

    private TreeNode<E> splitTree(final List<DataItem<E>> dataItems, final List<DecisionTree.Factor> factors) {
        final double currentGini = countGini(dataItems);
        double bestInfoGain = 0;
        DecisionTree.Factor factor = null;
        DataSplit<E> bestDataSplit = null;

        /* Choose factor with best information gain. */
        for (final DecisionTree.Factor fac : factors) {
            final DataSplit<E> dataSplit = new DataSplit<E>(dataItems, fac);
            final List<DataItem<E>> trueItems = dataSplit.getTrueItems();
            final List<DataItem<E>> falseItems = dataSplit.getFalseItems();

            /* Calculate information gain:
            * G(A) - (size(At)/size(A))*G(At) - (size(Af)/size(A))*G(Af) */
            double infoGain = countGini(dataItems)
                    - (trueItems.size() / dataItems.size() * countGini(trueItems))
                    - (falseItems.size() / dataItems.size() * countGini(falseItems));
            if (infoGain > bestInfoGain && !trueItems.isEmpty() && !falseItems.isEmpty()) {
                bestDataSplit = dataSplit;
                bestInfoGain = infoGain;
                factor = fac;
            }
        }

        if (factor != null) {
            final TreeNode<E> trueNode = splitTree(bestDataSplit.getTrueItems(), factors);
            final TreeNode<E> falseNode = splitTree(bestDataSplit.getFalseItems(), factors);
            return new TreeNode<E>(factor, falseNode, trueNode);
        } else {
            return new TreeNode<E>(dataItems.get(0).category());
        }
    }

    private double countGini(final List<DataItem<E>> items) {
        int countUnequal = 0;
        for (final DataItem<E> item : items) {
            for (final DataItem<E> inItem : items) {
                if (!item.category().equals(inItem.category())) {
                    countUnequal++;
                }
            }
        }

        /* Number of all unequal pairs divided by all possible pairs */
        return (double) countUnequal / (items.size() * items.size());
    }

    /* Splits items in two lists by specified factor
    * and stores these two parts. */
    private class DataSplit<E extends Enum<E>> {

        private final List<DataItem<E>> trueItems;
        private final List<DataItem<E>> falseItems;

        private DataSplit(final List<DataItem<E>> items, final DecisionTree.Factor factor) {
            trueItems = new ArrayList<DataItem<E>>();
            falseItems = new ArrayList<DataItem<E>>();

            /* Split items in two lists by specified factor */
            for (final DataItem<E> item : items) {
                if (factor.is(item.entity())) {
                    trueItems.add(item);
                } else {
                    falseItems.add(item);
                }
            }
        }

        private List<DataItem<E>> getTrueItems() {
            return trueItems;
        }

        private List<DataItem<E>> getFalseItems() {
            return falseItems;
        }
    }
}
