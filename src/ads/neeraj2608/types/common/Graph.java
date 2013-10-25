package ads.neeraj2608.types.common;

import java.util.LinkedList;
import java.util.List;

/**
 * This class represents a graph. Connectivity is stored as an adjacency list.
 */
public class Graph{
  
  int numVertices;
  
  int numEdges;
  
  List<List<AdjListNode>> adjList; // the adjacency list is a list of lists of objects of the AdjListNode class. The targetNode field of the object
                                   // is the node that is being connected by the edge field of the same object. E.g.
                                   // adjList[0].put(new AdjListNode(1, new Edge(0, 1, 100, false))) creates an edge from vertex 0 to vertex 1 with a
                                   // cost of 100 that is not yet in the MST (indicated by the last argument to Edge = false)
  
  /**
   * Constructs a graph with the specified size and density
   * 
   * @param numVertices
   *          number of vertices in graph
   * @param density
   *          edge density of graph
   */
  public Graph(int numVertices, double density){
    this.numVertices = numVertices;
    this.numEdges = (int) (density * numVertices * (numVertices - 1) / 2);
    
    adjList = new LinkedList<List<AdjListNode>>();
    for(int i=0;i<numVertices;i++){
      adjList.add(i, new LinkedList<AdjListNode>());
    }
  }
  
  /**
   * Picks a random node in the graph
   * 
   * @return the index of the connected node
   */
  public int selectARandomNode(){
    return (int) (Math.random() * numVertices);
  }

  public int getNumVertices(){
    return numVertices;
  }

  public List<List<AdjListNode>> getAdjList(){
    return adjList;
  }

  public int getNumEdges(){
    return numEdges;
  }

  public void setAdjList(List<List<AdjListNode>> adjList){
    this.adjList = adjList;
  }
  
}
