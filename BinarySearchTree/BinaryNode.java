package BinarySearchTree;

public class BinaryNode<T extends Comparable<T>> {

    protected T data;
    private BinaryNode<T> parent;
    private BinaryNode<T> leftChild;
    private BinaryNode<T> rightChild;

    /**
     * Creates a new node with supplied data and without references to other nodes.
     * @param data
     */
    public BinaryNode(T data) {
        this.data = data;
        this.parent = null;
        this.leftChild = null;
        this.rightChild = null;
    }

    /**
     * Lets caller know if this current node is the right child of its parent.
     * @return true if current node is right child
     * @return false if current node is not right child or does not have a parent.
     */
    public boolean isRightChild() {
        if (this.getParent() != null && this.getParent().getRightChild() == this)
            return true;

        return false;
    }

    // getters and setters
    public T getData() {
        return this.data;
    }

    public BinaryNode<T> getParent() {
        return this.parent;
    }

    public BinaryNode<T> getLeftChild() {
        return this.leftChild;
    }

    public BinaryNode<T> getRightChild() {
        return this.rightChild;
    }

    public void setData(T data) {
        this.data = data;
    }

    public void setParent(BinaryNode<T> parent) {
        this.parent = parent;
    }

    public void setLeftChild(BinaryNode<T> leftChild) {
        this.leftChild = leftChild;
    }

    public void setRightChild(BinaryNode<T> rightChild) {
        this.rightChild = rightChild;
    }

}