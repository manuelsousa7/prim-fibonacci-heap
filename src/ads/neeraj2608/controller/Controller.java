package ads.neeraj2608.controller;

import java.util.List;

import ads.neeraj2608.graphgeneration.GraphGenerator;
import ads.neeraj2608.mstgeneration.simplescheme.SimpleSchemeMSTGenerator;
import ads.neeraj2608.types.Edge;
import ads.neeraj2608.types.Graph;

public class Controller{
  
  public final static boolean DEBUG = true;

  public static void main(String[] args){
    Graph graph = GraphGenerator.generateGraph(5, 0.5);
    
    List<Edge> finalMST = new SimpleSchemeMSTGenerator().generateMST(graph);
    
    printResults(finalMST);
  }

  private static void printResults(List<Edge> finalMST){
    int totalCost = 0;
    for(Edge MSTEdge: finalMST){
      totalCost += MSTEdge.getCost();
    }
    
    System.out.println(totalCost);
    
    for(Edge MSTEdge: finalMST){
      System.out.println(MSTEdge.getStart()+" "+MSTEdge.getFinish());
    }
  }

}
