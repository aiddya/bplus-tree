package com.iddya.trees;

import java.util.ArrayList;

public class BPTNode {
private int nodeDegree;
private Type nodeType;
private BPTNode parent;
private BPTNode prev;
private BPTNode next;
private ArrayList<KeyValue> data;
private ArrayList<Double> keys;
private ArrayList<BPTNode> children;

public enum Type {
    Root,
    Internal,
    Leaf
}

public BPTNode(Type nodeType, int degree)
{
    this.nodeType   = nodeType;
    this.nodeDegree = degree;
    if (nodeType == Type.Root || nodeType == Type.Leaf) {
        children = null;
        data     = new ArrayList<KeyValue>();
        keys     = null;
    } else if (nodeType == Type.Internal) {
        children = new ArrayList<BPTNode>();
        data     = null;
        keys     = new ArrayList<Double>();
    }
}

public Type getType()
{
    return this.nodeType;
}

protected BPTNode getParent()
{
    return this.parent;
}

protected void setParent(BPTNode parentNode)
{
    this.parent = parentNode;
}

protected BPTNode getPrev()
{
    if (this.nodeType == Type.Leaf) {
        return this.prev;
    } else {
        return null;
    }
}

protected void setPrev(BPTNode prevNode)
{
    if (this.nodeType == Type.Leaf) {
        this.prev = prevNode;
    }
}

protected BPTNode getNext()
{
    if (this.nodeType == Type.Leaf) {
        return this.next;
    } else {
        return null;
    }
}

protected void setNext(BPTNode nextNode)
{
    if (this.nodeType == Type.Leaf) {
        this.next = nextNode;
    }
}

protected ArrayList<KeyValue> getData()
{
    return data;
}

private void setData(ArrayList<KeyValue> data)
{
    this.data = data;
}

protected ArrayList<Double> getKeys()
{
    return keys;
}

protected ArrayList<BPTNode> getChildren()
{
    return children;
}

protected Boolean isRoot()
{
    return nodeType == Type.Root;
}

protected Boolean isInternal()
{
    return nodeType == Type.Internal;
}

protected Boolean isLeaf()
{
    return nodeType == Type.Leaf;
}

public String toString()
{
    StringBuilder sb = new StringBuilder();
    if (data != null)
    {
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

protected void insertIntoDataNode(double key, String value)
{
    if (!isRoot() && !isLeaf()) {
        // Shouldn't be here
        return;
    }

    // TODO: Clean-up the excessive cases
    if (data.size() == 0) {
        // Node is empty, create and add a KeyValue object
        KeyValue newKey = new KeyValue(key, value);
        data.add(newKey);
    } else {
        // Node is not empty, look for a spot to insert the new key
        // TODO: Implement binary search
        for (int i = 0; i < data.size(); i++) {
            KeyValue currKV = data.get(i);
            if (currKV.getKey() == key) {
                // Key exists, insert value into the same key
                currKV.addValue(value);
                break;
            } else if (currKV.getKey() > key) {
                KeyValue newKey = new KeyValue(key, value);
                // Insert before the current element
                data.add(i, newKey);
                break;
            } else if (i + 1 == data.size()) {
                // Key is the largest yet. Append at the end.
                KeyValue newKey = new KeyValue(key, value);
                data.add(newKey);
                break;
            }
        }
    }

    // Node is overfull, need to split it
    if (data.size() == nodeDegree) {
        BPTNode newLeaf = new BPTNode(Type.Leaf, nodeDegree);

        for (int i = nodeDegree / 2; i < nodeDegree; i++)
            newLeaf.getData().add(data.remove(nodeDegree / 2));

        if (isRoot()) {
            // Current node is Root, create another Leaf node to add to Root
            BPTNode newLeaf2 = new BPTNode(Type.Leaf, nodeDegree);
            newLeaf2.setData(data);
            data     = null;
            children = new ArrayList<BPTNode>();
            children.add(newLeaf2);
            children.add(newLeaf);
            keys = new ArrayList<Double>();
            keys.add(newLeaf.getData().get(0).getKey());
            newLeaf.setParent(this);
            newLeaf2.setParent(this);
            newLeaf2.setNext(newLeaf);
            newLeaf.setPrev(newLeaf2);
        } else {
            // Current Node is Leaf, add new node to LinkedList
            if (this.getNext() != null) {
                this.getNext().setPrev(newLeaf);
                newLeaf.setNext(this.getNext());
            }
            this.setNext(newLeaf);
            newLeaf.setPrev(this);
            this.getParent().insertIntoInternalNode(key, newLeaf);
        }
    }
}

protected void insertIntoInternalNode(double key, BPTNode newChild)
{
}
}
