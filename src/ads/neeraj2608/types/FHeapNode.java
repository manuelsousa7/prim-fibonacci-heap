package ads.neeraj2608.types;

public class FHeapNode{
  
  private int cost;
  
  private FHeapNode leftSibling;
  
  private FHeapNode rightSibling;
  
  private FHeapNode parent;
  
  private FHeapNode child;
  
  private boolean marked;
  
  private int degree;
  
  private int index;
  
  private int predecessor;
  
  private boolean alreadyInMST;
  
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
