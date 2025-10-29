package AVLTree;

import org.junit.jupiter.api.*;

class AVLTreeTests {

    @Test
    protected void testClassNode() {
        Node<Integer> nodeOne = new Node<Integer>(0);
        Node<Integer> nodeTwo = new Node<Integer>(-1);
        Node<Integer> nodeThree = new Node<Integer>(1);

        // test to see if nodeThree is actually the right child
        nodeOne.setLeft(nodeTwo);
        nodeTwo.setParent(nodeOne);
        nodeOne.setRight(nodeThree);
        nodeThree.setParent(nodeOne);
        Assertions.assertFalse(nodeTwo.isRightChild(), 
            "[ 1 ]  ##  Node is considered right child.");
        Assertions.assertTrue(nodeThree.isRightChild(), 
            "[ 2 ]  ##  Node is considered right child.");
    }

    /**
     * Unit Test: insert(), size(), height()
     * 1. Tests base cases.
     * 2. Tests insert of 5 nodes to create small tree
     * 3. Tests inserting duplicate node on sub-test 2.
     * 
     * DISABLED. Tree is self balancing.
     */
    @Test
    @Disabled
    protected void testInsert() {
        AVLTree<Integer> tree = new AVLTree<Integer>();

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
            () -> {Assertions.assertEquals(5, tree.root.getLeft().getData());},
            () -> {Assertions.assertEquals(15, tree.root.getRight().getData());},
            () -> {Assertions.assertEquals(8, tree.root.getLeft().getRight().getData());},
            () -> {Assertions.assertEquals(18, tree.root.getRight().getRight().getData());}
        );

        // 2.1 Test size and height
        Assertions.assertEquals(5, tree.size());
        Assertions.assertEquals(3, tree.height());

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

    /**
     * Clear-box test for rotate() within internal Rotator class.
     * TODO: Ensure rotate() is private once throught with self-balancing implementation.
     * 
     * DISABLED. Tree is self balancing.
     */
    @Test
    @Disabled
    void testRotate() {
        AVLTree<Integer> tree = new AVLTree<Integer>();

        // rotate left on root
        tree.insert(10);
        tree.insert(5);
        tree.insert(15);
        tree.rotate(tree.root.getRight(), tree.root);

        Assertions.assertEquals(15, tree.root.getData());
        Assertions.assertEquals(10, tree.root.getLeft().getData());
        Assertions.assertEquals(5, tree.root.getLeft().getLeft().getData());
        tree.clear();

        // rotate right on root
        tree.insert(10);
        tree.insert(5);
        tree.insert(15);
        tree.rotate(tree.root.getLeft(), tree.root);

        Assertions.assertEquals(5, tree.root.getData());
        Assertions.assertEquals(10, tree.root.getRight().getData());
        Assertions.assertEquals(15, tree.root.getRight().getRight().getData());
        tree.clear();

        // rotate left on inside leaf node of tree height 3
        tree.insert(10);
        tree.insert(5);
        tree.insert(15);
        tree.insert(8);
        tree.insert(18);
        tree.insert(9);
        tree.rotate(tree.root.getLeft().getRight(), tree.root.getLeft());

        Assertions.assertEquals(10, tree.root.getData());
        Assertions.assertEquals(8, tree.root.getLeft().getData());
        Assertions.assertEquals(5, tree.root.getLeft().getLeft().getData());
        Assertions.assertEquals(9, tree.root.getLeft().getRight().getData());
        Assertions.assertEquals(18, tree.root.getRight().getRight().getData());
        tree.clear();
    }
    
    /**
     * Unit-test for ensureBalance.
     * DISABLED. Tree is self balancing.
     */
    @Test
    @Disabled
    void testEnsureBalance() {

        AVLTree<Integer> tree = new AVLTree<Integer>();
        tree.insert(10);
        tree.insert(5);
        tree.insert(15);
        tree.insert(4);
        tree.insert(2);
        tree.ensureBalance(tree.root);

        Assertions.assertEquals(5, tree.root.getData());
        Assertions.assertEquals(4, tree.root.getLeft().getData());
        Assertions.assertEquals(2, tree.root.getLeft().getLeft().getData());
        Assertions.assertEquals(10, tree.root.getRight().getData());
        Assertions.assertEquals(15, tree.root.getRight().getRight().getData());
    }

    /**
     * Tests the self-balancing factor of AVL tree
     */
    @Test
    void testSelfBalancingInsert() {
        AVLTree<Integer> tree = new AVLTree<Integer>();
        tree.insert(10);
        tree.insert(5);
        tree.insert(15);
        tree.insert(8);
        tree.insert(18);
        tree.insert(9);

        Assertions.assertAll(
            "positions",
            () -> {Assertions.assertEquals(10, tree.root.getData());},
            () -> {Assertions.assertEquals(8, tree.root.getLeft().getData());},
            () -> {Assertions.assertEquals(15, tree.root.getRight().getData());},
            () -> {Assertions.assertEquals(5, tree.root.getLeft().getLeft().getData());},
            () -> {Assertions.assertEquals(9, tree.root.getLeft().getRight().getData());},
            () -> {Assertions.assertEquals(18, tree.root.getRight().getRight().getData());}
        );

    }
}