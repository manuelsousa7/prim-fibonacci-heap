package ads.neeraj2608.types.fheapscheme;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * This class represents a Fibonacci heap (F-heap). It supports the following
 * F-heap operations (please see documentation of individual methods for more
 * details):
 * <ul>
 * <li><b>insert</b> insert a node into the graph
 * <li><b>decreaseKey</b> decrease the key or cost of a node
 * <li><b>deleteMin</b> delete the node with the minimum key or cost
 * </ul>
 */
public class FHeap{

  private FHeapNode           min;        // the minimum node in the heap at any given time

  private List<FHeapNode>     nodeList;   // the index of this heap node corresponds to the index of the node in the graph adjList.
                                           // Used for easy access but not important for any of the F-Heap operations in itself.

  private int                 size;

  HashMap<Integer, FHeapNode> nodeDegrees; // hash map used for merging root nodes after a delete min operation

  public FHeap(){
    size = 0;
    nodeList = new ArrayList<FHeapNode>();
    nodeDegrees = new HashMap<Integer, FHeapNode>();
  }

  /**
   * Inserts a new Node into the heap with the specified key
   * 
   * @param key
   *          key of the node to insert
   */
  public void insert(int key){
    FHeapNode newNode = new FHeapNode(size, key, 0, 0, null, null, null, null, false);
    nodeList.add(newNode);
    size++;
    if(min == null){
      newNode.setLeftSibling(newNode);
      newNode.setRightSibling(newNode);
      min = newNode;
    }
    else{
      insertIntoRootList(nodeList.get(size - 1));
    }
  }

  /**
   * Inserts a node into the root list or root chain of the F-heap. Updates the
   * min node.
   * 
   * @param nodeToInsert
   *          node to insert
   */
  private void insertIntoRootList(FHeapNode nodeToInsert){
    spliceNodeToRight(min, nodeToInsert);
    nodeToInsert.setParent(null);
    nodeToInsert.setMarked(false);

    if(nodeToInsert.getCost() < min.getCost())
      min = nodeToInsert;
  }

  /**
   * Splices and connects a new node to an existing node.
   * <p>
   * Node1 <-> Node3 becomes Node1 <-> Node2 <-> Node3
   * </p>
   * 
   * @param existingNode
   *          existing node
   * @param newNode
   *          new node to connect
   */
  private void spliceNodeToRight(FHeapNode existingNode, FHeapNode newNode){
    newNode.setRightSibling(existingNode.getRightSibling());
    newNode.setLeftSibling(existingNode);
    existingNode.getRightSibling().setLeftSibling(newNode);
    existingNode.setRightSibling(newNode);
  }

  /**
   * Deletes or extracts the minimum node from the heap.
   * 
   * @return The minimum node in the heap when the method was invoked
   */
  public FHeapNode deleteMin(){
    if(size == 0)
      throw new RuntimeException("Error: No elements in heap");

    FHeapNode nodeToDelete = nodeList.get(min.getIndex());

    // insert the children of the node we're about to delete into the root chain
    insertChildrenOfNodeIntoRootList(nodeToDelete);

    // connect up the two neighbors of the node we're about to delete
    joinNeighbors(nodeToDelete);

    nodeToDelete.setAlreadyInMST(true); // set the corresponding nodeList object to null to indicate it's already in the MST (this will be used to decide
                                        // whether to ignore this node in decreaseKey
    size--;

    updateMin(nodeToDelete.getRightSibling());

    nonRecursiveBinomialize(min);

    return nodeToDelete;
  }

  private void insertChildrenOfNodeIntoRootList(FHeapNode node){
    if(node.getChild() != null){
      FHeapNode startNode = node.getChild();
      FHeapNode currentNode = startNode.getRightSibling();
      insertIntoRootList(startNode);

      while(currentNode != startNode){
        FHeapNode tempNode = currentNode.getRightSibling();
        insertIntoRootList(currentNode);
        currentNode = tempNode;
      }
    }
  }

  private void updateMin(FHeapNode startNode){
    min = startNode;
    FHeapNode currentNode = startNode.getRightSibling();
    while(currentNode != startNode){
      if(!currentNode.isAlreadyInMST() && currentNode.getCost() < min.getCost()){
        min = currentNode;
      }
      currentNode = currentNode.getRightSibling();
    }
  }

