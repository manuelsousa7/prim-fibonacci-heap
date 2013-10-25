package ads.neeraj2608.mst.common;

import static ads.neeraj2608.mst.common.Controller.DEBUG;

import java.util.HashSet;
import ads.neeraj2608.types.common.AdjListNode;
import ads.neeraj2608.types.common.Edge;
import ads.neeraj2608.types.common.Graph;

/**
 * Class that generates a random, unconnected, undirected graph
 */
public class GraphGenerator{
  
  private static final int SENTINEL = -1; // used to indicate that a given graph node has NO incoming edges as yet
  private static final int MAX_EDGE_COST = 1000; // the maximum possible cost of an edge in the graph

  /**
   * Creates a random undirected connected graph with the specified size and edge density.
   * 
   * @param numVertices
   *          number of vertices in graph
   * @param density
   *          edge density of graph
   * @return the Graph object representing the created graph
   */
  public static Graph generateGraph(int numVertices, double density){
    Graph graph = null;
    
    while(notConnected(graph = createAndPopulateGraph(numVertices, density)));
    
    return graph;
  }

  private static Graph createAndPopulateGraph(int numVertices, double density){
    if(DEBUG)
      return createTestGraph(numVertices);
    
    return uniformSpanningTreeApproach(numVertices, density);
  }

  /**
   * Method to ensure that the generated graph is connected. Runs a depth-first
   * search on the graph to do so.
   * 
   * @param graph
   *          graph object to check
   * @return true if <b>NOT</b> connected, false otherwise. False is good!
   */
  private static boolean notConnected(Graph graph){
    int startDFSAt = graph.selectARandomNode();
    
    HashSet<Integer> visitedNodes = new HashSet<Integer>();
    visitedNodes.add(startDFSAt);
    
    runDFSFromNode(startDFSAt, visitedNodes, graph);
    
    if(visitedNodes.size() == graph.getNumVertices())
      return false;
    
    return true;
  }

  /**
   * Runs depth-first search on the given graph (recursive method).
   * 
   * @param startNode node to start DFS at
   * @param visitedNodes hash set that keeps track of all the nodes visited by DFS so far
   * @param graph graph object to check
   */
  private static void runDFSFromNode(Integer startNode, HashSet<Integer> visitedNodes, Graph graph){
    for(AdjListNode node: graph.getAdjList().get(startNode)){
      if(!visitedNodes.contains(node.getTargetNode())){
        visitedNodes.add(node.getTargetNode());
        runDFSFromNode(node.getTargetNode(), visitedNodes, graph);
      }
    }
  }
  
