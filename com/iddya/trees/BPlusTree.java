package com.iddya.trees;

import java.util.ArrayList;

/**
 * The main B+ tree class. Handles search and provides public API
 * for insert and search.
 */
public class BPlusTree {
/**
 * Holds the root node of the B+ tree
 */
private BPTNode rootNode;

/**
 * Initializes a B+ tree of given degree.
 *
 * @param degree: Degree of the new B+ tree
 */
public BPlusTree(int degree)
{
    rootNode = new BPTNode(degree);
}

/**
 * Performs a BFS to print the tree. Only used for debugging.
 *
 * @return String: A representation of the B+ tree
 */
@Override public String toString()
{
    StringBuilder sb         = new StringBuilder();
    ArrayList<BPTNode> array = new ArrayList<>();
    array.add(rootNode);
    sb.append(rootNode.toString());
    sb.append(System.lineSeparator());
    while (array.get(0).getData() == null) {
        ArrayList<BPTNode> newArray = new ArrayList<>();
        for (BPTNode node: array) {
            for (BPTNode childNode: node.getChildren()) {
                newArray.add(childNode);
                sb.append(childNode.toString());
                sb.append("| ");
            }
            sb.append("| ");
        }
        sb.append(System.lineSeparator());
        array = newArray;
    }

    return sb.toString();
}

/**
 * Inserts a new key value pair into the B+ tree. Calls insertData function of
 * BPTNode class. If the function calls returns a BPTNode object indicating a
 * root node split, the returned object is used as the new root node.
 *
 * @param key: Key to be inserted
 * @param value: Value associated with the key
 */
public void insert(double key, String value)
{
    // Start from root and find a node that contains data and keys close to the
    // key being inserted
    BPTNode insertNode = rootNode;
    while (insertNode.getData() == null) {
        int childIndex = insertNode.searchInternalNode(key);
        insertNode = insertNode.getChildren().get(childIndex);
    }

    BPTNode newRoot = insertNode.insertData(key, value);

    // If newRoot is not null, it has been replaced. Update rootNode variable.
    if (newRoot != null) {
        rootNode = newRoot;
    }
}

/**
 * Searches for a key in a B+ tree and returns the values associated with the
 * key.
 *
 * @param searchKey: Key being searched for
 * @return String: Values associated with the key with comma separator.
 */
public String search(double searchKey)
{
    // Navigate the tree till a node with data and keys close to the one being
    // searched for is found
    BPTNode searchNode = rootNode;
    while (searchNode.getData() == null) {
        int childIndex = searchNode.searchInternalNode(searchKey);
        searchNode = searchNode.getChildren().get(childIndex);
    }

    int index = searchNode.searchDataNode(searchKey);

    // Since searchDataNode returns first element greater than or equal to
    // current key, confirm that the key actually exists
    if (index < searchNode.getData().size() &&
        searchNode.getData().get(index).getKey() == searchKey) {
        return searchNode.getData().get(index).getValues();
    }

    return "Null";
}

/**
 * Searches for keys in a B+ tree that are in between bounds provided in
 * method arguments and returns them in a String.
 *
 * @param startKey: Minimum key being searched for
 * @param endKey: Maximum key being searched for
 * @return String: Keys and values with comma separator.
 */
public String searchRange(double startKey, double endKey)
{
    StringBuilder sb   = new StringBuilder();
    BPTNode searchNode = rootNode;

    // Navigate the tree till a node with data is found
    while (searchNode.getData() == null) {
        int childIndex = searchNode.searchInternalNode(startKey);
        searchNode = searchNode.getChildren().get(childIndex);
    }

    // Loop through the leaf nodes until a key greater than the endKey is found
    while (searchNode != null) {
        for (KeyValue record: searchNode.getData()) {
            if (record.getKey() > endKey) {
                // Set searchNode to null to exit the outer loop
                searchNode = null;
                break;
            } else if (record.getKey() >= startKey) {
                sb.append(record.toString());
                sb.append(",");
            }
        }

        // As long as endKey has not been hit, move to next node in linked list
        searchNode = searchNode != null ? searchNode.getNext() : null;
    }
    if (sb.length() != 0) {
        sb.delete(sb.length() - 1, sb.length());
        return sb.toString();
    } else {
        return "Null";
    }
}
}
