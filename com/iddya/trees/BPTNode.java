package com.iddya.trees;

import java.util.ArrayList;

public class BPTNode {
private int nodeDegree;
private Type nodeType;
private ArrayList<BPTNode> children;
private ArrayList<KeyValue> data;
private ArrayList<Double> keys;
private BPTNode parent;
private BPTNode prev;
private BPTNode next;

public enum Type {
    Root,
    Internal,
    Leaf
}

BPTNode(int degree)
{
    this.nodeType   = Type.Root;
    this.nodeDegree = degree;
    children        = null;
    data            = new ArrayList<>();
    keys            = null;
}

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

private BPTNode getParent()
{
    return this.parent;
}

private void setParent(BPTNode parentNode)
{
    this.parent = parentNode;
}

private BPTNode getPrev()
{
    if (this.nodeType == Type.Leaf) {
        return this.prev;
    } else {
        return null;
    }
}

private void setPrev(BPTNode prevNode)
{
    if (this.nodeType == Type.Leaf) {
        this.prev = prevNode;
    }
}

BPTNode getNext()
{
    if (this.nodeType == Type.Leaf) {
        return this.next;
    } else {
        return null;
    }
}

private void setNext(BPTNode nextNode)
{
    if (this.nodeType == Type.Leaf) {
        this.next = nextNode;
    }
}

ArrayList<KeyValue> getData()
{
    return data;
}

private void setData(ArrayList<KeyValue> data)
{
    this.data = data;
}

ArrayList<Double> getKeys()
{
    return keys;
}

ArrayList<BPTNode> getChildren()
{
    return children;
}

private Boolean isRoot()
{
    return nodeType == Type.Root;
}

private Boolean isInternal()
{
    return nodeType == Type.Internal;
}

private Boolean isLeaf()
{
    return nodeType == Type.Leaf;
}

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

BPTNode insertIntoDataNode(double key, String value)
{
    BPTNode retNode = null;

    if (!isRoot() && !isLeaf()) {
        // Shouldn't be here
        return retNode;
    }

    // TODO: Clean-up the excessive cases
    if (data.size() == 0) {
        // Node is empty, create and add a KeyValue object
        KeyValue newKey = new KeyValue(key, value);
        data.add(newKey);
    } else {
        // Node is not empty, look for a spot to insert the new key
        for (int i = 0; i < data.size(); i++) {
            KeyValue currRecord = data.get(i);
            if (currRecord.getKey() == key) {
                // Key exists, insert value into the same key
                currRecord.addValue(value);
                break;
            } else if (currRecord.getKey() > key) {
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
            children = new ArrayList<>();
            children.add(newLeaf2);
            children.add(newLeaf);
            keys = new ArrayList<>();
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
            double splitKey = newLeaf.getData().get(0).getKey();
            retNode = parent.insertInternal(splitKey, newLeaf);
        }
    }

    return retNode;
}

private BPTNode insertInternal(double newKey, BPTNode newChild)
{
    BPTNode retNode = null;

    int insertIndex = keys.size();
    while (insertIndex > 0) {
        if (keys.get(insertIndex - 1) <= newKey) {
            break;
        }
        insertIndex--;
    }

    children.add(insertIndex + 1, newChild);
    keys.add(insertIndex, newKey);
    newChild.setParent(this);

    if (keys.size() == nodeDegree) {
        BPTNode newInt = new BPTNode(Type.Internal, nodeDegree);
        int splitIndex = (int)Math.ceil(nodeDegree / 2.0);
        for (int i = splitIndex; i < nodeDegree; i++) {
            BPTNode child = children.remove(splitIndex);
            child.setParent(newInt);
            newInt.getChildren().add(child);
            newInt.getKeys().add(keys.remove(splitIndex));
        }
        BPTNode child = children.remove(splitIndex);
        child.setParent(newInt);
        newInt.getChildren().add(child);
        double splitKey = keys.remove(splitIndex - 1);
        if (this.getParent() == null) {
            // Overfull node is Root, new node should be created
            BPTNode newRoot = new BPTNode(Type.Root, nodeDegree);
            newRoot.getKeys().add(splitKey);
            newRoot.getChildren().add(this);
            newRoot.getChildren().add(newInt);
            this.nodeType = Type.Internal;
            this.parent   = newRoot;
            newInt.setParent(newRoot);
            retNode = newRoot;
        } else {
            retNode = this.getParent().insertInternal(splitKey, newInt);
        }
    }
    return retNode;
}
}
