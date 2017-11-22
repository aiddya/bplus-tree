# Implementation of a memory-resident B+ tree

## Introduction

This is an implementation of B+ tree in Java with support for duplicate values, insert, search and range search operations. This program reads an input file to determine the degree of B+ tree, operations to be performed on it and writes the output of those operations to a file.

## Class Structure

There are four classes: `treesearch`, `BPlusTree`, `BPTNode` and `KeyValue`. `BPlusTree`, `BPTNode` and `KeyValue` are part of a package called `com.iddya.trees`.

`treesearch` class reads the input file, parses it to determine the degree of B+ tree to be created. After creating a B+ tree of the required degree, it reads the rest of the file to parse Insert and Search commands and calls the corresponding methods implemented in `BPlusTree` class to insert and search nodes. It also writes the output from these method calls to a file.

`BPlusTree` and `BPTNode` classes contain the implementation of the B+ tree.  `BPlusTree` maintains a reference to the root node, exposes a method for inserting keys and values and implements search and range search functionality.

Each node in the B+ tree is of type `BPTNode`. `BPTNode` class maintains references to keys, child nodes and key value pairs of type `KeyValue`. It also implements the insert functionality, splits nodes when they are overfull and propagates the middle keys from split nodes up the tree.

`KeyValue` class holds key and value pairs for the B+ tree. It is capable of holding multiple values for the same key.

***
The directory structure is:
```sh
.
├── com
│   └── iddya
│       └── trees
│           ├── BPlusTree.java
│           ├── BPTNode.java
│           └── KeyValue.java
├── Makefile
└── treesearch.java
```
## Class variables

### BPlusTree class

| Field Type    | Field Name    | Content                      |
|:-------------:|:-------------:| ---------------------------- |
| `BPTNode`     | `rootNode`    | The root node of the B+ tree |

### BPTNode class

| Field Type            | Field Name    | Content                                                         |
|:---------------------:|:-------------:| --------------------------------------------------------------- |
| `int`                 | `nodeDegree`  | The degree of the B+ tree                                       |
| `Type`                | `nodeType`    | The node type as a member of `Type` enum                        |
| `ArrayList<BPTNode>`  | `children`    | References to child nodes of the current node                   |
| `ArrayList<KeyValue>` | `data`        | `KeyValue` objects containing the records (leaf and root nodes) |
| `ArrayList<Double>`   | `keys`        | Keys (internal and root nodes)                                  |
| `BPTNode`             | `parent`      | Parent node                                                     |
| `BPTNode`             | `prev`        | Previous node in the linked list (leaf only)                    |
| `BPTNode`             | `next`        | Next node in the linked list (leaf only)                        |

### Key Value class

| Field Type          | Field Name    | Content                                |
|:-------------------:|:-------------:| -------------------------------------- |
| `double`            | `key`         | Key                                    |
| `ArrayList<String>` | `values`      | A list of values associated with `key` |


## Function Prototypes

### treesearch class

```java
public static void main(String[] args)
```
This is the main entry point of the program the reads and parses the input file to create a B+ tree and finally writes the output to a file.

### BPlusTree class

```java
public BPlusTree(int degree)
```
Constructs a B+ tree of degree equal to the `degree` parameter.

```java
public void insert(double key, String value)
```
This method inserts a new key value pair into the B+ tree. It calls `insertData` method of `BPTNode` class. If the method call returns a `BPTNode` object indicating a root node split, the returned object is used as the new root node.

```java
public String search(double searchKey)
```
This method searches for a key in a B+ tree and returns the values associated with the key formatted with comma separator.

```java
public String searchRange(double startKey, double endKey)
```
`searchRange` method searches for keys in a B+ tree that are in between bounds provided in method arguments. It uses the leaf node linked list for efficient range retrieval. Every key value pair in the output is surrounded with parentheses and separated by commas.

### BPTNode class

```java
BPTNode(int degree)
```
This constructor initializes a new root node for a B+ tree. The `degree` argument is used to set `nodeDegree` field. The root node created from this constructor is capable of holding records. This is the only constructor available to `BPlusTree` class.

```java
private BPTNode(Type nodeType, int degree)
```
This method initializes different types of nodes based on the `nodeType` argument and `degree` argument. This is a private constuctor used to create new nodes during splitting of overfull nodes. The root node created from this constructor is not capable of holding records.

```java
private Boolean isRoot()
```
This method rSeturns true if the current node is the root node.

```java
public int searchInternalNode(double searchKey)
```
`searchInternalNode` method searches internal nodes and root node without data for keys. It returns the index of the first key greater than the search key. If the search key is greater than all elements in the array, it returns the size of the array. It uses `binarySearchInt` method to perform binary search.

```java
private int binarySearchInt(double searchKey, int first, int last)
```
This method uses a modified version of binary search to return the index of the first key greater than the search key. This method is only called from `searchInternalNode` method.

```java
public int searchDataNode(double searchKey)
```
This method searches data nodes and root node with data for keys. It returns the index of the first key greater than or equal to the search key. If the search key is greater than all elements in the array, it returns the size of the array. It uses `binarySearchData` method to perform binary search.

```java
private int binarySearchData(double searchKey, int first, int last)
```
This method uses a modified version of binary search to return the index of the first key greater than or equal to the search key. This method is only called from `searchDataNode` method.

```java
BPTNode insertData(double key, String value)
```
`insertData` method is used to insert into leaf node or a root node with records. It inserts a new key value pair into the current node. When the node is overfull after insertion, it splits the current node into two and calls `insertInternal` method on the parent node. If `insertInternal` returns a new root node, it will be returned from this method too. Null is returned otherwise.

```java
private BPTNode insertInternal(double newKey, BPTNode newChild)
```
`insertInternal` method inserts a new key and child node into an internal node or a root node without records. When the node is overfull after insert, it splits the current node into two and calls itself in the context of the parent node. The base case in this recursion is when the current node is a root node. Then, a new root node is created and returned. If root node does not split, the method returns null.

### KeyValue class

```java
KeyValue(double key, String value)
```
This method instantiates an object of `KeyValue` class.

```java
String getValues()
```
This method returns a string containing all values associated with the current `KeyValue` object separated by a comma. Used by `search` method of `BPlusTree` class.

```java
void addValue(String value)
```
This method adds a new value to an existing `KeyValue` object.

```java
@Override public String toString()
```
This method overrides `Object.toString()` method by returning each value associated with the key as a pair surrounded by parentheses with comma separator.
