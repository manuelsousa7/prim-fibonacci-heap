package ads.neeraj2608.types.fheapscheme;

/**
 * This class models a Fibonacci heap (F-heap) node.
 */
public class FHeapNode{
  
  private int cost; // the key or cost of the node
  
  private FHeapNode leftSibling; // references the node to the left
  
  private FHeapNode rightSibling; // references the node to the right
  
  private FHeapNode parent; // references the parent node
  
  private FHeapNode child; // references the child node
  
  private boolean marked; // flag used for the cut (and cascade-cut) operations
  
  private int degree; // records the number of children of this node. Used when merging root list nodes of the same degree
  
  private int index; // identifies the node uniquely
  
  private int predecessor; // stores the index of the node that we arrived at this node from
  
  private boolean alreadyInMST; // flag that indicates this node is already in the MST being built
  
  public FHeapNode(int index, int cost, int degree, int predecessor, FHeapNode leftSibling, FHeapNode rightSibling, FHeapNode parent,
      FHeapNode child, boolean alreadyInMST){
    super();
    this.index = index;
    this.cost = cost;
    this.degree = degree;
    this.predecessor = predecessor;
    this.leftSibling = leftSibling;
    this.rightSibling = rightSibling;
    this.parent = parent;
    this.child = child;
    this.alreadyInMST = alreadyInMST;
  }
  
  public int getCost(){
    return cost;
  }
  
  public void setCost(int cost){
    this.cost = cost;
  }
  
  public FHeapNode getLeftSibling(){
    return leftSibling;
  }
  
  public void setLeftSibling(FHeapNode leftSibling){
    this.leftSibling = leftSibling;
  }
  
  public FHeapNode getRightSibling(){
    return rightSibling;
  }
  
  public void setRightSibling(FHeapNode rightSibling){
    this.rightSibling = rightSibling;
  }
  
  public FHeapNode getParent(){
    return parent;
  }
  
  public void setParent(FHeapNode parent){
    this.parent = parent;
  }
  
  public FHeapNode getChild(){
    return child;
  }
  
  public void setChild(FHeapNode child){
    this.child = child;
  }

  public boolean isMarked(){
    return marked;
  }

  public void setMarked(boolean marked){
    this.marked = marked;
  }

  public int getDegree(){
    return degree;
  }

  public void setDegree(int degree){
    this.degree = degree;
  }

  public int getIndex(){
    return index;
  }

  public void setIndex(int index){
    this.index = index;
  }

  public int getPredecessor(){
    return predecessor;
  }

  public void setPredecessor(int predecessor){
    this.predecessor = predecessor;
  }

  public boolean isAlreadyInMST(){
    return alreadyInMST;
  }

  public void setAlreadyInMST(boolean alreadyInMST){
    this.alreadyInMST = alreadyInMST;
  }
  
}
