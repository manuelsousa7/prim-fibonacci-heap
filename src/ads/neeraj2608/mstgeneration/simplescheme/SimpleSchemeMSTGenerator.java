package ads.neeraj2608.mstgeneration.simplescheme;

import java.util.ArrayList;
import java.util.List;

import ads.neeraj2608.mstgeneration.iface.MSTGeneratorInterface;
import ads.neeraj2608.types.Edge;
import ads.neeraj2608.types.Graph;

/**
 * Array based (ArrayList) implementation of Prim's shortest path algorithm
 * Complexity is O(n^2)
 * @author Raj
 *
 */
public class SimpleSchemeMSTGenerator implements MSTGeneratorInterface{

  @Override
  public List<Edge> generateMST(Graph graph){
    List<Integer> nodesInMST = new ArrayList<Integer>();
    int startMSTAt = graph.selectAConnectedNode();
    nodesInMST.add(startMSTAt);
    
    Edge[] feelerEdges = new Edge[graph.getNumVertices()];
    for(int i=0;i<feelerEdges.length;i++){
      feelerEdges[i] = new Edge(0, 0, Integer.MAX_VALUE, false);
    }
    feelerEdges[startMSTAt] = new Edge(startMSTAt, startMSTAt, 0, true);
    
    List<Edge> finalMSTEdges = new ArrayList<Edge>();
    
    buildMSTNodeByNode(graph, startMSTAt, nodesInMST, finalMSTEdges, feelerEdges);
    
    return finalMSTEdges;
  }

  public void buildMSTNodeByNode(Graph graph, 
                                 int startMSTAt,
                                 List<Integer> nodesInMST,
                                 List<Edge> finalMSTEdges,
                                 Edge[] feelerEdges){
    //add the outgoing edges from this node to all the outgoing edges we already have
    for(Edge feelerEdge: graph.getAdjList().get(startMSTAt).values()){
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
    for(int i=0;i<feelerEdges.length;i++){
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
