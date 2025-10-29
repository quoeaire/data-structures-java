package RedBlackTree;

import org.junit.jupiter.api.*;

/**
 * Class for testing RedBlackTree package. Utilizes JUnit Jupiter otherwise known as
 * JUnit 5.
 * @author Matthew Smith
 */
class RedBlackTreeTests {

    /**
     * RedBlackNode Tester
     */
    @Test
    protected void testClassRedBlackNode() {
        // 1. Test that relations work properly
        RedBlackNode<Integer> nodeOne = new RedBlackNode<Integer>(0);
        RedBlackNode<Integer> nodeTwo = new RedBlackNode<Integer>(-1);
        RedBlackNode<Integer> nodeThree = new RedBlackNode<Integer>(1);

        nodeOne.setLeft(nodeTwo);
        nodeTwo.setParent(nodeOne);
        nodeOne.setRight(nodeThree);
        nodeThree.setParent(nodeOne);
        Assertions.assertFalse(nodeTwo.isRight(), 
            "[ 1 ]  ##  Node is considered right child.");
        Assertions.assertTrue(nodeThree.isRight(), 
            "[ 2 ]  ##  Node is considered right child.");

        // 2. Test that node color can be flipped
        nodeOne.flipColor();
        Assertions.assertTrue(nodeOne.isBlack());
    }

    /**
     * RedBlackTree Testers
     */
    @Test
    protected void testClassRedBlackTree() {

        { // Tests method insert()
        RedBlackTree<Integer> tree = new RedBlackTree<Integer>();

        // 1. Cant insert null into tree, empty tree returns 0;
        Assertions.assertThrows(IllegalArgumentException.class, () -> {tree.insert(null);});
        Assertions.assertEquals(0, tree.size());

        // 2. Affirm insertion of 5 nodes form expected structure of tree
        tree.insert(10);
        tree.insert(5);
        tree.insert(15);
        tree.insert(8);
        tree.insert(18);
        Assertions.assertAll(
            "positions",
            () -> {Assertions.assertEquals(10, tree.root.getData());},
            () -> {Assertions.assertTrue(tree.root.isBlack());},
            () -> {Assertions.assertEquals(5, tree.root.getLeft().getData());},
            () -> {Assertions.assertTrue(tree.root.getLeft().isBlack());},
            () -> {Assertions.assertEquals(15, tree.root.getRight().getData());},
            () -> {Assertions.assertTrue(tree.root.getRight().isBlack());},
            () -> {Assertions.assertEquals(8, tree.root.getLeft().getRight().getData());},
            () -> {Assertions.assertTrue(!tree.root.getLeft().getRight().isBlack());},
            () -> {Assertions.assertEquals(18, tree.root.getRight().getRight().getData());},
            () -> {Assertions.assertTrue(!tree.root.getRight().getRight().isBlack());}
        );

        // 3. Affirm inserting duplicates creates expected tree structure
        tree.insert(5);
        tree.insert(18);
        tree.insert(10);
        Assertions.assertAll(
            "positions",
            () -> {Assertions.assertEquals(10, tree.root.getData());},
            () -> {Assertions.assertEquals(10, tree.root.getLeft().getData());},
            () -> {Assertions.assertEquals(15, tree.root.getRight().getData());},
            () -> {Assertions.assertEquals(null, tree.root.getLeft().getRight());},
            () -> {Assertions.assertEquals(5, tree.root.getLeft().getLeft().getData());},
            () -> {Assertions.assertEquals(8, tree.root.getLeft().getLeft().getRight().getData());},
            () -> {Assertions.assertEquals(18, tree.root.getRight().getRight().getData());},
            () -> {Assertions.assertEquals(18, tree.root.getRight().getRight().getLeft().getData());}
        );
        }

    }
}