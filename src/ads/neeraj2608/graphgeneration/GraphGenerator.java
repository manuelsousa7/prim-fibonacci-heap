package ads.neeraj2608.graphgeneration;

import java.util.HashSet;

import ads.neeraj2608.types.Edge;
import ads.neeraj2608.types.Graph;

import static ads.neeraj2608.controller.Controller.DEBUG;

public class GraphGenerator{
  
  public static Graph generateGraph(int n, double d){
    Graph graph = null;
    
    while(notConnected(graph = createAndPopulateGraph(n, d)));
    
    return graph;
  }

  private static Graph createAndPopulateGraph(int numVertices, double density){
    Graph graph = new Graph(numVertices, density);
    
    int i = 0;
    
    while(i < graph.getNumEdges()){
      int row = (int)(Math.random() * numVertices);
      int column = (int)(Math.random() * numVertices);
      
      //no self-connections
      while(row == column){
        column = (int)(Math.random() * numVertices);
      }
      
      if(!graph.getAdjList().get(row).containsKey(column)){ //this edge not yet in graph
        int cost = ((int)(Math.random() * 1000))+1;
        graph.getAdjList().get(row).put(column, new Edge(row, column, cost, true));
        if(DEBUG) System.out.println(row+"--->"+column+" : "+cost);
        i++;
      }
    }
    
    return graph;
  }
  
  private static boolean notConnected(Graph graph){
    int startDFSAt = graph.selectAConnectedNode();
    
    HashSet<Integer> visitedNodes = new HashSet<Integer>();
    visitedNodes.add(startDFSAt);
    
    runDFSFromNode(startDFSAt, visitedNodes, graph);
    
    if(visitedNodes.size() == graph.getNumVertices())
      return false;
    
    return true;
  }

  private static void runDFSFromNode(Integer startNode, HashSet<Integer> visitedNodes, Graph graph){
    if(DEBUG) System.out.println(startNode);
    for(Integer i: graph.getAdjList().get(startNode).keySet()){
      if(!visitedNodes.contains(i)){
        visitedNodes.add(i);
        runDFSFromNode(i, visitedNodes, graph);
      }
    }
  }
  
}
