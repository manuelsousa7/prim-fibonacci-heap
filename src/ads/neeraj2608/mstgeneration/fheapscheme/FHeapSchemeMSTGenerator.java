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
 *
 */
public class FHeapSchemeMSTGenerator implements MSTGeneratorInterface{
  
  FHeap fHeap;

  @Override
  public List<Edge> generateMST(Graph graph){
    fHeap = new FHeap();
    List<Edge> generatedMST = new ArrayList<Edge>();
    
    int startNodeIndex = graph.selectAConnectedNode();
    
    for(int i=0;i<graph.getNumVertices();i++){
//      if(i != startNodeIndex)
        fHeap.insert(Integer.MAX_VALUE);
      /*else
        fHeap.insert(0);*/
    }
    
    fHeap.decreaseKey(fHeap.getNodeList().get(startNodeIndex), 0, startNodeIndex);
    
    while(fHeap.getSize()!=0){
      // the order of decreaseKey and deleteMin is CRUCIAL. deleteMin must be called AFTER decreaseKey has been
      // executed for that node. In decreaseKey, we take care not to change the min since it has not yet
      // been deleted by calling deleteMin.
      startNodeIndex = fHeap.getMin().getIndex();
      HashMap <Integer, Edge> adjEdges = graph.getAdjList().get(startNodeIndex);
      for(Integer nodeIndex: adjEdges.keySet()){
        fHeap.decreaseKey(fHeap.getNodeList().get(nodeIndex), adjEdges.get(nodeIndex).getCost(), startNodeIndex);
      }
      
      FHeapNode newestMSTNode = fHeap.deleteMin();
      generatedMST.add(new Edge(newestMSTNode.getFromNode(), newestMSTNode.getIndex(), newestMSTNode.getCost(), false));
    }
    
    generatedMST.remove(0);
    
    return generatedMST;
  }

}