  /**
   * Same as {@link FHeap#binomialize(FHeapNode)}, only non-recursive.
   * Introduced because the recursive version craps out (stack overflow) for
   * large graphs (n approx 3000)
   * 
   * @param startNode
   *          Node in the root list to start the merge at
   */
  private void nonRecursiveBinomialize(FHeapNode startNode){
    boolean DONE = false;
    FHeapNode currentNode = startNode;
    nodeDegrees.clear();

    while(!DONE){
      while(true){
        int degree = currentNode.getDegree();
        if(nodeDegrees.containsKey(degree)){
          FHeapNode existingNodeOfSameDegree = nodeDegrees.get(degree);
          FHeapNode winningNode = union(currentNode, existingNodeOfSameDegree);
          currentNode = winningNode;
          startNode = winningNode;
          if(winningNode.getCost() <= min.getCost())
            min = winningNode;
          nodeDegrees.clear();
          break;
        }
        else{
          nodeDegrees.put(degree, currentNode);
          currentNode = currentNode.getRightSibling();
          if(currentNode == startNode){
            DONE = true;
            break;
          }
        }
      }
      //if(currentNode.getCost() <= min.getCost())
      //  min = currentNode;
    }
  }

  /**
   * Merges nodes in the root list that have the same degree (similar to a
   * binomial heap, hence the method name)
   * <p>
   * Will update the min node if required.
   * </p>
   * 
   * @param startNode
   *          Node in the root list to start the merge at
   */
  private void binomialize(FHeapNode startNode){
    nodeDegrees.clear();
    binomialize(startNode, startNode);
  }

  private void binomialize(FHeapNode startNode, FHeapNode currentNode){
    nodeDegrees.put(currentNode.getDegree(), currentNode);

    while(currentNode.getRightSibling() != startNode){
      FHeapNode node = currentNode.getRightSibling();
      int degree = node.getDegree();

      if(nodeDegrees.containsKey(degree)){
        FHeapNode existingNodeOfSameDegree = nodeDegrees.get(degree);
        FHeapNode winningNode = union(node, existingNodeOfSameDegree);
        if(winningNode.getCost() <= min.getCost())
          min = winningNode;
        binomialize(winningNode); // start afresh
        return;
      }
      else{
        binomialize(startNode, node); // retain degrees hashmap
        return;
      }
    }
  }

  /**
   * Takes two nodes and makes the lesser node a child of the other. Takes care
   * to update child/parent/sibling pointers as required.
   * 
   * @param node1
   * @param node2
   * @return
   */
  private FHeapNode union(FHeapNode node1, FHeapNode node2){
    if(node1.getCost() <= node2.getCost()){
      node2.setParent(node1);

      // connect up the neighbors of the node that we're about to make a child. this way the root chain stays connected
      // without passing through the node that we just demoted
      joinNeighbors(node2);

      // connect up the child nodes
      if(node1.getDegree() == 0){ // node1 has no preexisting children. this will be its first child. x <-> x
        node1.setChild(node2);
        node2.setLeftSibling(node2);
        node2.setRightSibling(node2);
      }
      else
        spliceNodeToRight(node1.getChild(), node2);

      node1.setDegree(node1.getDegree() + 1); // increment degree

      return node1;
    }
    else
      return union(node2, node1); // node2 was < node1; try again
  }

  /**
   * Connects up the neighbors of a node to each other.
   * <p>
   * A <-> node <-> B becomes A <-> B
   * </p>
   * <p>
   * The sibling/child/parent pointers of the node passed in will <b>NOT</b> be
   * changed.
   * </p>
   * 
   * @param node
   */
  private void joinNeighbors(FHeapNode node){
    node.getLeftSibling().setRightSibling(node.getRightSibling());
    node.getRightSibling().setLeftSibling(node.getLeftSibling());
  }

  /**
   * Decreases the key of the specified node to the specified value. Does
   * nothing if the specified node were already in the MST being built.
   * 
   * @param node
   *          node to decrease the key of
   * @param newKey
   *          new key value
   * @param predecessor
   *          the node from which this key or cost applies
   */
  public void decreaseKey(FHeapNode node, int newKey, int predecessor){
    if(node.isAlreadyInMST())
      return;

    if(newKey >= node.getCost())
      return;

    node.setCost(newKey);
    node.setPredecessor(predecessor);

    cut(node);
  }

  /**
   * Performs the F-heap cut operation. Will call cut recursively (cascade-cut)
   * if required.
   * 
   * @param node
   */
  private void cut(FHeapNode node){
    FHeapNode parent = node.getParent(); // save this beforehand because the reference to the parent will be nulled out in the insertIntoRootList method
    if(null == parent){ // stop if we're at the root chain or if we ascended to it
      if(node.getCost() < min.getCost())
        min = node;
      return;
    }
    else if(node.getCost() < parent.getCost()){
      parent.setDegree(parent.getDegree() - 1); // decrement degree

      if(node.getRightSibling() == node)
        parent.setChild(null);
      else{
        if(parent.getChild() == node){
          parent.setChild(node.getRightSibling());
        }
        joinNeighbors(node);
      }

      insertIntoRootList(node);

      if(!parent.isMarked())
        parent.setMarked(true);
      else
        cut(parent);
    }
  }

  public FHeapNode getMin(){
    return min;
  }

  public int getSize(){
    return size;
  }

  public List<FHeapNode> getNodeList(){
    return nodeList;
  }

}