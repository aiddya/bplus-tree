package com.iddya.trees;

import java.util.ArrayList;

public class BPlusTree {
private BPTNode rootNode;

public BPlusTree(int degree)
{
    rootNode = new BPTNode(degree);
}

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
                sb.append(" | ");
            }
            sb.append(" || ");
        }
        sb.append(System.lineSeparator());
        array = newArray;
    }

    return sb.toString();
}

public void insert(double key, String value)
{
    BPTNode insertNode = rootNode;
    while (insertNode.getData() == null) {
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

    BPTNode newRoot = insertNode.insertIntoDataNode(key, value);
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
            }
        }
        searchNode = searchNode != null ? searchNode.getNext() : null;
    }
    return sb.toString();
}
}
