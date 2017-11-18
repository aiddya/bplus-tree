package com.iddya.trees;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

public class BPlusTree {
int degree;
BPTNode rootNode;

public BPlusTree(int degree)
{
    this.degree = degree;
    rootNode    = new BPTNode(BPTNode.Type.Root, degree);
}

public String toString()
{
    StringBuilder sb       = new StringBuilder();
    Queue<BPTNode> toVisit = new LinkedList<BPTNode>();
    toVisit.add(rootNode);
    while (toVisit.peek() != null) {
        BPTNode curr                = toVisit.remove();
        ArrayList<BPTNode> children = curr.getChildren();
        sb.append(curr.toString());
        if (children != null) {
            for (int i = 0; i < children.size(); i++)
                toVisit.add(children.get(i));
        }
    }
    return sb.toString();
}

public void insert(double key, String value)
{
    System.out.println("Inserting " + key + "," + value);
    rootNode.insertIntoDataNode(key, value);
    System.out.println(this.toString());
}

public String search(double key)
{
    return "Searching for " + key;
}

public String searchRange(double startKey, double endKey)
{
    return "Searching from " + startKey + " to " + endKey;
}
}
