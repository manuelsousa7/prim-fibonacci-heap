package ads.neeraj2608.types;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

public class Graph{
  
  int numVertices;
  
  int numEdges;
  
  List<HashMap<Integer, Edge>> adjList;
  
  public Graph(int numVertices, double density){
    this.numVertices = numVertices;
    this.numEdges = (int) (density * numVertices * (numVertices - 1) / 2);
    
    adjList = new LinkedList<HashMap<Integer, Edge>>();
    for(int i=0;i<numVertices;i++){
      adjList.add(i, new HashMap<Integer, Edge>());
    }
  }
  
  //find first connected node to start dfs at
  public int selectAConnectedNode(){
    int startDFSAt;
    
    for(startDFSAt=0;startDFSAt<numVertices;startDFSAt++){
      if(!adjList.get(startDFSAt).isEmpty()) break;
    }
    
    return startDFSAt;
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
  
}
