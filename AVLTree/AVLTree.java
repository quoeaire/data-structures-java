package AVLTree;

public class AVLTree<T extends Comparable<T>> {
    
    // root node
    Node<T> root;

    /**
     * Simple constructor.
     */
    public AVLTree() {
        this.root = null;
    }

    /**
     * Allows for the insertion of data into the tree.
     * @param data
     * @throws IllegalArgumentException
     */
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
        ensureBalance(this.root);
    }

    /**
     * The insert algorithm assumes that this tree is non-balanced. It will find the
     * empty spot where it belongs (if it is not a duplicate) and insert. If it is a
     * duplicate, it will insert just below the node its duplicating
     * 
     * @param insertNode
     * @param search
     */
    public void insertHelper(Node<T> insertNode, Node<T> search) {
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
            ensureBalance(search.getLeft());
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
            ensureBalance(search.getRight());
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
    }

    public void delete() {

    }

    public void deleteHelper(Node<T> deletionNode) {

    }

    /**
     * Ensures the tree is balanced. Called as recusion is undone in insertHelper() and insert().
     * @param node
     */
    public void ensureBalance(Node<T> node) {
        int leftHeight = height(node.getLeft());
        int rightHeight = height(node.getRight());
        int balanceFactor = leftHeight - rightHeight;

        // if balance factor is greater than 2, subtree is weighted left
        // rotate right
        if (balanceFactor >= 2)
        {
            rotate(node.getLeft(), node);
        }

        // if balance factor is less than -2, subtree is weighted right
        // rotate left
        else if (balanceFactor <= -2)
        {
            rotate(node.getRight(), node);
        }

        return;
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
    public int height(Node<T> search) {
        if (search == null)
            return 0;

        int leftHeight = 1 + height(search.getLeft());
        int rightHeight = 1 + height(search.getRight());

        return Math.max(leftHeight, rightHeight);
    }

    /**
     * Returns how many nodes are in the tree.
     * @return
     */
    public int size() {
        if (this.root == null)
            return 0;
            
        return sizeHelper(this.root);
    }

    /**
     * Internal helper method for size().
     * @param search
     * @return
     */
    public int sizeHelper(Node<T> search) {
        if (search == null)
            return 0;

        return 1 + sizeHelper(search.getLeft()) + sizeHelper(search.getRight());
    }

    /**
     * Clears the tree.
     */
    public void clear() {
        this.root = null;
    }

    /**
     * Checks to see if the tree is empty.
     * @return
     */
    public boolean isEmpty() {
        return (this.root == null) ? true : false;
    }

    /**
     * Method used to rotate a child in the parents direction.
     * @param child
     * @param parent
     * @throws IllegalArgumentException
     */
    public void rotate(Node<T> child, Node<T> parent) throws IllegalArgumentException 
    {
        if (child.getParent() != parent)
            throw new IllegalArgumentException("These two nodes are not related.");

        if (child == null || parent == null)
            throw new IllegalArgumentException("All arguments must be non-null.");

        // some instantiables for readability
        Node<T> liminalNode = null, grandparentNode = null;

        // if grandparent not null, grab it
        if (parent.getParent() != null) {
            grandparentNode = parent.getParent();
        }

        // left rotate
        if (child.isRightChild()) {

            // if theres a liminal node, grab it
            if (child.getLeft() != null)
                liminalNode = child.getLeft(); 

            // child becomes new parent
            child.setParent(grandparentNode);
            if (grandparentNode != null && parent.isRightChild())
                grandparentNode.setRight(child);
            
            if (grandparentNode != null && !parent.isRightChild())
                grandparentNode.setLeft(child);

            child.setLeft(parent);

            // parent becomes new child, leaving left subtree untouched
            parent.setParent(child);

            // liminal node gets moved over if it exists
            parent.setRight(liminalNode);
            if (liminalNode != null)
                liminalNode.setParent(parent);

            // if grandparent node is null, we must be at root
            // so new parent is now root
            if (grandparentNode == null)
                this.root = child;

        } else if (!child.isRightChild()) {

            // if theres a liminal node, grab it
            if (child.getRight() != null)
                liminalNode = child.getRight(); 

            // child becomes new parent
            child.setParent(grandparentNode);
            if (grandparentNode != null && parent.isRightChild())
                grandparentNode.setRight(child);
            
            if (grandparentNode != null && !parent.isRightChild())
                grandparentNode.setLeft(child);

            child.setRight(parent);

            // parent becomes new child, leaving left subtree untouched
            parent.setParent(child);

            // liminal node gets moved over if it exists
            parent.setLeft(liminalNode);
            if (liminalNode != null)
                liminalNode.setParent(parent);

            // if grandparent node is null, we must be at root
            // so new parent is now root
            if (grandparentNode == null)
                this.root = child;
        }
    }
    
    // testing methods
    // TODO: remove after tests
    public Node<T> getRoot() {
        return this.root;
    }

    public void setRoot(Node<T> newRoot) {
        this.root = newRoot;
    }
}
