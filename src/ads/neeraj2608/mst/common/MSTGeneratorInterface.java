package ads.neeraj2608.mst.common;

import java.util.List;

import ads.neeraj2608.types.common.Edge;
import ads.neeraj2608.types.common.Graph;

public interface MSTGeneratorInterface{
  public List<Edge> generateMST(Graph graph);
}
