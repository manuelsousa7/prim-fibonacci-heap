package ads.neeraj2608.mst.fheapscheme;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import ads.neeraj2608.mst.common.MSTGeneratorInterface;
import ads.neeraj2608.types.common.Edge;
import ads.neeraj2608.types.common.Graph;
import ads.neeraj2608.types.fheapscheme.FHeap;
import ads.neeraj2608.types.fheapscheme.FHeapNode;

/**
 * Class that generates the minimum spanning tree using a Fibonacci
 * heap (F-heap) based implementation of Prim's shortest path algorithm
 */
public class FHeapSchemeMSTGenerator implements MSTGeneratorInterface{
  
  FHeap fHeap;

  @Override
  public List<Edge> generateMST(Graph graph){
    fHeap = new FHeap();
    List<Edge> generatedMST = new ArrayList<Edge>();
    
    int startNodeIndex = graph.selectARandomNode();
    
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
