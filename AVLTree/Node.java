package AVLTree;

/**
 * Simple node.
 * @author Matthew Smith
 */
class Node<T extends Comparable<T>> {

    T data;
    Node<T> parent;
    Node<T> left;
    Node<T> right;
    
    /**
     * Constructor. Only way to set data.
     * @param data
     */
    Node(T data) {
        this.data = data;
        this.parent = null;
        this.left = null;
        this.right = null;
    }

    // getters and setters
    T getData() {
        return this.data;
    }

    Node<T> getParent() {
        return this.parent;
    }

    Node<T> getLeft() {
        return this.left;
    }

    Node<T> getRight() {
        return this.right;
    }

    void setParent(Node<T> parentNode) {
        this.parent = parentNode;
    }
    
    void setLeft(Node<T> leftNode) {
        this.left = leftNode;
    }

    void setRight(Node<T> rightNode) {
        this.right = rightNode;
    }
}