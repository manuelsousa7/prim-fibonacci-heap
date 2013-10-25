package ads.neeraj2608.types.common;

/**
 * Models a node for the graph adjacency list
 */
public class AdjListNode{

  int targetNode;
  
  Edge edge;
  
  public AdjListNode(int targetNode, Edge edge){
    super();
    this.targetNode = targetNode;
    this.edge = edge;
  }
  
  @Override
  public int hashCode(){
    final int prime = 31;
    int result = 1;
    result = prime * result + targetNode;
    return result;
  }

  @Override
  public boolean equals(Object obj){
    if(this == obj)
      return true;
    if(obj == null)
      return false;
    if(!(obj instanceof AdjListNode))
      return false;
    AdjListNode other = (AdjListNode)obj;
    if(targetNode != other.targetNode)
      return false;
    return true;
  }
  
  public int getTargetNode(){
    return targetNode;
  }

  public void setTargetNode(int targetNode){
    this.targetNode = targetNode;
  }

  public Edge getEdge(){
    return edge;
  }

  public void setEdge(Edge edge){
    this.edge = edge;
  }
}
