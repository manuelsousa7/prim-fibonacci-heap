package ads.neeraj2608.mst.common;

import java.util.List;

import ads.neeraj2608.mst.fheapscheme.FHeapSchemeMSTGenerator;
import ads.neeraj2608.mst.simplescheme.SimpleSchemeMSTGenerator;
import ads.neeraj2608.types.common.Edge;
import ads.neeraj2608.types.common.Graph;

/**
 * This class only used for development and debugging!!
 */
public class Controller{
  
  public final static boolean DEBUG = false;

  public static void main(String[] args){
    int numVertices = 1000;
    double density = 0.5;
    
    //uncomment the while to check if the f-heap, simple scheme equality breaks down at any time
    /*while(true){*/
    
      Graph graph = GraphGenerator.generateGraph(numVertices, density);

      printGraphMetrics(graph, density);

      List<Edge> finalMST = new SimpleSchemeMSTGenerator().generateMST(graph);

      int total1 = printResults(finalMST);

      finalMST = new FHeapSchemeMSTGenerator().generateMST(graph);

      int total2 = printResults(finalMST);

      /*if(total1 != total2)
        break;
    }*/
    
  }

  private static void printGraphMetrics(Graph graph, double density){
    System.out.println("Number of vertices = "+graph.getNumVertices());
    System.out.println("Edge density       = "+density);
    System.out.println("-----------------------------");
  }

  private static int printResults(List<Edge> finalMST){
    int totalCost = 0;
    for(Edge MSTEdge: finalMST){
      totalCost += MSTEdge.getCost();
    }
    
    System.out.println(totalCost);
    System.out.println("-----");
    
    for(Edge MSTEdge: finalMST){
      System.out.println(MSTEdge.getStart()+"\t"+MSTEdge.getFinish()+"\t"+MSTEdge.getCost());
    }
    
    System.out.println();
    return totalCost;
  }

}
