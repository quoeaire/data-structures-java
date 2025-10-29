package RedBlackTree;

class Rotator<T extends Comparable<T>> {

    private RedBlackNode<T> tr = null;

    Rotator(RedBlackNode<T> treesRoot) {
        this.tr = treesRoot;
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
                this.tr = child;
                
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
                this.tr = child;

            // proceed to set parent and childs right child
            child.setLeftChild(parent);
            parent.setParent(child);

            // move liminal node to where child was on the parent
            parent.setLeftChild(liminialNode);
            liminialNode.setParent(parent);
        }
    }
}