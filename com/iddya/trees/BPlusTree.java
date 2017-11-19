package com.iddya.trees;

import java.util.ArrayList;
import java.util.ArrayDeque;

public class BPlusTree {
private BPTNode rootNode;

public BPlusTree(int degree)
{
    rootNode    = new BPTNode(BPTNode.Type.Root, degree);
}

@Override public String toString()
{
    StringBuilder sb       = new StringBuilder();
    ArrayDeque<ArrayList<BPTNode>> nodes = new ArrayDeque<>();
    ArrayList<BPTNode> array = new ArrayList<>();
    array.add(rootNode);
    sb.append(rootNode.toString());
    nodes.add(array);
    sb.append(System.lineSeparator());
    while(nodes.getLast().get(0).getData() == null) {
        array = new ArrayList<>();
        for (BPTNode node: nodes.getLast()) {
            array.addAll(node.getChildren());
            for(BPTNode node2: node.getChildren()) {
                sb.append(node2.toString());
                sb.append(" | ");
            }
            sb.append(" || ");
        }
        sb.append(System.lineSeparator());
        nodes.add(array);
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

    BPTNode newRoot = insertNode.insertIntoDataNode(key, value);
    if (newRoot != null)
    {
        rootNode = newRoot;
    }
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
