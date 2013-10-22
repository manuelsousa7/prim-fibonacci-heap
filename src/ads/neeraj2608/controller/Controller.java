package ads.neeraj2608.controller;

import java.util.List;

import ads.neeraj2608.graphgeneration.GraphGenerator;
import ads.neeraj2608.mstgeneration.fheapscheme.FHeapSchemeMSTGenerator;
import ads.neeraj2608.mstgeneration.simplescheme.SimpleSchemeMSTGenerator;
import ads.neeraj2608.types.Edge;
import ads.neeraj2608.types.Graph;

public class Controller{
  
  public final static boolean DEBUG = false;

  public static void main(String[] args){
    int numVertices = 50;
    double density = 0.5;
    Graph graph = GraphGenerator.generateGraph(numVertices, density);
    
    printGraphMetrics(graph, density);
    
    List<Edge> finalMST = new SimpleSchemeMSTGenerator().generateMST(graph);
    
    printResults(finalMST);
    
    finalMST = new FHeapSchemeMSTGenerator().generateMST(graph);
    
    printResults(finalMST);
  }

  private static void printGraphMetrics(Graph graph, double density){
    System.out.println("Number of vertices = "+graph.getNumVertices());
    System.out.println("Edge density       = "+density);
    System.out.println("-----------------------------");
  }

  private static void printResults(List<Edge> finalMST){
    int totalCost = 0;
    for(Edge MSTEdge: finalMST){
      totalCost += MSTEdge.getCost();
    }
    
    System.out.println(totalCost);
    System.out.println("-----");
    
    for(Edge MSTEdge: finalMST){
      System.out.println(MSTEdge.getStart()+" "+MSTEdge.getFinish());
    }
    
    System.out.println();
  }

}
