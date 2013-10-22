package ads.neeraj2608.types;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class FHeap{
  
  private FHeapNode min;
  
  private List<FHeapNode> nodeList; // the index of this heap node corresponds to the index of the node in the graph adjList. Used for easy access but not important
                                    // for any of the F-Heap operations in itself.
  
  private int size;
  
  HashMap<Integer, FHeapNode> nodeDegrees;
  
  public FHeap(){
    size = 0;
    nodeList = new ArrayList<FHeapNode>();
    nodeDegrees = new HashMap<Integer, FHeapNode>();
  }
  
  public void insert(int key){
    nodeList.add(new FHeapNode(size, key, 0, 0, null, null, null, null));
    size++;
    insertIntoRootList(nodeList.get(size-1));
  }
  
  private void insertIntoRootList(FHeapNode fNode){
    insertIntoRootListWithoutUpdatingMin(fNode);
    
    if(fNode.getCost() < min.getCost())
      min = fNode;
  }

  public void insertIntoRootListWithoutUpdatingMin(FHeapNode fNode){
    if(min == null){
      fNode.setLeftSibling(fNode);
      fNode.setRightSibling(fNode);
      min = fNode;
      return;
    }
    
    spliceAndAddNodeToRight(min, fNode);
    fNode.setParent(null);
    fNode.setMarked(false);
  }

  private void spliceAndAddNodeToRight(FHeapNode existingNode, FHeapNode newNode){
    newNode.setRightSibling(existingNode.getRightSibling());
    newNode.setLeftSibling(existingNode);
    existingNode.getRightSibling().setLeftSibling(newNode);
    existingNode.setRightSibling(newNode);
  }

  public FHeapNode deleteMin(){
    if(size == 0)
      throw new RuntimeException("Error: No elements in heap");
    
    if(size == 1){
      size--;
      return min;
    }
    
    // insert the children of the node we're about to delete into the root chain. we do not touch the min
    // while doing this! min will be updated after the heap has been binomialized
    insertChildrenOfNodeIntoRootList(min);
    
    // connect up min's two neighbors so min is out of the root chain
    joinNeighbors(min);
    
    FHeapNode nodeToDelete = new FHeapNode(min.getIndex(), min.getCost(), 0, min.getFromNode(), null, null, null, null);
    size--;
    nodeList.set(min.getIndex(), null); // set the corresponding nodeList object to null to indicate it's already in the MST (this will be used to decide
                                        // whether to ignore this node in decreaseKey
    
    updateMin(min.getRightSibling());
    
    binomialize(min);
    
    return nodeToDelete;
  }

  private void updateMin(FHeapNode startNode){
    min = startNode;
    updateMin(startNode, startNode);
  }

  private void updateMin(FHeapNode startNode, FHeapNode currentNode){
    if(currentNode.getCost() < min.getCost())
      min = currentNode;
    
    if(currentNode.getRightSibling() != startNode)
      updateMin(startNode, currentNode.getRightSibling());
  }

  private void insertChildrenOfNodeIntoRootList(FHeapNode node){
    if(node.getChild() != null){
      insertAllChildrenIntoRootList(node.getChild());
    }
  }

  private void insertAllChildrenIntoRootList(FHeapNode node){
    insertAllChildrenIntoRootList(node, node);
  }

  private void insertAllChildrenIntoRootList(FHeapNode startNode, FHeapNode sibling){
    if(startNode != sibling.getRightSibling()){
      FHeapNode nextNode = sibling.getRightSibling();
      insertIntoRootListWithoutUpdatingMin(sibling);
      insertAllChildrenIntoRootList(startNode, nextNode);
      return;
    }
    insertIntoRootListWithoutUpdatingMin(sibling);
  }
  
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
        binomialize(winningNode); // start afresh
        return;
      } else {
        binomialize(startNode, node); // retain degrees hashmap
        return;
      }
    }
  }

  private FHeapNode union(FHeapNode node1, FHeapNode node2){
    if(node1.getCost() <= node2.getCost()){
      node2.setParent(node1);
      
      // connect up the neighbors of the node that we're about to make a child. this way the root chain stays connected
      // without passing through the node that we just demoted
      joinNeighbors(node2);
      
      node1.setDegree(node1.getDegree() + 1);
      
      // connect up the child nodes
      if(null == node1.getChild()){ // node1 has no preexisting children. this will be its first child. x <-> x
        node1.setChild(node2);
        node2.setLeftSibling(node2);
        node2.setRightSibling(node2);
      }
      else
        spliceAndAddNodeToRight(node1.getChild(), node2);
      
      return node1;
    }
    
    return union(node2, node1); // node2 was < node1; try again
  }

  public void joinNeighbors(FHeapNode node){
    node.getLeftSibling().setRightSibling(node.getRightSibling());
    node.getRightSibling().setLeftSibling(node.getLeftSibling());
  }
  
  public void decreaseKey(FHeapNode node, int newKey, int fromNode){
    if(node == null) //null indicates this node has already been added to the MST and should be ignored
      return;
    
    if(newKey > node.getCost())
      return;
    
    node.setCost(newKey);
    node.setFromNode(fromNode);
    
    cut(node);
  }

  private void cut(FHeapNode node){
    if(null == node.getParent()){ // stop if we've ascended up to the root chain (or if we started at the root chain)
      return;
    } else if(node.getCost() < node.getParent().getCost()){
      FHeapNode parent = node.getParent(); // save this beforehand because the reference to the parent will be nulled out in the insertIntoRootListWithoutUpdatingMin method
      parent.setDegree(parent.getDegree() - 1);
      
      if(parent.getChild() == node){ // any other children of this parent must not become unreachable from the parent just because we promoted this node to the root chain
        if(node.getRightSibling() != node){
          parent.setChild(node.getRightSibling());
          joinNeighbors(node);
        }
        else // this node had no siblings and hence can be safely promoted and the parent can be rendered childless
          parent.setChild(null);
      } else { // the parent's direct child is a sibling of the node we're about to promote. cut all connections between it and the direct child
        joinNeighbors(node);
      }
      
      insertIntoRootListWithoutUpdatingMin(node); // don't update min because deleteMin has yet to be called
      node.setMarked(false);

      if(parent.isMarked()){
        cut(parent);
        return;
      }
      else
        parent.setMarked(true);
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