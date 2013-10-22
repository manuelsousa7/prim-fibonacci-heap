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
  
  private int fromNode;
  
  public FHeapNode(int index, int cost, int degree, int fromNode, FHeapNode leftSibling, FHeapNode rightSibling, FHeapNode parent,
      FHeapNode child){
    super();
    this.index = index;
    this.cost = cost;
    this.degree = degree;
    this.fromNode = fromNode;
    this.leftSibling = leftSibling;
    this.rightSibling = rightSibling;
    this.parent = parent;
    this.child = child;
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

  public int getFromNode(){
    return fromNode;
  }

  public void setFromNode(int fromNode){
    this.fromNode = fromNode;
  }
  
}
