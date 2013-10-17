package ads.neeraj2608.mstgeneration.simplescheme;

import java.util.ArrayList;
import java.util.HashSet;
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
    int startDFSAt = graph.selectAConnectedNode();
    HashSet<Integer> nodesInMST = new HashSet<Integer>();
    nodesInMST.add(startDFSAt);
    
    List<Edge> feelers = new ArrayList<Edge>();
    List<Edge> finalMSTEdges = new ArrayList<Edge>();
    
    buildMSTNodeByNode(graph, startDFSAt, nodesInMST, finalMSTEdges, feelers);
    
    return finalMSTEdges;
  }

  public void buildMSTNodeByNode(Graph graph, 
                                 int startDFSAt,
                                 HashSet<Integer> nodesInMST,
                                 List<Edge> finalMSTEdges,
                                 List<Edge> feelers){
    //add the outgoing edges from this node to all the outgoing edges we already have
    for(Edge feelerEdge: graph.getAdjList().get(startDFSAt).values()){
      if(!nodesInMST.contains(feelerEdge.getFinish())){
        feelers.add(feelerEdge);
      }
    }
    
    if(feelers.isEmpty())
      return;
    
    //O(n) traversal through the feeler edges to pick the least one
    int minCost = 2000; //larger than any possible cost (since max random(1000)+1 = 1000)
    Edge minEdge = null;
    int minEdgeIndex = -1;
    for(int i=0;i<feelers.size();i++){
      if(null != feelers.get(i) && feelers.get(i).getCost() < minCost){
        minCost = feelers.get(i).getCost();
        minEdge = feelers.get(i);
        minEdgeIndex = i;
      }
    }
    
    finalMSTEdges.add(minEdge);
    nodesInMST.add(minEdge.getFinish());
    feelers.remove(minEdgeIndex);
    
    buildMSTNodeByNode(graph, minEdge.getFinish(), nodesInMST, finalMSTEdges, feelers);
  }

}
