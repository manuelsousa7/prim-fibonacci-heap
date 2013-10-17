package ads.neeraj2608.mstgeneration.iface;

import java.util.List;

import ads.neeraj2608.types.Edge;
import ads.neeraj2608.types.Graph;

public interface MSTGeneratorInterface{
  public List<Edge> generateMST(Graph graph);
}
