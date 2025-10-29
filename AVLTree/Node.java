package AVLTree;

/**
 * Simple node.
 * @author Matthew Smith
 */
public class Node<T extends Comparable<T>> {

    public T data;
    public Node<T> parent;
    public Node<T> left;
    public Node<T> right;
    
    /**
     * Constructor. Only way to set data.
     * @param data
     */
    public Node(T data) {
        this.data = data;
        this.parent = null;
        this.left = null;
        this.right = null;
    }

    public boolean isRightChild() {
        if (this.getParent() == null)
            return false;

        return (this.getData().compareTo(this.getParent().getData()) > 0) ? true : false;
    }

    // getters and setters
    public T getData() {
        return this.data;
    }

    public Node<T> getParent() {
        return this.parent;
    }

    public Node<T> getLeft() {
        return this.left;
    }

    public Node<T> getRight() {
        return this.right;
    }

    public void setParent(Node<T> parentNode) {
        this.parent = parentNode;
    }
    
    public void setLeft(Node<T> leftNode) {
        this.left = leftNode;
    }

    public void setRight(Node<T> rightNode) {
        this.right = rightNode;
    }
}