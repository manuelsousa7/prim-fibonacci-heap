package ads.neeraj2608.mstgeneration.fheapscheme;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import ads.neeraj2608.mstgeneration.iface.MSTGeneratorInterface;
import ads.neeraj2608.types.Edge;
import ads.neeraj2608.types.FHeap;
import ads.neeraj2608.types.FHeapNode;
import ads.neeraj2608.types.Graph;

/**
 * Fibonacci-heap based implementation of Prim's shortest path algorithm
 * @author Raj
 */
public class FHeapSchemeMSTGenerator implements MSTGeneratorInterface{
  
  FHeap fHeap;

  @Override
  public List<Edge> generateMST(Graph graph){
    fHeap = new FHeap();
    List<Edge> generatedMST = new ArrayList<Edge>();
    
    int startNodeIndex = graph.selectAConnectedNode();
    
    for(int i=0;i<graph.getNumVertices();i++){
        fHeap.insert(Integer.MAX_VALUE);
    }
    
    fHeap.decreaseKey(fHeap.getNodeList().get(startNodeIndex), 0, startNodeIndex);
    
    while(fHeap.getSize()!=0){
      FHeapNode newestMSTNode = fHeap.deleteMin();
      startNodeIndex = newestMSTNode.getIndex();
      HashMap <Integer, Edge> adjEdges = graph.getAdjList().get(startNodeIndex);
      for(Integer nodeIndex: adjEdges.keySet()){
        fHeap.decreaseKey(fHeap.getNodeList().get(nodeIndex), adjEdges.get(nodeIndex).getCost(), startNodeIndex);
      }
      
      generatedMST.add(new Edge(newestMSTNode.getPredecessor(), newestMSTNode.getIndex(), newestMSTNode.getCost(), false));
    }
    
    generatedMST.remove(0);
    
    return generatedMST;
  }

}