  /**
   * <p>
   * Use a random walk approach to create a random connected undirected graph.
   * The random walk algorithm is described in <a
   * href="http://stackoverflow.com/a/14618505">this</a> Stackoverflow post.
   * </p>
   * <p>
   * In short, the algorithm works by picking a node at random and then
   * initiating a random walk until all the vertices in the graph have been
   * covered. Since the number of edges when all the nodes have been covered may
   * be more or less than the desired number of edges (numVertices *
   * (numVertices - 1)/2), we may have to <ul><li>add more edges, or</li> <li>to prune existing
   * ones.</li></ul> <p>The first case is trivial.</p> <p>Pruning edges requires more care since
   * there is a chance that we might prune all incoming edges to a node, leaving
   * it isolated (and destroying the connectedness of the graph). To prevent
   * this from happening, we record the first incident edge to every node (i.e,
   * the edge that brings us to that edge the very first time). When pruning
   * nodes, all we need to do is to make sure that we don't delete the first
   * incident edge. This ensures that there is at least one edge that can bring
   * us to that node, preserving the connectedness of the graph.</p>
   * </p>
   * 
   * @param numVertices
   *          number of vertices in graph
   * @param density
   *          edge density of graph
   * @return the Graph object representing the created graph
   */
  private static Graph uniformSpanningTreeApproach(int numVertices, double density){
    Graph graph = new Graph(numVertices, density);
    HashSet<Integer> visitedNodes = new HashSet<Integer>();
    
    int[] firstIncidentEdges = createIncidentEdgeArray(numVertices);
    int startNode = (int) (Math.random() * numVertices);
    int nextNode = startNode;
    int numEdgesAdded = 0;
    visitedNodes.add(startNode);
    firstIncidentEdges[startNode] = startNode;
    
    while(visitedNodes.size() < numVertices){
      while((nextNode = (int) (Math.random() * numVertices)) == startNode); // no self-connections!
      if(!graph.getAdjList().get(startNode).contains(new AdjListNode(nextNode, null))){
        int cost = ((int)(Math.random() * MAX_EDGE_COST))+1;
        graph.getAdjList().get(startNode).add(new AdjListNode(nextNode, new Edge(startNode, nextNode, cost, false))); //undirected graph; symmetrical connections
        graph.getAdjList().get(nextNode).add(new AdjListNode(startNode, new Edge(nextNode, startNode, cost, false))); //undirected graph; symmetrical connections
        visitedNodes.add(nextNode);
        numEdgesAdded = numEdgesAdded + 2;
        if(firstIncidentEdges[nextNode] == SENTINEL){
          firstIncidentEdges[nextNode] = startNode;
        }
        startNode = nextNode;
      }
    }
    
    if(numEdgesAdded < graph.getNumEdges()){ // we don't have enough edges, add more randomly, creating cycles
      while(numEdgesAdded < graph.getNumEdges()){
        startNode = (int) (Math.random() * numVertices);
        while((nextNode = (int) (Math.random() * numVertices)) == startNode); // no self-connections!
        if(!graph.getAdjList().get(startNode).contains(new AdjListNode(nextNode, null))){
          int cost = ((int)(Math.random() * 1000))+1;
          graph.getAdjList().get(startNode).add(new AdjListNode(nextNode, new Edge(startNode, nextNode, cost, false))); //undirected graph; symmetrical connections
          graph.getAdjList().get(nextNode).add(new AdjListNode(startNode, new Edge(nextNode, startNode, cost, false))); //undirected graph; symmetrical connections
          numEdgesAdded = numEdgesAdded + 2;
        }
      }
    } else if (numEdgesAdded > graph.getNumEdges()){ // we have too many edges, prune some. take care not to maroon any existing vertex
      while(numEdgesAdded > graph.getNumEdges()){
        startNode = (int) (Math.random() * numVertices);
        while((nextNode = (int) (Math.random() * numVertices)) == startNode); // no self-connections!
        if(graph.getAdjList().get(startNode).contains(new AdjListNode(nextNode, null))){
          if(firstIncidentEdges[nextNode] != startNode){ // remove the edge only if it's not the first incident edge
            graph.getAdjList().get(startNode).remove(new AdjListNode(nextNode, null));
            graph.getAdjList().get(nextNode).remove(new AdjListNode(startNode, null));
            numEdgesAdded = numEdgesAdded - 2;
          }
        }
      }
    }
    
    return graph;
  }

  private static int[] createIncidentEdgeArray(int numVertices){
    int[] firstIncidentEdges = new int[numVertices]; // record the first incident edge on a newly discovered vertex. comes in handy if we need to prune edges
                                                     // because it helps us avoid marooning vertices
    for(int i=0; i<numVertices; i++){
      firstIncidentEdges[i] = SENTINEL; // sentinel value
    }
    
    return firstIncidentEdges;
  }

