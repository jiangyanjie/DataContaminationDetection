package ru.ifmo.enf.kogan.t05;

import junit.framework.Assert;
import org.junit.Test;

import java.util.HashMap;

/**
 * Created by arsenykogan on 01/03/14.
 */
public class DecisionTreeTest extends Assert {

    public enum Fruits {
        APPLE, GRAPES, BANANA, LEMON, GRAPEFRUIT, CHERRY, WATERMELON;
    }

    @Test
    public void dummyTree() {

        // Defining tree with some nodes
        // 1. Creating leaf-nodes
        final TreeNode<Fruits> bananaNode = new TreeNode<Fruits>(Fruits.BANANA);
        final TreeNode<Fruits> grapesNode = new TreeNode<Fruits>(Fruits.GRAPES);
        final TreeNode<Fruits> appleNode = new TreeNode<Fruits>(Fruits.APPLE);

        // 2. Creating some factor-nodes
        final TreeNode<Fruits> sizeIsBIG =
                new TreeNode<Fruits>(new NodeFactor("Size is BIG", "size", "big"), grapesNode, appleNode);

        // 3. Creating root node
        final TreeNode<Fruits> colorIsRED =
                new TreeNode<Fruits>(new NodeFactor("Color is RED", "color", "red"), bananaNode, sizeIsBIG);

        final DecisionTree<Fruits> tree = new DecisionTreeImpl<Fruits>(colorIsRED);


        // Creating entity with defined attributes
        final DecisionTree.Entity banana = getEntityByAttributes("color", "yellow", "size", "big");
        final DecisionTree.Entity grapes = getEntityByAttributes("color", "red", "size", "small");
        final DecisionTree.Entity apple = getEntityByAttributes("color", "red", "size", "big");

        assertEquals(Fruits.BANANA, tree.getCategory(banana));
        assertEquals(Fruits.APPLE, tree.getCategory(apple));
        assertEquals(Fruits.GRAPES, tree.getCategory(grapes));

        System.out.println(tree);
    }

    @Test
    public void niceFruitTree() {
        // Creating tree from leafs to root
        final TreeNode<Fruits> diam4 =
                new TreeNode<Fruits>(new NodeFactor("Diam > 4", "diam", ">4"),
                        new TreeNode(Fruits.LEMON), new TreeNode(Fruits.GRAPEFRUIT));
        final TreeNode<Fruits> shapeIsSphere =
                new TreeNode<Fruits>(new NodeFactor("Shape is sphere", "shape", "sphere"),
                        new TreeNode(Fruits.BANANA), diam4);
        final TreeNode<Fruits> hasSeeds =
                new TreeNode<Fruits>(new NodeFactor("Has seeds", "seeds", "yes"),
                        new TreeNode(Fruits.GRAPES), new TreeNode(Fruits.CHERRY));
        final TreeNode<Fruits> diam2 =
                new TreeNode<Fruits>(new NodeFactor("Diam > 2", "diam", ">2"),
                        hasSeeds, new TreeNode(Fruits.APPLE));
        final TreeNode<Fruits> isRed =
                new TreeNode<Fruits>(new NodeFactor("Is red", "color", "red"),
                        shapeIsSphere, diam2);
        final TreeNode<Fruits> diam2Green =
                new TreeNode<Fruits>(new NodeFactor("Diam > 2", "diam", ">2"),
                        new TreeNode(Fruits.GRAPES), new TreeNode(Fruits.APPLE));
        final TreeNode<Fruits> diam6Green =
                new TreeNode<Fruits>(new NodeFactor("Diam > 6", "diam", ">6"),
                        diam2Green, new TreeNode(Fruits.WATERMELON));
        final TreeNode<Fruits> isGreen =
                new TreeNode<Fruits>(new NodeFactor("Is green", "color", "green"),
                        isRed, diam6Green);

        // Tree initialization
        final DecisionTree<Fruits> tree = new DecisionTreeImpl<Fruits>(isGreen);

        assertEquals(Fruits.GRAPEFRUIT, tree.getCategory(getEntityByAttributes("diam", ">4", "shape", "sphere", "color", "orange", "seeds", "no")));
        assertEquals(Fruits.APPLE, tree.getCategory(getEntityByAttributes("diam", ">2", "shape", "sphere", "color", "green", "seeds", "yes")));
        assertEquals(Fruits.CHERRY, tree.getCategory(getEntityByAttributes("diam", "<2", "shape", "sphere", "color", "red", "seeds", "yes")));

        System.out.println(tree);

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
        final DecisionTree.Entity entity = new Entity<Fruits>(attributes);
        return entity;
    }
}
