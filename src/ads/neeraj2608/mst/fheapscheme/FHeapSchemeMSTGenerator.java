package ads.neeraj2608.mst.fheapscheme;

import java.util.ArrayList;
import java.util.List;

import ads.neeraj2608.mst.common.MSTGeneratorInterface;
import ads.neeraj2608.types.common.AdjListNode;
import ads.neeraj2608.types.common.Edge;
import ads.neeraj2608.types.common.Graph;
import ads.neeraj2608.types.fheapscheme.FHeap;
import ads.neeraj2608.types.fheapscheme.FHeapNode;

/**
 * Class that generates the minimum spanning tree using a Fibonacci heap
 * (F-heap) based implementation of Prim's shortest path algorithm
 */
public class FHeapSchemeMSTGenerator implements MSTGeneratorInterface{

  FHeap fHeap;

  /*
   * (non-Javadoc) Method that builds the MST using an F-heap and Prim's
   * algorithm. The heap is initialized with the keys of all the nodes of the
   * graph set to Integer.MAX_VALUE (\infinity). We choose a random node of the
   * graph and decrease its key to 0. Then, we decrease the keys of all the
   * vertices adjacent to the chosen vertex to the weights of the edges going to
   * those nodes from the chosen vertex. At every successive step of the
   * algorithm, we delete the minimum node from the heap and repeat the above
   * process. This continues until the heap is empty. Every time a node is
   * deleted, we record the weight of the edge with which it was reached and the
   * vertex that we reached it from. This gives us enough information to record
   * the corresponding edge. At the end of the algorithm (when the heap is
   * empty), the set of all edges gives us the MST for the graph.
   * 
   * @see
   * ads.neeraj2608.mst.common.MSTGeneratorInterface#generateMST(ads.neeraj2608
   * .types.common.Graph)
   */
  @Override
  public List<Edge> generateMST(Graph graph){
    fHeap = new FHeap();
    List<Edge> generatedMST = new ArrayList<Edge>();

    int startNodeIndex = graph.selectARandomNode();

    for(int i = 0; i < graph.getNumVertices(); i++){
      fHeap.insert(Integer.MAX_VALUE);
    }

    fHeap.decreaseKey(fHeap.getNodeList().get(startNodeIndex), 0, startNodeIndex);

    while(fHeap.getSize() != 0){
      FHeapNode newestMSTNode = fHeap.deleteMin();
      startNodeIndex = newestMSTNode.getIndex();
      for(AdjListNode node: graph.getAdjList().get(startNodeIndex)){
        int nodeIndex = node.getTargetNode();
        int cost = node.getEdge().getCost();
        fHeap.decreaseKey(fHeap.getNodeList().get(nodeIndex), cost, startNodeIndex);
      }

      generatedMST.add(new Edge(newestMSTNode.getPredecessor(), newestMSTNode.getIndex(),
          newestMSTNode.getCost(), false));
    }

    generatedMST.remove(0);

    return generatedMST;
  }

}
