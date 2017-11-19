package com.iddya.trees;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

public class BPlusTree {
private int degree;
private BPTNode rootNode;

public BPlusTree(int degree)
{
    this.degree = degree;
    rootNode    = new BPTNode(BPTNode.Type.Root, degree);
}

@Override public String toString()
{
    StringBuilder sb       = new StringBuilder();
    Queue<BPTNode> toVisit = new LinkedList<BPTNode>();
    toVisit.add(rootNode);
    while (toVisit.peek() != null) {
        BPTNode curr                = toVisit.remove();
        ArrayList<BPTNode> children = curr.getChildren();
        sb.append(curr.toString());
        if (children != null) {
            toVisit.addAll(children);
        }
    }
    return sb.toString();
}

public void insert(double key, String value)
{
    System.out.println("Inserting " + key + "," + value);
    BPTNode insertNode = rootNode;
    while (insertNode.getData() == null) {
        ArrayList<Double> keys = insertNode.getKeys();
        int childIndex = keys.size();
        while (childIndex > 0) {
            if (keys.get(childIndex - 1) <= key) {
                break;
            }
            childIndex--;
        }
        insertNode = insertNode.getChildren().get(childIndex);
    }

    insertNode.insertIntoDataNode(key, value);
    System.out.println(this.toString());
}

public String search(double searchKey)
{
    BPTNode searchNode = rootNode;
    while (searchNode.getData() == null) {
        ArrayList<Double> keys = searchNode.getKeys();
        int childIndex = keys.size();
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
    return "Searching from " + startKey + " to " + endKey;
}
}
