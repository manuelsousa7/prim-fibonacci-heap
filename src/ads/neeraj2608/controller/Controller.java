package ads.neeraj2608.controller;

import ads.neeraj2608.graphgeneration.GraphGenerator;
import ads.neeraj2608.types.Graph;

public class Controller{
  
  public final static boolean DEBUG = true;

  public static void main(String[] args){
    Graph graph = GraphGenerator.generateGraph(5, 0.5);
  }

}
