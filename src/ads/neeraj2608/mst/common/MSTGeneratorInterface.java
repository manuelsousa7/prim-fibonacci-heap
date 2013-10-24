package ads.neeraj2608.mst.common;

import java.util.List;

import ads.neeraj2608.types.common.Edge;
import ads.neeraj2608.types.common.Graph;

public interface MSTGeneratorInterface{
  /**
   * Creates the minimum spanning tree (MST) for a graph
   * 
   * @param graph graph of which to build the MST
   * @return the MST for the graph
   */
  public List<Edge> generateMST(Graph graph);
}
