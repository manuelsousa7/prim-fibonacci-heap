package ads.neeraj2608.mst.simplescheme;

import java.util.ArrayList;
import java.util.List;

import ads.neeraj2608.mst.common.MSTGeneratorInterface;
import ads.neeraj2608.types.common.AdjListNode;
import ads.neeraj2608.types.common.Edge;
import ads.neeraj2608.types.common.Graph;

/**
 * Class that generates the minimum spanning tree using an array-based
 * implementation of Prim's shortest path algorithm
 */
public class SimpleSchemeMSTGenerator implements MSTGeneratorInterface{

  @Override
  public List<Edge> generateMST(Graph graph){
    List<Integer> nodesInMST = new ArrayList<Integer>();
    int startMSTAt = graph.selectARandomNode();
    nodesInMST.add(startMSTAt);

    Edge[] feelerEdges = new Edge[graph.getNumVertices()];
    for(int i = 0; i < feelerEdges.length; i++){
      feelerEdges[i] = new Edge(0, 0, Integer.MAX_VALUE, false);
    }
    feelerEdges[startMSTAt] = new Edge(startMSTAt, startMSTAt, 0, true);

    List<Edge> finalMSTEdges = new ArrayList<Edge>();

    buildMSTNodeByNode(graph, startMSTAt, nodesInMST, finalMSTEdges, feelerEdges);

    return finalMSTEdges;
  }

  /**
   * Recursive method to build the MST. At every step of the algorithm, we
   * record the outgoing edges from <i>all</i> of the vertices put into the MST
   * so far to any vertices <i>not</i> already in the MST. These edges are
   * recorded in an array. If we have already recorded an edge going to the
   * target vertex, we only overwrite it with a newly discovered edge if the
   * latter has a lower cost than the existing edge.
   * 
   * @param graph Graph object to build the MST of
   * @param startMSTAt node to start the MST at
   * @param nodesInMST nodes put into the MST at any given instant
   * @param finalMSTEdges the edges in the built MST
   * @param feelerEdges list of edges going from the set of vertices in the MST
   *                    to the set of vertices not in the MST
   */
  private void buildMSTNodeByNode(Graph graph,
      int startMSTAt,
      List<Integer> nodesInMST,
      List<Edge> finalMSTEdges,
      Edge[] feelerEdges){
    //add the outgoing edges from this node to all the outgoing edges we already have
    for(AdjListNode node: graph.getAdjList().get(startMSTAt)){
      Edge feelerEdge = node.getEdge();
      if(!nodesInMST.contains(feelerEdge.getFinish())){
        if(feelerEdge.getCost() < feelerEdges[feelerEdge.getFinish()].getCost()){
          feelerEdges[feelerEdge.getFinish()] = feelerEdge;
        }
      }
    }

    if(nodesInMST.size() == graph.getNumVertices())
      return;

    //O(n) traversal through the feeler edges to pick the least one
    int minCost = Integer.MAX_VALUE;
    int minEdgeIndex = -1;
    Edge minEdge = null;
    for(int i = 0; i < feelerEdges.length; i++){
      if(!feelerEdges[i].isAlreadyInMST() && feelerEdges[i].getCost() < minCost){
        minCost = feelerEdges[i].getCost();
        minEdge = feelerEdges[i];
        minEdgeIndex = i;
      }
    }

    finalMSTEdges.add(minEdge);
    nodesInMST.add(minEdge.getFinish());
    feelerEdges[minEdgeIndex].setAlreadyInMST(true);

    buildMSTNodeByNode(graph, minEdge.getFinish(), nodesInMST, finalMSTEdges, feelerEdges);
  }

}
