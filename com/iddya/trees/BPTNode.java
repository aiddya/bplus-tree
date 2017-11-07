package com.iddya.trees;

public class BPTNode {
  private NodeType type;

  public enum NodeType {
    Root,
    Internal,
    Leaf
  }

  public BPTNode(NodeType type) {
    this.type = type;
  }

  public NodeType getType() {
    return this.type;
  }

  protected void setType() {
    this.type = type;
  }
}
