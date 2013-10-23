package ads.neeraj2608.types.common;

/**
 * Models a graph edge.
 * Edges have four fields:
 * <ul>
 * <li> <b>start</b> the node that the edge starts at
 * <li> <b>finish</b> the node that the edge ends at
 * <li> <b>cost</b> the cost of the edge (in F-heap terminology, the "key")
 * <li> <b>alreadyInMST</b> a boolean flag that indicates whether this edge is already in the MST
 * </ul>
 */
public class Edge{

  private int start;
  private int finish;
  private int cost;
  private boolean alreadyInMST;
  
  public Edge(int start, int finish, int cost, boolean eligible){
    super();
    this.start = start;
    this.finish = finish;
    this.cost = cost;
    this.alreadyInMST = eligible;
  }
  
  public int getStart(){
    return start;
  }
  
  public void setStart(int start){
    this.start = start;
  }
  
  public int getFinish(){
    return finish;
  }
  
  public void setFinish(int finish){
    this.finish = finish;
  }
  
  public int getCost(){
    return cost;
  }
  
  public void setCost(int cost){
    this.cost = cost;
  }
  
  public boolean isAlreadyInMST(){
    return alreadyInMST;
  }
  
  public void setAlreadyInMST(boolean alreadyInMST){
    this.alreadyInMST = alreadyInMST;
  }
  
}
