package com.iddya.trees;

import java.util.ArrayList;

/**
 * Node class of B+ tree. Handles insertion and splitting
 */
public class BPTNode {
/**
 * Degree of the B+ tree, duplicated in every node for use in splitting
 */
private int nodeDegree;

/**
 * Type of the node
 */
private Type nodeType;

/**
 * Children of current node
 */
private ArrayList<BPTNode> children;

/**
 * Key value pairs of type KeyValue stored in an ArrayList
 * Used in leaf nodes and root node when it stores records.
 */
private ArrayList<KeyValue> data;

/**
 * Keys stored in internal nodes and root node whn it doesn't store data.
 */
private ArrayList<Double> keys;

/**
 * Parent node of the current node
 */
private BPTNode parent;

/**
 * Previous node in a linked list
 */
private BPTNode prev;

/**
 * Next node in a linked list
 */
private BPTNode next;

/**
 * Enum type to store type of nodes
 */
public enum Type {
    Root,
    Internal,
    Leaf
}

/**
 * Initializes a new root node for a new B+ tree.
 * The root node created from this method can store records.
 *
 * @param degree: Degree of the B+ tree
 */
BPTNode(int degree)
{
    this.nodeType   = Type.Root;
    this.nodeDegree = degree;
    children        = null;
    data            = new ArrayList<>();
    keys            = null;
}

/**
 * Initializes a new node based on the type.
 * The root node created from this method can only store keys, not records.
 *
 * @param nodeType: Type of the new node
 * @param degree: Degree of the new node
 */
private BPTNode(Type nodeType, int degree)
{
    this.nodeType   = nodeType;
    this.nodeDegree = degree;
    if (nodeType == Type.Leaf) {
        children = null;
        data     = new ArrayList<>();
        keys     = null;
    } else if (nodeType == Type.Root || nodeType == Type.Internal) {
        children = new ArrayList<>();
        data     = null;
        keys     = new ArrayList<>();
    }
}

/**
 * Gets the parent of the current node
 *
 * @return BPTNode: The parent node
 */
private BPTNode getParent()
{
    return this.parent;
}

/**
 * Sets the parent of the current node
 *
 * @param parentNode: The new parent node
 */
private void setParent(BPTNode parentNode)
{
    this.parent = parentNode;
}

/**
 * Gets the previous node in leaf node linked list
 *
 * @return BPTNode: Previous node in leaf node linked list
 */
private BPTNode getPrev()
{
    if (this.nodeType == Type.Leaf) {
        return this.prev;
    } else {
        return null;
    }
}

/**
 * Sets the previous node in leaf nodes to create a linked list
 *
 * @param prevNode: Node that is previous from the current node
 */
private void setPrev(BPTNode prevNode)
{
    if (this.nodeType == Type.Leaf) {
        this.prev = prevNode;
    }
}

/**
 * Gets the next node in leaf node linked list
 *
 * @return BPTNode: Next node in leaf node linked list
 */
BPTNode getNext()
{
    if (this.nodeType == Type.Leaf) {
        return this.next;
    } else {
        return null;
    }
}

/**
 * Sets the next node in leaf nodes to create a linked list
 *
 * @param nextNode: Node that is next to the current node
 */
private void setNext(BPTNode nextNode)
{
    if (this.nodeType == Type.Leaf) {
        this.next = nextNode;
    }
}

/**
 * Gets the key-value pair data of the current node in an ArrayList
 *
 * @return ArrayList<KeyValue> containing key-value pairs of the current node
 */
ArrayList<KeyValue> getData()
{
    return data;
}

/**
 * Sets the data ArrayList in the current node
 *
 * @param data: New data to be set
 */
private void setData(ArrayList<KeyValue> data)
{
    this.data = data;
}

/**
 * Gets the keys of the current node in an ArrayList
 *
 * @return ArrayList<Double> containing keys of current node
 */
ArrayList<Double> getKeys()
{
    return keys;
}

/**
 * Gets the children of the current node in an ArrayList
 *
 * @return ArrayList<BPTNode> containing children of current node
 */
ArrayList<BPTNode> getChildren()
{
    return children;
}

/**
 * Checks if the current node is a root node
 *
 * @return Boolean: True if the current node is a root node, false otherwise
 */
private Boolean isRoot()
{
    return nodeType == Type.Root;
}

/**
 * Used only for debugging purpose.
 *
 * @return String: Returns all keys / key value pairs seperated by space.
 */
@Override public String toString()
{
    StringBuilder sb = new StringBuilder();
    if (data != null) {
        for (KeyValue kv: data) {
            sb.append(kv.toString());
            sb.append(" ");
        }
    } else if (keys != null) {
        for (Double key: keys) {
            sb.append(key.toString());
            sb.append(" ");
        }
    }
    return sb.toString();
}

/**
 * Searches internal nodes (also root node without data) for keys.
 * Uses binarySearchInt method to perform binary search.
 *
 * @param searchKey: Key to look for
 * @return int: Returns the index of the first key greater than the search key.
 * Returns the size of the array if the search key is greater than all elements
 * in the array.
 */
public int searchInternalNode(double searchKey)
{
    if (keys != null)
        return binarySearchInt(searchKey, 0, keys.size());
    else
        return -1;
}

/**
 * A modified binary search helper method for searchInternalNode.
 *
 * @param searchKey: Key to look for in a sub-array
 * @param first: First key in sub-array
 * @param last: Last key in sub-array
 * @return int: Returns the index of the first key greater than the search key.
 */
private int binarySearchInt(double searchKey, int first, int last)
{
    int retValue = -1;

    if (last - first == 1) {
        retValue = searchKey < keys.get(first) ? first : last;
    } else {
        int mid = (first + last) / 2;

        if (searchKey < keys.get(mid)) {
            retValue = binarySearchInt(searchKey, first, mid);
        } else if (searchKey >= keys.get(mid)) {
            retValue = binarySearchInt(searchKey, mid, last);
        }
    }
    return retValue;
}

/**
 * Searches data nodes (also root node with data) for keys.
 * Uses binarySearchData method to perform binary search.
 *
 * @param searchKey: Key to look for
 * @return int: Returns the index of the first key greater than or equal to the
 * search key. Returns the size of the array if the search key is greater than
 * all elements in the array.
 */
public int searchDataNode(double searchKey)
{
    if (data != null)
        return binarySearchData(searchKey, 0, data.size());
    else
        return -1;
}

/**
 * A modified binary search helper method for searchDataNode.
 *
 * @param searchKey: Key to look for in a sub-array
 * @param first: First key in sub-array
 * @param last: Last key in sub-array
 * @return int: Returns the index of the first key greater than or equal to the
 * search key.
 */
private int binarySearchData(double searchKey, int first, int last)
{
    int retValue = -1;

    if (last - first == 1) {
        retValue = searchKey <= data.get(first).getKey() ? first : last;
    } else {
        int mid = (first + last) / 2;

        if (searchKey < data.get(mid).getKey()) {
            retValue = binarySearchData(searchKey, first, mid);
        } else if (searchKey >= data.get(mid).getKey()) {
            retValue = binarySearchData(searchKey, mid, last);
        }
    }
    return retValue;
}

/**
 * Insert into leaf node / root node with records:
 * ===============================================
 * Inserts a new key value pair into the current node. When the node is
 * overfull after insertion, it splits the current node into two and calls
 * insertInternal function on the parent node. If insertInternal returns a new
 * root node, it will be returned from this function too. Null is returned
 * otherwise.
 *
 * The base case is when the current node is a root node. Then, two new leaf
 * nodes are created and linked to the root.
 *
 * @param key: New key that has to be inserted into the node
 * @param value: New value that accompanies the key
 * @return BPTNode: The new root node when it splits, null otherwise
 */
BPTNode insertData(double key, String value)
{
    BPTNode retNode = null;

    if (data == null) {
        // Shouldn't be here
        return null;
    }

    if (data.size() == 0) {
        // Node is empty, create and add a KeyValue object
        KeyValue newKey = new KeyValue(key, value);
        data.add(newKey);
    } else {
        // Node is not empty, look for a spot to insert the new key
        int insertIndex = searchDataNode(key);
        if (insertIndex < data.size() &&
            data.get(insertIndex).getKey() == key) {
            // Key exists, insert value to the same key
            data.get(insertIndex).addValue(value);
        } else {
            // Create and add a new key
            KeyValue newKey = new KeyValue(key, value);
            data.add(insertIndex, newKey);
        }
    }

    // Node is overfull, need to split it
    if (data.size() == nodeDegree) {
        BPTNode newLeaf = new BPTNode(Type.Leaf, nodeDegree);

        // Add half the nodes to a new node.
        // Splits 6 elements to 3+3 and 5 elements to 2+3
        for (int i = nodeDegree / 2; i < nodeDegree; i++)
            newLeaf.getData().add(data.remove(nodeDegree / 2));

        if (isRoot()) {
            // Current node is root, create another leaf node to add to root
            BPTNode newLeaf2 = new BPTNode(Type.Leaf, nodeDegree);

            // Move data from current node to new leaf node
            newLeaf2.setData(data);
            data = null;

            // Add new children to root node
            children = new ArrayList<>();
            children.add(newLeaf2);
            children.add(newLeaf);

            // Add the leftmost key of the right node as the root node key
            keys = new ArrayList<>();
            keys.add(newLeaf.getData().get(0).getKey());

            // Set parents and setup the linked list
            newLeaf.setParent(this);
            newLeaf2.setParent(this);
            newLeaf2.setNext(newLeaf);
            newLeaf.setPrev(newLeaf2);
        } else {
            // Current node is a leaf, add new node to the linked list
            if (this.getNext() != null) {
                this.getNext().setPrev(newLeaf);
                newLeaf.setNext(this.getNext());
            }
            this.setNext(newLeaf);
            newLeaf.setPrev(this);

            // Find the split key and add it to the parent along with the newly
            // created leaf node
            double splitKey = newLeaf.getData().get(0).getKey();
            retNode = parent.insertInternal(splitKey, newLeaf);
        }
    }

    return retNode;
}

/**
 * Insert into internal node / root node without records:
 * ======================================================
 * Inserts a new child node along with its key into the current node.
 * When the node is overfull after insert, it splits the current node into two
 * and calls the same function in the parent node.
 *
 * The base case is when the current node is a root node. Then, a new root node
 * is created and returned. If root node does not split, the function returns
 * null.
 *
 * @param newKey: New key that has to be inserted into the node
 * @param newChild: New child node that has to be inserted into the node
 * @return BPTNode: The new root node when it splits, null otherwise
 */
private BPTNode insertInternal(double newKey, BPTNode newChild)
{
    BPTNode retNode = null;

    // Find the appropriate position for the new key to be inserted into
    int insertIndex = keys.size();
    while (insertIndex > 0) {
        if (keys.get(insertIndex - 1) <= newKey) {
            break;
        }
        insertIndex--;
    }

    // Add the new key and child to the current node
    children.add(insertIndex + 1, newChild);
    keys.add(insertIndex, newKey);
    newChild.setParent(this);

    // Node is overfull, split it
    if (keys.size() == nodeDegree) {
        BPTNode newInt = new BPTNode(Type.Internal, nodeDegree);

        // Split using same logic and B-tree
        // Splits 6 elements to 2+1+3 and 5 elements to 2+1+2
        int splitIndex = (int)Math.ceil(nodeDegree / 2.0);

        // Copy other half of children and keys to the new node
        for (int i = splitIndex; i < nodeDegree; i++) {
            BPTNode child = children.remove(splitIndex);
            child.setParent(newInt);
            newInt.getChildren().add(child);
            newInt.getKeys().add(keys.remove(splitIndex));
        }

        // Pop the last child separately as the key accompanying it should be
        // the middle index, not go into the new node
        BPTNode child = children.remove(splitIndex);
        child.setParent(newInt);
        newInt.getChildren().add(child);

        // Get the middle index for the two newly split nodes
        double splitKey = keys.remove(splitIndex - 1);

        // If the current node is the root handle separately, else recurse
        if (this.getParent() == null) {
            // Overfull node is root, a new root node should be created
            BPTNode newRoot = new BPTNode(Type.Root, nodeDegree);
            newRoot.getKeys().add(splitKey);
            newRoot.getChildren().add(this);
            newRoot.getChildren().add(newInt);
            this.nodeType = Type.Internal;
            this.parent   = newRoot;
            newInt.setParent(newRoot);

            // Return new root node for BPlusTree class to update
            retNode = newRoot;
        } else {
            retNode = this.getParent().insertInternal(splitKey, newInt);
        }
    }
    return retNode;
}
}
