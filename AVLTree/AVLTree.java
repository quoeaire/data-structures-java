package AVLTree;

public class AVLTree<T extends Comparable<T>> {
    
    Node<T> root;

    public AVLTree() {
        this.root = null;
    }

    public void insert(T data) throws IllegalArgumentException {
        if (data == null)
            throw new IllegalArgumentException("Can not insert null into a tree.");
        
        if (this.root == null) { // set insert node to root if null
            this.root = new Node<T>(data);
            return;
        }

        Node<T> insertNode = new Node<T>(data);

        // otherwise look for the spot and insert
        insertHelper(insertNode, this.root);
    }

    /**
     * The insert algorithm assumes that this tree is non-balanced. It will find the
     * empty spot where it belongs (if it is not a duplicate) and insert. If it is a
     * duplicate, it will insert just below the node its duplicating
     * 
     * @param insertNode
     * @param search
     */
    private void insertHelper(Node<T> insertNode, Node<T> search) {
        int comparison = insertNode.getData().compareTo(search.getData());

        // case 2: insert nodes data is less than current nodes data
        //         and left child is empty, insert
        if (comparison < 0 
            && search.getLeft() == null) 
        {
            search.setLeft(insertNode);
            insertNode.setParent(search);
            return;
        } 

        // case 2.1: insert nodes data is less than current nodes data
        //           and left child is not empty, search left
        if (comparison < 0
            && search.getLeft() != null) 
        {
            insertHelper(insertNode, search.getLeft());
            return;
        }

        // case 3: insert nodes data is greater than current nodes data
        //         and new spot is empty, insert
        if (comparison > 0
            && search.getRight() == null)
        {
            search.setRight(insertNode);
            insertNode.setParent(search);
            return;
        }

        // case 3.1: insert nodes data is greater than current ndoes data
        //           and right child is not empty, search right
        if (comparison > 0
            && search.getRight() != null)
        {
            insertHelper(insertNode, search.getRight());
            return;
        }

        // case 4: insert nodes data is equal to the current nodes data
        //         determine we can insert in an empty spot or 
        //         if we need to do the surgery
        if (comparison == 0)
        {
            // if left is null, insert
            if (search.getLeft() == null)
            {
                search.setLeft(insertNode);
                insertNode.setParent(search);
                return;
            } 

            // IMPLEMENTATION NOTE: just insert, do not look for recurrences
            // left is not null, do the surgery
            Node<T> liminalNode = search.getLeft();
            search.setLeft(insertNode);
            insertNode.setParent(search);
            insertNode.setLeft(liminalNode);
            liminalNode.setParent(liminalNode);
            return;
        }

        // recursive cases
        if (comparison < 0) {insertHelper(insertNode, search.getLeft());}
        if (comparison > 0) {insertHelper(insertNode, search.getRight());}
    }

    void delete() {

    }

    /**
     * Returns the height of the tree.
     */
    public int height() {
        if (this.root == null)
            return 0;

        return height(this.root);
    }

    /**
     * Internal helper method for self balancing.
     * @param search
     */
    private int height(Node<T> search) {
        if (search == null)
            return 0;

        int leftHeight = 1 + height(search.getLeft());
        int rightHeight = 1 + height(search.getRight());

        return Math.max(leftHeight, rightHeight);
    }

    public int size() {
        if (this.root == null)
            return 0;
            
        return sizeHelper(this.root);
    }

    private int sizeHelper(Node<T> search) {
        if (search == null)
            return 0;

        return 1 + sizeHelper(search.getLeft()) + sizeHelper(search.getRight());
    }

    public boolean isEmpty() {
        return (this.root == null) ? true : false;
    }

    Node<T> getRoot() {
        return this.root;
    }

    /**
     * Rotation utilities
     */
    class Rotator {

    }
}
