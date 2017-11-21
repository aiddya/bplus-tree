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
    // Start from root and find a node that contains data close to what we're looking for
    BPTNode insertNode = rootNode;
    while (insertNode.getData() == null) {
        // Start from the end of each node and stop when a key less than what we're
        // looking for is on the immediate left
        ArrayList<Double> keys = insertNode.getKeys();
        int childIndex         = keys.size();
        while (childIndex > 0) {
            if (keys.get(childIndex - 1) <= key) {
                break;
            }
            childIndex--;
        }
        insertNode = insertNode.getChildren().get(childIndex);
    }

    BPTNode newRoot = insertNode.insertData(key, value);

    // If newRoot is not null, it has been replaced. Update rootNode variable.
    if (newRoot != null) {
        rootNode = newRoot;
    }
}

public String search(double searchKey)
{
    BPTNode searchNode = rootNode;
    while (searchNode.getData() == null) {
        ArrayList<Double> keys = searchNode.getKeys();
        int childIndex         = keys.size();
        while (childIndex > 0) {
            if (keys.get(childIndex - 1) <= searchKey) {
                break;
            }
            childIndex--;
        }
        searchNode = searchNode.getChildren().get(childIndex);
    }

    for (KeyValue kv: searchNode.getData()) {
        if (kv.getKey() == searchKey) {
            return kv.getValues();
        } else if (kv.getKey() > searchKey) {
            return "Null";
        }
    }
    return "Null";
}

public String searchRange(double startKey, double endKey)
{
    StringBuilder sb   = new StringBuilder();
    BPTNode searchNode = rootNode;

    while (searchNode.getData() == null) {
        ArrayList<Double> keys = searchNode.getKeys();
        int childIndex         = keys.size();
        while (childIndex > 0) {
            if (keys.get(childIndex - 1) <= startKey) {
                break;
            }
            childIndex--;
        }
        searchNode = searchNode.getChildren().get(childIndex);
    }

    while (searchNode != null) {
        for (KeyValue record: searchNode.getData()) {
            if (record.getKey() > endKey) {
                searchNode = null;
                break;
            } else if (record.getKey() >= startKey) {
                sb.append(record.toString());
                sb.append(", ");
            }
        }
        searchNode = searchNode != null ? searchNode.getNext() : null;
    }
    sb.delete(sb.length() - 2, sb.length());
    return sb.toString();
}
}