  /**
   * Initialize a graph with a particular state. Used for testing the Prim
   * algorithm implementation.
   * 
   * @param numVertices
   *          number of vertices in graph
   * @return the Graph object representing the created graph
   */
  private static Graph createTestGraph(int numVertices){
    Graph graph = new Graph(numVertices, 0); //density is ignored since we're going to manually add test data
    
    /*graph.getAdjList().get(2).put(5, new Edge(2, 5, 36, false));
    graph.getAdjList().get(5).put(2, new Edge(5, 2, 36, false));
    graph.getAdjList().get(1).put(4, new Edge(1, 4, 54, false));
    graph.getAdjList().get(4).put(1, new Edge(4, 1, 54, false));
    graph.getAdjList().get(1).put(5, new Edge(1, 5, 30, false));
    graph.getAdjList().get(5).put(1, new Edge(5, 1, 30, false));
    graph.getAdjList().get(6).put(4, new Edge(6, 4, 85, false));
    graph.getAdjList().get(4).put(6, new Edge(4, 6, 85, false));
    graph.getAdjList().get(2).put(0, new Edge(2, 0, 91, false));
    graph.getAdjList().get(0).put(2, new Edge(0, 2, 91, false));
    graph.getAdjList().get(2).put(6, new Edge(2, 6, 21, false));
    graph.getAdjList().get(6).put(2, new Edge(6, 2, 21, false));
    graph.getAdjList().get(3).put(0, new Edge(3, 0, 46, false));
    graph.getAdjList().get(0).put(3, new Edge(0, 3, 46, false));
    graph.getAdjList().get(3).put(1, new Edge(3, 1, 52, false));
    graph.getAdjList().get(1).put(3, new Edge(1, 3, 52, false));
    graph.getAdjList().get(0).put(1, new Edge(0, 1, 80, false));
    graph.getAdjList().get(1).put(0, new Edge(1, 0, 80, false));
    graph.getAdjList().get(4).put(0, new Edge(4, 0, 34, false));
    graph.getAdjList().get(0).put(4, new Edge(0, 4, 34, false));*/
    
    /*graph.getAdjList().get(4).put(2, new Edge(4, 2, 40, false));
    graph.getAdjList().get(2).put(4, new Edge(2, 4, 40, false));
    graph.getAdjList().get(4).put(0, new Edge(4, 0, 77, false));
    graph.getAdjList().get(0).put(4, new Edge(0, 4, 77, false));
    graph.getAdjList().get(2).put(0, new Edge(2, 0, 30, false));
    graph.getAdjList().get(0).put(2, new Edge(0, 2, 30, false));
    graph.getAdjList().get(3).put(0, new Edge(3, 0, 54, false));
    graph.getAdjList().get(0).put(3, new Edge(0, 3, 54, false));
    graph.getAdjList().get(3).put(1, new Edge(3, 1, 50, false));
    graph.getAdjList().get(1).put(3, new Edge(1, 3, 50, false));*/
    
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
    /*graph.getAdjList().get(1).put(3, new Edge(1, 3, 580, false));
    graph.getAdjList().get(2).put(0, new Edge(2, 0, 909, false));
    graph.getAdjList().get(2).put(1, new Edge(2, 1, 515, false));
    graph.getAdjList().get(2).put(4, new Edge(2, 4, 776, false));
    graph.getAdjList().get(3).put(2, new Edge(3, 2, 411, false));*/
    
    /*graph.getAdjList().get(10).put(8, new Edge(10, 8, 11, false));
    graph.getAdjList().get(8).put(10, new Edge(8, 10, 11, false));
    graph.getAdjList().get(7).put(12, new Edge(7, 12, 38, false));
    graph.getAdjList().get(12).put(7, new Edge(12, 7, 38, false));
    graph.getAdjList().get(10).put(0, new Edge(10, 0, 79, false));
    graph.getAdjList().get(0).put(10, new Edge(0, 10, 79, false));
    graph.getAdjList().get(5).put(0, new Edge(5, 0, 45, false));
    graph.getAdjList().get(0).put(5, new Edge(0, 5, 45, false));
    graph.getAdjList().get(6).put(2, new Edge(6, 2, 79, false));
    graph.getAdjList().get(2).put(6, new Edge(2, 6, 79, false));
    graph.getAdjList().get(14).put(6, new Edge(14, 6, 61, false));
    graph.getAdjList().get(6).put(14, new Edge(6, 14, 61, false));
    graph.getAdjList().get(3).put(12, new Edge(3, 12, 60, false));
    graph.getAdjList().get(12).put(3, new Edge(12, 3, 60, false));
    graph.getAdjList().get(6).put(9, new Edge(6, 9, 97, false));
    graph.getAdjList().get(9).put(6, new Edge(9, 6, 97, false));
    graph.getAdjList().get(7).put(0, new Edge(7, 0, 52, false));
    graph.getAdjList().get(0).put(7, new Edge(0, 7, 52, false));
    graph.getAdjList().get(4).put(6, new Edge(4, 6, 90, false));
    graph.getAdjList().get(6).put(4, new Edge(6, 4, 90, false));
    graph.getAdjList().get(6).put(13, new Edge(6, 13, 91, false));
    graph.getAdjList().get(13).put(6, new Edge(13, 6, 91, false));
    graph.getAdjList().get(5).put(10, new Edge(5, 10, 28, false));
    graph.getAdjList().get(10).put(5, new Edge(10, 5, 28, false));
    graph.getAdjList().get(2).put(13, new Edge(2, 13, 58, false));
    graph.getAdjList().get(13).put(2, new Edge(13, 2, 58, false));
    graph.getAdjList().get(10).put(7, new Edge(10, 7, 32, false));
    graph.getAdjList().get(7).put(10, new Edge(7, 10, 32, false));
    graph.getAdjList().get(11).put(6, new Edge(11, 6, 57, false));
    graph.getAdjList().get(6).put(11, new Edge(6, 11, 57, false));
    graph.getAdjList().get(14).put(11, new Edge(14, 11, 44, false));
    graph.getAdjList().get(11).put(14, new Edge(11, 14, 44, false));
    graph.getAdjList().get(12).put(0, new Edge(12, 0, 97, false));
    graph.getAdjList().get(0).put(12, new Edge(0, 12, 97, false));
    graph.getAdjList().get(12).put(4, new Edge(12, 4, 21, false));
    graph.getAdjList().get(4).put(12, new Edge(4, 12, 21, false));
    graph.getAdjList().get(3).put(6, new Edge(3, 6, 79, false));
    graph.getAdjList().get(6).put(3, new Edge(6, 3, 79, false));
    graph.getAdjList().get(9).put(4, new Edge(9, 4, 69, false));
    graph.getAdjList().get(4).put(9, new Edge(4, 9, 69, false));
    graph.getAdjList().get(0).put(1, new Edge(0, 1, 17, false));
    graph.getAdjList().get(1).put(0, new Edge(1, 0, 17, false));
    graph.getAdjList().get(9).put(2, new Edge(9, 2, 85, false));
    graph.getAdjList().get(2).put(9, new Edge(2, 9, 85, false));
    graph.getAdjList().get(4).put(7, new Edge(4, 7, 76, false));
    graph.getAdjList().get(7).put(4, new Edge(7, 4, 76, false));
    graph.getAdjList().get(9).put(12, new Edge(9, 12, 39, false));
    graph.getAdjList().get(12).put(9, new Edge(12, 9, 39, false));
    graph.getAdjList().get(5).put(1, new Edge(5, 1, 40, false));
    graph.getAdjList().get(1).put(5, new Edge(1, 5, 40, false));
    graph.getAdjList().get(14).put(5, new Edge(14, 5, 21, false));
    graph.getAdjList().get(5).put(14, new Edge(5, 14, 21, false));
    graph.getAdjList().get(14).put(7, new Edge(14, 7, 10, false));
    graph.getAdjList().get(7).put(14, new Edge(7, 14, 10, false));
    graph.getAdjList().get(5).put(4, new Edge(5, 4, 13, false));
    graph.getAdjList().get(4).put(5, new Edge(4, 5, 13, false));
    graph.getAdjList().get(5).put(11, new Edge(5, 11, 59, false));
    graph.getAdjList().get(11).put(5, new Edge(11, 5, 59, false));
    graph.getAdjList().get(6).put(0, new Edge(6, 0, 73, false));
    graph.getAdjList().get(0).put(6, new Edge(0, 6, 73, false));
    graph.getAdjList().get(6).put(1, new Edge(6, 1, 52, false));
    graph.getAdjList().get(1).put(6, new Edge(1, 6, 52, false));
    graph.getAdjList().get(12).put(10, new Edge(12, 10, 15, false));
    graph.getAdjList().get(10).put(12, new Edge(10, 12, 15, false));
    graph.getAdjList().get(1).put(3, new Edge(1, 3, 36, false));
    graph.getAdjList().get(3).put(1, new Edge(3, 1, 36, false));
    graph.getAdjList().get(14).put(9, new Edge(14, 9, 75, false));
    graph.getAdjList().get(9).put(14, new Edge(9, 14, 75, false));
    graph.getAdjList().get(14).put(2, new Edge(14, 2, 73, false));
    graph.getAdjList().get(2).put(14, new Edge(2, 14, 73, false));
    graph.getAdjList().get(1).put(2, new Edge(1, 2, 11, false));
    graph.getAdjList().get(2).put(1, new Edge(2, 1, 11, false));
    graph.getAdjList().get(13).put(4, new Edge(13, 4, 28, false));
    graph.getAdjList().get(4).put(13, new Edge(4, 13, 28, false));
    graph.getAdjList().get(10).put(14, new Edge(10, 14, 97, false));
    graph.getAdjList().get(14).put(10, new Edge(14, 10, 97, false));
    graph.getAdjList().get(11).put(9, new Edge(11, 9, 48, false));
    graph.getAdjList().get(9).put(11, new Edge(9, 11, 48, false));
    graph.getAdjList().get(7).put(11, new Edge(7, 11, 85, false));
    graph.getAdjList().get(11).put(7, new Edge(11, 7, 85, false));
    graph.getAdjList().get(14).put(1, new Edge(14, 1, 69, false));
    graph.getAdjList().get(1).put(14, new Edge(1, 14, 69, false));
    graph.getAdjList().get(3).put(0, new Edge(3, 0, 61, false));
    graph.getAdjList().get(0).put(3, new Edge(0, 3, 61, false));
    graph.getAdjList().get(10).put(3, new Edge(10, 3, 66, false));
    graph.getAdjList().get(3).put(10, new Edge(3, 10, 66, false));
    graph.getAdjList().get(11).put(13, new Edge(11, 13, 76, false));
    graph.getAdjList().get(13).put(11, new Edge(13, 11, 76, false));
    graph.getAdjList().get(8).put(11, new Edge(8, 11, 95, false));
    graph.getAdjList().get(11).put(8, new Edge(11, 8, 95, false));
    graph.getAdjList().get(4).put(11, new Edge(4, 11, 6, false));
    graph.getAdjList().get(11).put(4, new Edge(11, 4, 6, false));
    graph.getAdjList().get(2).put(0, new Edge(2, 0, 65, false));
    graph.getAdjList().get(0).put(2, new Edge(0, 2, 65, false));
    graph.getAdjList().get(4).put(2, new Edge(4, 2, 15, false));
    graph.getAdjList().get(2).put(4, new Edge(2, 4, 15, false));
    graph.getAdjList().get(1).put(7, new Edge(1, 7, 99, false));
    graph.getAdjList().get(7).put(1, new Edge(7, 1, 99, false));
    graph.getAdjList().get(10).put(1, new Edge(10, 1, 91, false));
    graph.getAdjList().get(1).put(10, new Edge(1, 10, 91, false));
    graph.getAdjList().get(12).put(14, new Edge(12, 14, 60, false));
    graph.getAdjList().get(14).put(12, new Edge(14, 12, 60, false));
    graph.getAdjList().get(8).put(4, new Edge(8, 4, 87, false));
    graph.getAdjList().get(4).put(8, new Edge(4, 8, 87, false));*/

    return graph;
  }
}
