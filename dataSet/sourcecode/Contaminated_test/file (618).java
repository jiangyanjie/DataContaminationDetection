package ru.ifmo.enf.kogan.t07;

import junit.framework.Assert;
import org.junit.Test;
import ru.ifmo.enf.kogan.t05.DecisionTree;
import ru.ifmo.enf.kogan.t05.Entity;
import ru.ifmo.enf.kogan.t05.NodeFactor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by arsenykogan on 15/03/14.
 */
public class DecisionTreeBuilderTest extends Assert {

    public enum Category {
        PREMIUM, BASIC, NONE;
    }

    @Test
    public void simpleTree() {
        final List<DecisionTreeBuilder.DataItem<Category>> items = new ArrayList<DecisionTreeBuilder.DataItem<Category>>() {
            {
                add(new DataItem<Category>(getEntityByAttributes("country", "usa", "faq", ">20"), Category.PREMIUM));
                add(new DataItem<Category>(getEntityByAttributes("country", "usa", "faq", ",<20"), Category.BASIC));
                add(new DataItem<Category>(getEntityByAttributes("country", "germany", "faq", ">10"), Category.BASIC));
                add(new DataItem<Category>(getEntityByAttributes("country", "france", "faq", "<10"), Category.NONE));
            }
        };

        final List<DecisionTree.Factor> factors = new ArrayList<DecisionTree.Factor>() {
            {
                add(new NodeFactor("from usa", "country", "usa"));
                add(new NodeFactor("faq > 20", "faq", ">20"));
                add(new NodeFactor("faq > 10", "faq", ">10"));
            }
        };

        final DecisionTreeBuilder<Category> treeBuilder = new DecisionTreeBuilderImpl<Category>();
        final DecisionTree<Category> decisionTree = treeBuilder.buildTree(items, factors);
        final Category category = decisionTree.getCategory(getEntityByAttributes("country", "germany", "faq", ">10"));
        assertEquals(Category.BASIC, category);

    }

    @Test
    public void accountTree() {
        final List<DecisionTreeBuilder.DataItem<Category>> items = new ArrayList<DecisionTreeBuilder.DataItem<Category>>() {
            {
                add(new DataItem<Category>(getEntityByAttributes("from", "slashdot", "country", "usa", "faq", "yes", "pages", "18"),
                        Category.NONE));
                add(new DataItem<Category>(getEntityByAttributes("from", "google", "country", "france", "faq", "yes", "pages", "23"),
                        Category.PREMIUM));
                add(new DataItem<Category>(getEntityByAttributes("from", "digg", "country", "usa", "faq", "yes", "pages", "24"),
                        Category.BASIC));
                add(new DataItem<Category>(getEntityByAttributes("from", "kiwitobes", "country", "france", "faq", "yes", "pages", "23"),
                        Category.BASIC));
                add(new DataItem<Category>(getEntityByAttributes("from", "google", "country", "gb", "faq", "no", "pages", "21"),
                        Category.PREMIUM));
                add(new DataItem<Category>(getEntityByAttributes("from", "-", "country", "nz", "faq", "no", "pages", "12"),
                        Category.NONE));
                add(new DataItem<Category>(getEntityByAttributes("from", "-", "country", "gb", "faq", "no", "pages", "21"),
                        Category.BASIC));
                add(new DataItem<Category>(getEntityByAttributes("from", "google", "country", "usa", "faq", "no", "pages", "24"),
                        Category.PREMIUM));
                add(new DataItem<Category>(getEntityByAttributes("from", "slashdot", "country", "france", "faq", "yes", "pages", "19"),
                        Category.NONE));
                add(new DataItem<Category>(getEntityByAttributes("from", "digg", "country", "usa", "faq", "no", "pages", "18"),
                        Category.NONE));
                add(new DataItem<Category>(getEntityByAttributes("from", "google", "country", "gb", "faq", "no", "pages", "18"),
                        Category.NONE));
                add(new DataItem<Category>(getEntityByAttributes("from", "kiwitobes", "country", "gb", "faq", "no", "pages", "19"),
                        Category.NONE));
                add(new DataItem<Category>(getEntityByAttributes("from", "digg", "country", "nz", "faq", "yes", "pages", "12"),
                        Category.BASIC));
                add(new DataItem<Category>(getEntityByAttributes("from", "google", "country", "gb", "faq", "yes", "pages", "18"),
                        Category.BASIC));
                add(new DataItem<Category>(getEntityByAttributes("from", "kiwitobes", "country", "france", "faq", "yes", "pages", "19"),
                        Category.BASIC));
            }
        };

        final List<DecisionTree.Factor> factors = new ArrayList<DecisionTree.Factor>() {
            {
                addAll(getAllFactors("from", "google", "digg","slashdot", "kiwitobes", "-"));
                addAll(getAllFactors("country", "usa", "france", "gb", "nz"));
                addAll(getAllFactors("faq", "yes"));
                addAll(getAllNumericFactors("pages", 12, 24));

            }
        };

        final DecisionTreeBuilder<Category> treeBuilder = new DecisionTreeBuilderImpl<Category>();
        final DecisionTree<Category> decisionTree = treeBuilder.buildTree(items, factors);
        assertEquals(Category.NONE,
                decisionTree.getCategory(getEntityByAttributes("from", "slashdot", "country", "usa", "faq", "yes", "pages", "18")));
        assertEquals(Category.PREMIUM,
                decisionTree.getCategory(getEntityByAttributes("from", "google", "country", "france", "faq", "yes", "pages", "23")));
        assertEquals(Category.BASIC,
                decisionTree.getCategory(getEntityByAttributes("from", "digg", "country", "usa", "faq", "yes", "pages", "24")));

    }

    /*
    * Converts human notation (e.g. "color", "red", "size", "small") in HashMap.
    * Splits list of attributes into pairs and adds them to HashMap.
    * Then returns new entity with desired attributes. */
    private DecisionTree.Entity getEntityByAttributes(final String... attributesList) {

        final HashMap<String, String> attributes = new HashMap<String, String>();
        for (int i = 0; i < attributesList.length / 2; i++) {
            attributes.put(attributesList[i * 2], attributesList[i * 2 + 1]);
        }
        // Creating entity with these attributes
        final DecisionTree.Entity entity = new Entity<Category>(attributes);
        return entity;
    }

    /*
    * Returns a list of all possible factors by given values and key name. */
    private List<DecisionTree.Factor> getAllFactors(final String key, final String... possibleValues) {
        final List<DecisionTree.Factor> factors = new ArrayList<DecisionTree.Factor>();
        for (final String value : possibleValues) {
            factors.add(new NodeFactor(key + "=" + value, key, value));
        }
        return factors;
    }

    /*
    * Creates numeric factors for range [from : to].
     * E.g. getAllNumericFactors("pages", 1, 3) gives
     * three factors: ">1" , ">2" and ">3" */
    private List<DecisionTree.Factor> getAllNumericFactors(final String key, final int from, final int to) {
        final List<DecisionTree.Factor> factors = new ArrayList<DecisionTree.Factor>();

        for (int i = from; i <= to; i++) {
            final int val = i;
            factors.add(new DecisionTree.Factor() {
                private final String name = key + ">" + val;
                private final int value = val;
                private final String inKey = key;
                @Override
                public String name() {
                    return name;
                }

                @Override
                public boolean is(final DecisionTree.Entity entity) {
                    return (Integer.parseInt(entity.getAttributeValue(inKey)) >= val);
                }
            });
        }
        return factors;
    }


}
