package ads.neeraj2608.types.common;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

/**
 * This class represents a graph. Connectivity is stored as an adjacency list.
 */
public class Graph{
  
  int numVertices;
  
  int numEdges;
  
  List<HashMap<Integer, Edge>> adjList; // the adjacency list is a list of hashmaps. the key of the hashmap is the target node and the value is the edge to the target
                                        // node from the index of adjList the hashmap is stored at. E.g.
                                        // adjList[0].put(1, new Edge(0, 1, 100, false)) creates an edge from vertex 0 to vertex 1 with a cost of 100 that is not yet
                                        // in the MST (indicated by the last argument = false)
  
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
    
    adjList = new LinkedList<HashMap<Integer, Edge>>();
    for(int i=0;i<numVertices;i++){
      adjList.add(i, new HashMap<Integer, Edge>());
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

  public List<HashMap<Integer, Edge>> getAdjList(){
    return adjList;
  }

  public int getNumEdges(){
    return numEdges;
  }

  public void setAdjList(List<HashMap<Integer, Edge>> adjList){
    this.adjList = adjList;
  }
  
}
