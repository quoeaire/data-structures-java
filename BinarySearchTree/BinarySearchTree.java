package BinarySearchTree;

public class BinarySearchTree<T extends Comparable<T>> {
    // the root node
    protected BinaryNode<T> root;

    public BinarySearchTree() {
        this.root = null;
    }

    public void insert(T data) throws IllegalArgumentException {
        if (data == null)
            throw new IllegalArgumentException("Can not insert null into a tree.");
        
        if (this.root == null) { // set insert node to root if null
            this.root = new BinaryNode<T>(data);
            return;
        }

        BinaryNode<T> insertNode = new BinaryNode<T>(data);

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
    private void insertHelper(BinaryNode<T> insertNode, BinaryNode<T> search) {
        int comparison = insertNode.getData().compareTo(search.getData());

        // case 2: insert nodes data is less than current nodes data
        //         and left child is empty, insert
        if (comparison < 0 
            && search.getLeftChild() == null) 
        {
            search.setLeftChild(insertNode);
            insertNode.setParent(search);
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
                return;
            } 

            // IMPLEMENTATION NOTE: just insert, do not look for recurrences
            // left is not null, do the surgery
            BinaryNode<T> liminalNode = search.getLeftChild();
            search.setLeftChild(insertNode);
            insertNode.setParent(search);
            insertNode.setLeftChild(liminalNode);
            liminalNode.setParent(liminalNode);
            return;
        }

        // recursive cases
        if (comparison < 0) {insertHelper(insertNode, search.getLeftChild());}
        if (comparison > 0) {insertHelper(insertNode, search.getRightChild());}
    }

    /**
     * Deletes node from binary search tree.
     * @param data
     * @return int 0 if tree did not contain the specified value
     *         int 1 if value was successfully deleted
     */
    public int delete(T data) throws IllegalArgumentException, NullPointerException {
        if (data == null)
            throw new IllegalArgumentException("delete() method argument can not be null.");

        if (this.root == null)
            throw new NullPointerException("There is nothing to delete as the tree is empty.");

        return 0 + deleteHelper(data, this.root.getLeftChild()) + deleteHelper(data, this.root.getRightChild());
    }

    /**
     * Internal helper for delete algoritm.
     * @param deleteNode
     * @param search
     * @return int 0 if tree did not contain the specified value
     *         int 1 if value was successfully deleted
     */
    private int deleteHelper(T data, BinaryNode<T> search) {
        if (search == null)
            return 0;

        if (data.compareTo(search.getData()) == 0)
        {
            // create some placeholders for easier readability
            BinaryNode parent, left, right, leftGrandchild = null, rightGrandchild = null;
            parent = search.getParent();
            left = search.getLeftChild();
            right = search.getRightChild();

            // get grandchildren, if there are any
            if (left != null)
                leftGrandchild = left.getRightChild();

            if (right != null)
                rightGrandchild = right.getLeftChild();

            // default to using leftGrandchild are replacement
            if (leftGrandchild != null)
            {
                
            }
            
            else if (rightGrandchild != null)
            {
                
            }

            return 1;
        }

        if (data.compareTo(search.getData()) < 0)
        {    
            deleteHelper(data, search.getLeftChild());
            return 0;
        }

        if (data.compareTo(search.getData()) > 0)
        {
            deleteHelper(data, search.getRightChild());
            return 0;
        }

        // to clear compile error
        return -1;
    }

    /**
     * Clears this tree of all nodes
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
    private boolean containsHelper(T data, BinaryNode<T> search) {
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
    private int sizeHelper(BinaryNode<T> search) {
        if (search == null)
            return 0;
        
        return 1 + sizeHelper(search.getLeftChild()) + sizeHelper(search.getRightChild());
    }

    public void rotate(BinaryNode<T> parent, BinaryNode<T> child) throws IllegalArgumentException{
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
        BinaryNode<T> liminialNode, grandparentNode;

        // since child is right child we intend to rotate left
        if (child.isRightChild())
        {
            liminialNode = child.getLeftChild();
            grandparentNode = parent.getParent();

            // determine if the parent node is a right child or not
            // and put childs node in its place
            child.setParent(grandparentNode);
            if (grandparentNode != null
                && parent.isRightChild())
            {
                grandparentNode.setRightChild(child);
            } 
            else if (grandparentNode != null
                     && !parent.isRightChild())
            {
                grandparentNode.setLeftChild(child);
            }
            
            // if grandparent node is null, then we must be at root
            if (grandparentNode == null)
                this.root = child;
                
            // proceed to set parent as childs left child
            child.setLeftChild(parent);
            parent.setParent(child);

            // move liminal node to where child was on parent
            parent.setRightChild(liminialNode);
            liminialNode.setParent(parent);
        } 
        
        // since child is left child we intend to rotate left
        else 
        {
            liminialNode = child.getRightChild();
            grandparentNode = parent.getParent();

            // determine if the parent node is a right child or not
            // and put childs node in its place
            child.setParent(grandparentNode);
            if (grandparentNode != null 
                && parent.isRightChild())
            {
                grandparentNode.setRightChild(child);
            } 
            else if (grandparentNode != null
                     && !parent.isRightChild())
            {
                grandparentNode.setLeftChild(child);
            }

            // if grandparent node is null, then we must be at root
            if (grandparentNode == null)
                this.root = child;

            // proceed to set parent and childs right child
            child.setLeftChild(parent);
            parent.setParent(child);

            // move liminal node to where child was on the parent
            parent.setLeftChild(liminialNode);
            liminialNode.setParent(parent);
        }
    }
}