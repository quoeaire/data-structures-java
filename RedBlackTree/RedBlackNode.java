package RedBlackTree;

public class RedBlackNode<T extends Comparable<T>>{

    protected T data;
    protected boolean isBlack;
    private RedBlackNode<T> parent;
    private RedBlackNode<T> leftChild;
    private RedBlackNode<T> rightChild;

    /**
     * Creates a new node with supplied data and without references to other nodes.
     * @param data
     */
    public RedBlackNode(T data) {
        this.data = data;
        this.isBlack = false;
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

    public boolean isBlack() {
        return this.isBlack;
    }

    public RedBlackNode<T> getParent() {
        return this.parent;
    }

    public RedBlackNode<T> getLeftChild() {
        return this.leftChild;
    }

    public RedBlackNode<T> getRightChild() {
        return this.rightChild;
    }

    public void setData(T data) {
        this.data = data;
    }

    // this is a setter that will set the color to black/red
    // depending on its current state
    public void flipColor() {
        this.isBlack = !this.isBlack;
    }

    public void setParent(RedBlackNode<T> parent) {
        this.parent = parent;
    }

    public void setLeftChild(RedBlackNode<T> leftChild) {
        this.leftChild = leftChild;
    }

    public void setRightChild(RedBlackNode<T> rightChild) {
        this.rightChild = rightChild;
    }

}