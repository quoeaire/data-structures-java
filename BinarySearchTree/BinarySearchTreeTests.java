package BinarySearchTree;

import org.junit.jupiter.api.*;

class BinarySearchTreeTests {

    /**
     * Binary Node Tester, tests:
     *  1. isRightChild returns correct value on a nonempty tree
     */
    @Test
    protected void testClassBinaryNode() {
        BinaryNode<Integer> nodeOne = new BinaryNode<Integer>(0);
        BinaryNode<Integer> nodeTwo = new BinaryNode<Integer>(-1);
        BinaryNode<Integer> nodeThree = new BinaryNode<Integer>(1);

        // test to see if nodeThree is actually the right child
        nodeOne.setLeftChild(nodeTwo);
        nodeTwo.setParent(nodeOne);
        nodeOne.setRightChild(nodeThree);
        nodeThree.setParent(nodeOne);
        Assertions.assertFalse(nodeTwo.isRightChild(), 
            "[ 1 ]  ##  Node is considered right child.");
        Assertions.assertTrue(nodeThree.isRightChild(), 
            "[ 2 ]  ##  Node is considered right child.");
    }

    /**
     * BST Tester, insert()
     * 1. Tests base cases.
     * 2. Tests insert of 5 nodes to create small tree
     * 3. Tests inserting duplicate node on sub-test 2.
     */
    @Test
    protected void testInsert() {
        BinarySearchTree<Integer> tree = new BinarySearchTree<Integer>();

        // 1. Cant insert null into tree, empty tree returns 0;
        Assertions.assertThrows(NullPointerException.class, () -> {tree.insert(null);});
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
            () -> {Assertions.assertEquals(5, tree.root.getLeftChild().getData());},
            () -> {Assertions.assertEquals(15, tree.root.getRightChild().getData());},
            () -> {Assertions.assertEquals(8, tree.root.getLeftChild().getRightChild().getData());},
            () -> {Assertions.assertEquals(18, tree.root.getRightChild().getRightChild().getData());}
        );

        // 3. Affirm inserting duplicates creates expected tree structure
        tree.insert(5);
        tree.insert(18);
        tree.insert(10);
        Assertions.assertAll(
            "positions",
            () -> {Assertions.assertEquals(10, tree.root.getData());},
            () -> {Assertions.assertEquals(10, tree.root.getLeftChild().getData());},
            () -> {Assertions.assertEquals(15, tree.root.getRightChild().getData());},
            () -> {Assertions.assertEquals(null, tree.root.getLeftChild().getRightChild());},
            () -> {Assertions.assertEquals(5, tree.root.getLeftChild().getLeftChild().getData());},
            () -> {Assertions.assertEquals(8, tree.root.getLeftChild().getLeftChild().getRightChild().getData());},
            () -> {Assertions.assertEquals(18, tree.root.getRightChild().getRightChild().getData());},
            () -> {Assertions.assertEquals(18, tree.root.getRightChild().getRightChild().getLeftChild().getData());}
        );
    }

    /**
     * Rotator, test rotate()
     * 1. Throws relevant exceptions when passed null.
     * 2. Will rotate left AND right on an appropriate 4 node tree.
     */
    @Test
    public void testRotate() {
        BinarySearchTree<Integer> tree = new BinarySearchTree<Integer>();
        
        tree.insert(2);
        tree.insert(1);
        tree.insert(4);
        tree.insert(3);
        tree.rotate(tree.root, tree.root.getRightChild());
        Assertions.assertAll(
            "positions",
            () -> {Assertions.assertEquals(4, tree.root.getData());},
            () -> {Assertions.assertEquals(2, tree.root.getLeftChild().getData());},
            () -> {Assertions.assertEquals(1, tree.root.getLeftChild().getLeftChild().getData());},
            () -> {Assertions.assertEquals(3, tree.root.getLeftChild().getRightChild().getData());}
        );
        
        tree.rotate(tree.root, tree.root.getRightChild());
        Assertions.assertAll(
            "positions",
            () -> {Assertions.assertEquals(4, tree.root.getData());},
            () -> {Assertions.assertEquals(2, tree.root.getLeftChild().getData());},
            () -> {Assertions.assertEquals(1, tree.root.getLeftChild().getLeftChild().getData());},
            () -> {Assertions.assertEquals(3, tree.root.getLeftChild().getRightChild().getData());}
        );
    }
}