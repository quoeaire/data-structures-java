package RedBlackTree;

public class RedBlackNode<T extends Comparable<T>>{

    protected T data;
    protected boolean isBlack;
    private RedBlackNode<T> parent;
    private RedBlackNode<T> left;
    private RedBlackNode<T> right;

    /**
     * Creates a new node with supplied data and without references to other nodes.
     * @param data
     */
    public RedBlackNode(T data) {
        this.data = data;
        this.isBlack = false;
        this.parent = null;
        this.left = null;
        this.right = null;
    }

    /**
     * Lets caller know if this current node is the right child of its parent.
     * @return true if current node is right child
     * @return false if current node is not right child or does not have a parent.
     */
    public boolean isRight() {
        if (this.getParent() != null && this.getParent().getRight() == this)
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

    public RedBlackNode<T> getLeft() {
        return this.left;
    }

    public RedBlackNode<T> getRight() {
        return this.right;
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

    public void setLeft(RedBlackNode<T> left) {
        this.left = left;
    }

    public void setRight(RedBlackNode<T> right) {
        this.right = right;
    }

}