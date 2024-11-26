public class AVLtree {


    private Node root;

    // inserting a node to the AVL tree.
    public void insert(int capacity, ParkingLot parkingLot) {
        root = insert(root, capacity, parkingLot, null);
    }

    //returns the height value of the node, if there is not any, returns 0.
    public int getHeight(Node node){
        if (node == null) {
            return 0;}
        else{
            return node.height;
        }
    }

    //i will look the balance factor to see if the node is in balance. this method just returns the size of the factor.
    public int getBalance(Node node) {
        if (node == null) {
            return 0;
        } else {
            return getHeight(node.left) - getHeight(node.right);

        }
    }

    private Node insert(Node node, int capacity, ParkingLot parkinglot, Node parent) {
        if (node == null) //base case, starts creating the nodes.
            return new Node(capacity, parkinglot, parent);

        // !!!!1
        if (capacity < node.capacity)
            node.left = insert(node.left, capacity, parkinglot, node);
        else if (capacity > node.capacity)
            node.right = insert(node.right, capacity, parkinglot, node);
        else
            return node; // if we have the same node, then there is no need to anything.

        /*after insertion, the height of the tree will change, therefore balance can be change,
        to find the right balance factor it should be write again.
        if we look at the root and there is no child of it, then its height should be equal to 1, but if it has another right or left child,its height should
        be equal to the one which is bigger.*/
        if (getHeight(node.left) >= getHeight(node.right)){
            node.height = getHeight(node.left) + 1; // +1 is our new nodes height
        } else{
            node.height = getHeight(node.right) + 1;
        }





        int balanceFactor = getBalance(node); //takes the balance factor to use it on rotations.

        /*now we are starting the AVL tree part, if balance is not balanced and there is a
        LEFT LEFT SITUATION which means if our nodes capacity is less than the root and if we add this to the left as we should
        the balance is not balanced which means balance factor = left-right is bigger than 1.*/

        if (balanceFactor > 1 && node.capacity < node.left.capacity)
            return rightRotate(node);

        //LEFT RIGHT SITUATION which means if our nodes capacity is bigger than the root and if we add this to the right as we should
        // the balance is not balanced and we should go back first to the left and then to the right.
        if (balanceFactor > 1 && node.capacity > node.left.capacity) {
            node.left = leftRotate(node.left);
            return rightRotate(node);
        }

        //RIGHT RIGHT SITUATION which means if our nodes capacity is bigger than the root and if we add this to the right as we should
        // the balance is not balanced which means balance factor = left-right is equal or less than -1.
        if (balanceFactor < -1 && node.capacity > node.right.capacity)
            return leftRotate(node);

        //RIGHT LEFT SITUATION which means if our nodes capacity is bless than the root and if we add this to the left as we should
        // the balance is not balanced and we should go back first to the right and then to the left.
        if (balanceFactor < -1 && node.capacity < node.right.capacity) {
            node.right = rightRotate(node.right);
            return leftRotate(node);
        }

        return node;
    }






    // RIGHT ROTATION
    private Node rightRotate(Node unbalancedNode) {


        // The new root after rotation is the left child of the unbalanced node
        Node newRoot = unbalancedNode.left;
        // If the new root is null, no rotation is needed since there is no unbalanced situation, just return the unbalanced node.
        if (newRoot == null) return unbalancedNode;



        // Saves the right child of the new root which will be reassigned after rotation.
        Node tempRightChild = newRoot.right;


        // now newroot is acts like our new root and we redesign it.
        newRoot.right = unbalancedNode;
        unbalancedNode.left = tempRightChild;

        //after the rotation, since nodes places are changed, their height value should be refactored.
        unbalancedNode.height = Math.max(getHeight(unbalancedNode.left), getHeight(unbalancedNode.right)) + 1;
        newRoot.height = Math.max(getHeight(newRoot.left), getHeight(newRoot.right)) + 1;


        /*Return the New Root: After the right rotation, the new root (the former left child of the unbalanced node)
        is returned, as it now represents the root of the subtree that was previously rooted at the unbalanced node.*/
        return newRoot;
    }





    // LEFT ROTATION
    private Node leftRotate(Node unbalancedNode) {

        // The new root after rotation is the right child of the unbalanced node
        Node newRoot = unbalancedNode.right;
        // If the new root is null, no rotation is needed since there is no unbalanced situation, just return the unbalanced node.
        if (newRoot == null) return unbalancedNode;


        // Saves the left child of the new root which will be reassigned after rotation.
        Node tempLeftChild = newRoot.left;



        // now newroot is acts like our new root and we redesign it.
        newRoot.left = unbalancedNode;
        unbalancedNode.right = tempLeftChild;


        // after the rotation, since nodes places are changed, their height value should be refactored.
        unbalancedNode.height = Math.max(getHeight(unbalancedNode.left), getHeight(unbalancedNode.right)) + 1;
        newRoot.height = Math.max(getHeight(newRoot.left), getHeight(newRoot.right)) + 1;

        /*Return the New Root: After the left rotation, the new root (the former right child of the unbalanced node)
        is returned, as it now represents the root of the subtree that was previously rooted at the unbalanced node.*/
        return newRoot;
    }




    //It traverses the tree starting from the root node and iterates down the tree for finding the parking lot with given capacity parameter. If it finds, returns the parking lot node, if it cannot returns null.
    public Node getNodebyCapacity(int capacity) {
        Node node = root;
        while (node != null) {
            if (capacity < node.capacity)
                node = node.left;
            else if (capacity > node.capacity)
                node = node.right;
            else
                return node;
        }
        return null;
    }



     //this function finds the smallest available node that has a capacity greater than or equal to the specified capacity.
    public Node findGreaterThan(int capacity) {
        //It traverses the tree starting from the root node and iterates down the tree.
        Node current = root;
        Node result = null;

        while (current != null) {
            // If the current node’s capacity is greater than or equal to the target capacity, it becomes a candidate for the result.
            // The search then continues to the left subtree to check if there is a smaller, but still valid, node.
            if (current.capacity >= capacity) {
                result = current;
                current = current.left;
            } // If the current node’s capacity is less than the target capacity, it moves to the right subtree to look for a larger node.
            else {
                current = current.right;
            }
        }
        //result holds the node with the smallest capacity that is greater than or equal to the specified capacity.
        return result;
    }

    public Node findLessThan(int capacity) {
        //It traverses the tree starting from the root node and iterates down the tree.
        Node current = root;
        Node result = null;

        // If the current node’s capacity is less or equal to the target capacity, it becomes a candidate for the result.
        // The search then continues to the right subtree to check if there is a larger, but still valid, node.
        while (current != null) {
            if (current.capacity <= capacity) {
                result = current;
                current = current.right;
            } // If the current node’s capacity is larger than the target capacity, it moves to the left subtree to look for a larger node.
            else {
                current = current.left;
            }
        }

        //result holds the node with the largest capacity that is smaller or equal to the specified capacity.
        return result;
    }



    /* The in-order predecessor of a node is the largest node that has a smaller capacity than the target node.
    There are two cases to find them:

    If the target node has a left child, the in-order predecessor
    is the maximum node in the left subtree which means it is the largest node with a smaller capacity.

    If the target node does not have a left child, the in-order
     predecessor is an parent node located while moving up the tree.
     We traverse from the root and track the last parent whose capacity is smaller
     than the target node's capacity, moving right if we encounter smaller nodes
     and left for larger ones. */

    public Node getInOrderPredecessor(Node node) {
        if (node == null) return null;

        // If the target node has a left subtree, return the maximum node in the left subtree.
        if (node.left != null) {
            return findMaximum(node.left);
        } else {
            // If there is no left subtree, traverse upwards to find the nearest parent
            // that is smaller than the target node.
            Node predecessor = null;
            Node parent = root; //Start from the root of the AVL tree.

            // Traverse from the root downwards to the target node, keeping track of the possible predecessor.
            while (parent != null) {
                if (node.capacity < parent.capacity) {
                    // If target node capacity is smaller than the current parent, move left.
                    parent = parent.left;
                } else if (node.capacity > parent.capacity) {
                    // If target node capacity is greater than the current parent, move right
                    // and update the current parent as a potential predecessor.
                    predecessor = parent;
                    parent = parent.right;
                } else {
                    // If capacities are equal, the target node is found, so we can exit the loop.
                    break;
                }
            }
            // Return the last valid predecessor found, or null if none exists.
            return predecessor;
        }
    }

    //goes through to right to find the greatest capacity node of the left subtree.
    public Node findMaximum(Node node) {
        while (node.right != null) {
            node = node.right;
        }
        return node;
    }



    /* The in-order successor of a node is the smallest node that has a larger capacity than the target node.
    There are two cases to find them:

    If the target node has a right child, the in-order successor
    is the minimum node in the right subtree which means it is the smallest node with a larger capacity.

    If the target node does not have a right child, the in-order
     successor is a parent node located while moving up the tree.
     We traverse from the root and track the last parent whose capacity is larger
     than the target node's capacity, moving left if we encounter larger nodes
     and left for smaller ones. */

    public Node getInOrderSuccessor(Node node) {
        if (node == null) return null;

        // If the target node has a right subtree, return the minimum node in the right subtree.
        if (node.right != null) {
            return findMinimum(node.right);
        } else {
            // If there is no right subtree, traverse upwards to find the nearest parent
            // that is bigger than the target node.
            Node successor = null;
            Node parent = root; //Start from the root of the AVL tree.

            // Traverse from the root downwards to the target node, keeping track of the possible successor.
            while (parent != null) {
                if (node.capacity < parent.capacity) {
                    // If target node capacity is larger than the current parent, move left.
                    successor = parent;
                    parent = parent.left;
                } else if (node.capacity > parent.capacity) {
                    // If target node capacity is less than the current parent, move right
                    // and update the current parent as a potential successor.
                    parent = parent.right;
                    // If capacities are equal, the target node is found, so we can exit the loop.
                } else {
                    break;
                }
            }

            // Return the last valid successor found, or null if none exists.
            return successor;
        }
    }

    //goes through to left to find the least capacity node of the right subtree.
        public Node findMinimum(Node node) {
            while (node.left != null) {
                node = node.left;
            }
            return node;
        }

}

