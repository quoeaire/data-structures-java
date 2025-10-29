package RedBlackTree;

public class RedBlackTree<T extends Comparable<T>> {
    // the root node
    protected RedBlackNode<T> root;
    private Rotator<T> rotator;

    public RedBlackTree() {
        this.root = null;
        this.rotator = new Rotator<T>(this.root);
    }

    /**
     * Method to insert new data into the tree. New nodes are
     * always red.
     * @param data the data that is to be inserted into this tree
     * @throws IllegalArgumentException when data is null
     */
    public void insert(T data) throws IllegalArgumentException {
        if (data == null)
            throw new IllegalArgumentException("Can not insert null into a tree.");
        
        if (this.root == null) { // set insert node to root if null and flip color
            this.root = new RedBlackNode<T>(data);
            ensureColorProperties(this.root);
            return;
        }

        RedBlackNode<T> insertNode = new RedBlackNode<T>(data);

        // otherwise look for the spot and insert
        insertHelper(insertNode, this.root);
    }

    /**
     * Helper method that ensures the RBTree does not violate color properties. 
     * Only called during insertion of a new node.
     * @param insertNode
     */
    private void ensureColorProperties(RedBlackNode<T> insertNode) {
        
        // case 1: insert node is the root node, ensure root is black
        if (this.root == insertNode
            && insertNode.getParent() == null) 
        {
            if (!this.root.isBlack()) this.root.flipColor();
            return;
        }

        // case 2: insert node has a parent, recursively check upwards
        //         only if parent is black (we have nothing to do here)
        RedBlackNode<T> parent = insertNode.getParent();
        if (parent.isBlack()) {
            ensureColorProperties(parent);
            return;
        }

        // case 3: insert node has a parent, but the parent is red...
        //         we need to make some adjustments
        //         so we set relations where we can
        RedBlackNode<T> grandParent = insertNode.getParent().getParent();
        RedBlackNode<T> auntNode, liminalNode;

        // determine which way we will need to rotate
        if (grandParent != null) 
        {
            // but is there a inside rotate thats needed?
            // parent is less than insert, gp is more than insert
            if (insertNode.getData().compareTo(parent.getData()) >= 0
                && insertNode.getData().compareTo(grandParent.getData()) <= 0)
            {
                this.rotator.rotate(parent, insertNode);
                liminalNode = parent;       // we are shifting scope here
                parent = insertNode;        // "parent" is now the insert node
                insertNode = liminalNode;   // which we rotated up
            }

            // parent is more than insert, gp is less than insert
            else if (insertNode.getData().compareTo(parent.getData()) <= 0
                && insertNode.getData().compareTo(grandParent.getData()) >= 0)
            {
                this.rotator.rotate(parent, insertNode);
                liminalNode = parent;       // we are shifting scope here
                parent = insertNode;        // "parent" is now the insert node
                insertNode = liminalNode;   // which we rotated up
            }


            // if the parent of insert is the right child
            // case 3.1: aunt is on the left    <---
            if (parent.isRightChild()) 
            {
                auntNode = grandParent.getLeftChild();

                // and determine the aunts color
                // case 3.1.1: aunt is red, we can just recolor
                if (!auntNode.isBlack()) {
                    parent.flipColor();         // black
                    auntNode.flipColor();       // black
                    grandParent.flipColor();    // red

                    // if we created another violation, we need to fix it
                    ensureColorProperties(grandParent);
                }

                // case 3.1.2: aunt is black, we need to rotate and recolor
                else {
                    grandParent.flipColor();
                    parent.flipColor();
                    this.rotator.rotate(grandParent, parent);
                    return;
                }
            }

            // if the parent of insert is the left child
            // case 3.2: aunt is on the right    --->
            else
            {
                auntNode = grandParent.getRightChild();

                // and determine the aunts color
                // case 3.2.1: aunt is red, so we can just recolor
                if (!auntNode.isBlack()) {
                    parent.flipColor();         // black
                    auntNode.flipColor();       // black
                    grandParent.flipColor();    // red

                    // if we created another violation, we need to fix it
                    ensureColorProperties(grandParent);
                }

                // case 3.2.2: aunt is black, we need to rotate and recolor
                else {
                    grandParent.flipColor();
                    parent.flipColor();
                    this.rotator.rotate(grandParent, parent);
                    return;
                }
            }
        }

        // case 2: parent node is red and insertNode is an "inside" child

        // case 3: we are looking at any other child-parent relationship
    }

