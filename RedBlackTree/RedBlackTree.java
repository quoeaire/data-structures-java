package RedBlackTree;

public class RedBlackTree<T extends Comparable<T>> {
    // the root node
    protected RedBlackNode<T> root;

    public RedBlackTree() {
        this.root = null;
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
            && search.getLeft() == null) 
        {
            search.setLeft(insertNode);
            insertNode.setParent(search);
            ensureColorProperties(insertNode);
            return;
        } 

        // case 2.1: insert nodes data is less than current nodes data
        //           and left child is not empty, search left
        if (comparison < 0
            && search.getLeft() != null) 
        {
            insertHelper(insertNode, search.getLeft());
        }

        // case 3: insert nodes data is greater than current nodes data
        //         and new spot is empty, insert
        if (comparison > 0
            && search.getRight() == null)
        {
            search.setRight(insertNode);
            insertNode.setParent(search);
            ensureColorProperties(insertNode);
            return;
        }

        // case 3.1: insert nodes data is less than current ndoes data
        //           and right child is not empty, search right
        if (comparison > 0
            && search.getRight() != null)
        {
            insertHelper(insertNode, search.getRight());   
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
                ensureColorProperties(insertNode);
                return;
            } 

            // IMPLEMENTATION NOTE: just insert, do not look for recurrences
            // left is not null, do the surgery
            RedBlackNode<T> liminalNode = search.getLeft();
            search.setLeft(insertNode);
            insertNode.setParent(search);
            insertNode.setLeft(liminalNode);
            liminalNode.setParent(liminalNode);
            ensureColorProperties(insertNode);
            return;
        }

        // recursive cases
        if (comparison < 0) {insertHelper(insertNode, search.getLeft());}
        if (comparison > 0) {insertHelper(insertNode, search.getRight());}
    }

    public void delete(T data) {
        // TODO: Implement
    }

    public void deleteHelper(RedBlackNode<T> deletionNode) {
        
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
                rotate(parent, insertNode);
                liminalNode = parent;       // we are shifting scope here
                parent = insertNode;        // "parent" is now the insert node
                insertNode = liminalNode;   // which we rotated up
            }

            // parent is more than insert, gp is less than insert
            else if (insertNode.getData().compareTo(parent.getData()) <= 0
                && insertNode.getData().compareTo(grandParent.getData()) >= 0)
            {
                rotate(parent, insertNode);
                liminalNode = parent;       // we are shifting scope here
                parent = insertNode;        // "parent" is now the insert node
                insertNode = liminalNode;   // which we rotated up
            }


            // if the parent of insert is the right child
            // case 3.1: aunt is on the left    <---
            if (parent.isRight()) 
            {
                auntNode = grandParent.getLeft();

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
                    rotate(grandParent, parent);
                    return;
                }
            }

            // if the parent of insert is the left child
            // case 3.2: aunt is on the right    --->
            else
            {
                auntNode = grandParent.getRight();

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
                    rotate(grandParent, parent);
                    return;
                }
            }
        }

        // case 2: parent node is red and insertNode is an "inside" child

        // case 3: we are looking at any other child-parent relationship
    }


    /**
     * Clears this tree of all nodes
     */
    public void clear() {
        this.root = null;
    }

    public boolean isEmpty() {
        if (this.root == null)
            return true;

        return false;
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

        return containsHelper(data, search.getLeft()) 
            || containsHelper(data, search.getRight());
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
        
        return 1 + sizeHelper(search.getLeft()) 
            + sizeHelper(search.getRight());
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
    public int height(RedBlackNode<T> search) {
        if (search == null)
            return 0;

        int leftHeight = 1 + height(search.getLeft());
        int rightHeight = 1 + height(search.getRight());

        return Math.max(leftHeight, rightHeight);
    }

/**
     * Simple rotator. Will rotate child in parents direction.
     * @param parent parent node
     * @param child child node
     * @throws IllegalArgumentException
     */
    void rotate(RedBlackNode<T> parent, RedBlackNode<T> child) 
        throws IllegalArgumentException
    {
        // base case 1: can not rotate on null
        if (child == null && parent == null)
            throw new IllegalArgumentException("Both parent and child can not be null");

        if (child == null)
            throw new IllegalArgumentException("Child can not be null");

        if (parent == null)
            throw new IllegalArgumentException("Parent can not be null");

        // base case 2: can not rotate nodes that are not related
        if (child.getParent() != parent) 
        {
            throw new IllegalArgumentException("These nodes are not related");
        }

        // determine which way we intend to rotate
        // and create some placeholders on the way
        RedBlackNode<T> liminialNode, grandparentNode;

        // since child is right child we intend to rotate left
        if (child.isRight())
        {
            liminialNode = child.getLeft();
            grandparentNode = parent.getParent();

            // determine if the parent node is a right child or not
            // and put childs node in its place
            child.setParent(grandparentNode);
            if (grandparentNode != null
                && parent.isRight())
            {
                grandparentNode.setRight(child);
            } 
            else if (grandparentNode != null
                     && !parent.isRight())
            {
                grandparentNode.setLeft(child);
            }
            
            // if grandparent node is null, then we must be at root
            if (grandparentNode == null)
                this.root = child;
                
            // proceed to set parent as childs left child
            child.setLeft(parent);
            parent.setParent(child);

            // move liminal node to where child was on parent
            parent.setRight(liminialNode);
            liminialNode.setParent(parent);
        } 
        
        // since child is left child we intend to rotate left
        else 
        {
            liminialNode = child.getRight();
            grandparentNode = parent.getParent();

            // determine if the parent node is a right child or not
            // and put childs node in its place
            child.setParent(grandparentNode);
            if (grandparentNode != null 
                && parent.isRight())
            {
                grandparentNode.setRight(child);
            } 
            else if (grandparentNode != null
                     && !parent.isRight())
            {
                grandparentNode.setLeft(child);
            }

            // if grandparent node is null, then we must be at root
            if (grandparentNode == null)
                this.root = child;

            // proceed to set parent and childs right child
            child.setLeft(parent);
            parent.setParent(child);

            // move liminal node to where child was on the parent
            parent.setLeft(liminialNode);
            liminialNode.setParent(parent);
        }
    }
}