package ads.neeraj2608.graphgeneration;

import static ads.neeraj2608.controller.Controller.DEBUG;

import java.util.HashSet;
import ads.neeraj2608.types.Edge;
import ads.neeraj2608.types.Graph;

public class GraphGenerator{
  
  public static Graph generateGraph(int n, double d){
    Graph graph = null;
    
    while(notConnected(graph = createAndPopulateGraph(n, d)));
    
    return graph;
  }

  private static Graph createAndPopulateGraph(int numVertices, double density){
    if(DEBUG)
      return createTestGraph(numVertices);
    
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
        graph.getAdjList().get(row).put(column, new Edge(row, column, cost, false));
        if(DEBUG) System.out.println(row+"--->"+column+" : "+cost);
        i++;
      }
    }
    
    if(DEBUG) System.out.println();
    
    return graph;
  }
  
  private static Graph createTestGraph(int numVertices){
    Graph graph = new Graph(numVertices, 0); //density is ignored since we're going to manually add test data
    
    /*
     * 0 -> 1 788
     * 0 -> 2 974
     * 0 -> 3 271
     * 0 -> 4 177
     * 3 -> 2 126
     */
    /*graph.getAdjList().get(0).put(1, new Edge(0, 1, 788, false));
    graph.getAdjList().get(0).put(2, new Edge(0, 2, 974, false));
    graph.getAdjList().get(0).put(3, new Edge(0, 3, 271, false));
    graph.getAdjList().get(0).put(4, new Edge(0, 4, 177, false));
    graph.getAdjList().get(3).put(2, new Edge(3, 2, 126, false));*/
    
    /*
     * 0 -> 1 669
     * 1 -> 3 575
     * 3 -> 2 1
     * 2 -> 0 993
     * 2 -> 4 979
     */
    /*graph.getAdjList().get(0).put(1, new Edge(0, 1, 669, false));
    graph.getAdjList().get(1).put(3, new Edge(1, 3, 575, false));
    graph.getAdjList().get(3).put(2, new Edge(3, 2, 1, false));
    graph.getAdjList().get(2).put(0, new Edge(2, 0, 993, false));
    graph.getAdjList().get(2).put(4, new Edge(2, 4, 979, false));*/
    
    /*
     * 0--->1 : 510
     * 0--->2 : 621
     * 1--->2 : 369
     * 1--->4 : 643
     * 4--->2 : 107
     * 4--->3 : 535
     */
    /*graph.getAdjList().get(0).put(1, new Edge(0, 1, 510, false));
    graph.getAdjList().get(0).put(2, new Edge(0, 2, 621, false));
    graph.getAdjList().get(1).put(2, new Edge(1, 2, 369, false));
    graph.getAdjList().get(1).put(4, new Edge(1, 4, 643, false));
    graph.getAdjList().get(4).put(2, new Edge(4, 2, 107, false));
    graph.getAdjList().get(4).put(3, new Edge(4, 3, 535, false));*/
    
    /*
     * 1--->4 : 325
     * 3--->0 : 751
     * 3--->2 : 804
     * 4--->0 : 429
     * 4--->3 : 484
     */
    /*graph.getAdjList().get(1).put(4, new Edge(1, 4, 325, false));
    graph.getAdjList().get(3).put(0, new Edge(3, 0, 751, false));
    graph.getAdjList().get(3).put(2, new Edge(3, 2, 804, false));
    graph.getAdjList().get(4).put(0, new Edge(4, 0, 429, false));
    graph.getAdjList().get(4).put(3, new Edge(4, 3, 484, false));*/
    
    /*
     * 0--->1 : 686
     * 0--->2 : 197
     * 1--->2 : 941
     * 1--->4 : 250
     * 2--->0 : 512
     * 2--->3 : 991
     */
    /*graph.getAdjList().get(0).put(1, new Edge(0, 1, 686, false));
    graph.getAdjList().get(0).put(2, new Edge(0, 2, 197, false));
    graph.getAdjList().get(1).put(2, new Edge(1, 2, 941, false));
    graph.getAdjList().get(1).put(4, new Edge(1, 4, 250, false));
    graph.getAdjList().get(2).put(0, new Edge(2, 0, 512, false));
    graph.getAdjList().get(2).put(3, new Edge(2, 3, 991, false));*/
    
    /*
     * 1--->3 : 580
     * 2--->0 : 909
     * 2--->1 : 515
     * 2--->4 : 776
     * 3--->2 : 411
     */
    graph.getAdjList().get(1).put(3, new Edge(1, 3, 580, false));
    graph.getAdjList().get(2).put(0, new Edge(2, 0, 909, false));
    graph.getAdjList().get(2).put(1, new Edge(2, 1, 515, false));
    graph.getAdjList().get(2).put(4, new Edge(2, 4, 776, false));
    graph.getAdjList().get(3).put(2, new Edge(3, 2, 411, false));
    
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
    for(Integer i: graph.getAdjList().get(startNode).keySet()){
      if(!visitedNodes.contains(i)){
        visitedNodes.add(i);
        runDFSFromNode(i, visitedNodes, graph);
      }
    }
  }
  
}