    /**
     * The insert algorithm assumes that this tree is non-balanced. It will find the
     * empty spot where it belongs (if it is not a duplicate) and insert. If it is a
     * duplicate, it will insert just below the node its duplicating
     * 
     * @param insertNode
     * @param search
     */
    private void insertHelper(RedBlackNode<T> insertNode, RedBlackNode<T> search) {
        int comparison = insertNode.getData().compareTo(search.getData());

        // case 2: insert nodes data is less than current nodes data
        //         and left child is empty, insert
        if (comparison < 0 
            && search.getLeftChild() == null) 
        {
            search.setLeftChild(insertNode);
            insertNode.setParent(search);
            ensureColorProperties(insertNode);
            return;
        } 

        // case 2.1: insert nodes data is less than current nodes data
        //           and left child is not empty, search left
        if (comparison < 0
            && search.getLeftChild() != null) 
        {
            insertHelper(insertNode, search.getLeftChild());
        }

        // case 3: insert nodes data is greater than current nodes data
        //         and new spot is empty, insert
        if (comparison > 0
            && search.getRightChild() == null)
        {
            search.setRightChild(insertNode);
            insertNode.setParent(search);
            ensureColorProperties(insertNode);
            return;
        }

        // case 3.1: insert nodes data is less than current ndoes data
        //           and right child is not empty, search right
        if (comparison > 0
            && search.getRightChild() != null)
        {
            insertHelper(insertNode, search.getRightChild());   
        }

        // case 4: insert nodes data is equal to the current nodes data
        //         determine we can insert in an empty spot or 
        //         if we need to do the surgery
        if (comparison == 0)
        {
            // if left is null, insert
            if (search.getLeftChild() == null)
            {
                search.setLeftChild(insertNode);
                insertNode.setParent(search);
                ensureColorProperties(insertNode);
                return;
            } 

            // IMPLEMENTATION NOTE: just insert, do not look for recurrences
            // left is not null, do the surgery
            RedBlackNode<T> liminalNode = search.getLeftChild();
            search.setLeftChild(insertNode);
            insertNode.setParent(search);
            insertNode.setLeftChild(liminalNode);
            liminalNode.setParent(liminalNode);
            ensureColorProperties(insertNode);
            return;
        }

        // recursive cases
        if (comparison < 0) {insertHelper(insertNode, search.getLeftChild());}
        if (comparison > 0) {insertHelper(insertNode, search.getRightChild());}
    }

    public void delete(T data) {
        // TODO: Implement
    }

    /**
     * Clears this tree of all nodes
     */
    public void clear() {
        this.root = null;
    }

    /**
     * Searches the tree for the specified data and returns true if it is found.
     * 
     * @param data
     * @return true if tree contains the data
     *         false if the tree does not contain the data
     */
    public boolean contains(T data) {
        if (this.root == null)
            return false;

        // ORing false with the result of a recursive call to return result
        return false || containsHelper(data, this.root);
    }

    /**
     * Recursive helper method for contains()
     * @param data
     * @param search
     * @return
     */
    private boolean containsHelper(T data, RedBlackNode<T> search) {
        if (data.compareTo(search.getData()) == 0)
            return true;

        return containsHelper(data, search.getLeftChild()) 
            || containsHelper(data, search.getRightChild());
    }

    /**
     * Iterates over the tree and returns how many nodes it contains.
     * 
     * @return the size of the tree
     */
    public int size() {
        if (this.root == null)
            return 0;
        
        return sizeHelper(this.root);
    }

    /**
     * Recursive helper method for size.
     * @param search
     * @return
     */
    private int sizeHelper(RedBlackNode<T> search) {
        if (search == null)
            return 0;
        
        return 1 + sizeHelper(search.getLeftChild()) 
            + sizeHelper(search.getRightChild());
    }

    public RedBlackNode<T> copyOfNode(T data) 
    {return new RedBlackNode<T>(data);}
}