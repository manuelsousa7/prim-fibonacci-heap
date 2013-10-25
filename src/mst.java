import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;
import java.util.concurrent.TimeUnit;

import ads.neeraj2608.mst.common.GraphGenerator;
import ads.neeraj2608.mst.common.MSTGenerator;
import ads.neeraj2608.mst.fheapscheme.FHeapSchemeMSTGenerator;
import ads.neeraj2608.mst.simplescheme.SimpleSchemeMSTGenerator;
import ads.neeraj2608.types.common.AdjListNode;
import ads.neeraj2608.types.common.Edge;
import ads.neeraj2608.types.common.Graph;

/*
 * Facade for the actual code. Processes the command being given on the input line.
 */
public class mst{
  public static void main(String[] args){
    BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    while(true){
      System.out.println("Please enter a command ('help' provides help):");
      System.out.print(">> ");
      String cmd;
      try{
        cmd = br.readLine();
        switch(cmd.toLowerCase()){
          case "exit":
            exit();
            break;
          case "help":
            printHelp();
            break;
          default:
            processCommandFurther(cmd);
        }
      }catch(IOException e){
        e.printStackTrace();
      }
    }
  }

  private static void processCommandFurther(String cmd){
    if(cmd.startsWith("mst -r")){ // random mode
      String[] params = cmd.split("\\s+");
      if(params.length < 4){
        System.out.println("ERROR: Not enough params given. Syntax is 'mst -r n d'. Please try again.\n");
      } else{
        int numVertices = Integer.parseInt(params[2]);
        double density = Double.parseDouble(params[3])/100;
        
        if(density < 0.01){
          System.out.println("Please specify a density in percentage, e.g. for a graph with density of 50%, please enter 50\n");
        } else{
          processRandomMode(numVertices, density);
        }
      }
    } else if(cmd.startsWith("mst -s")){ // file input mode, simple heap scheme
      Graph graph = createGraphFromFile(cmd);
      List<Edge> generatedMST = MSTGenerator.generateMST(new SimpleSchemeMSTGenerator(), graph);
      printTimeAndCost(MSTGenerator.getRuntime(), generatedMST);
      printEdges(generatedMST);
    } else if(cmd.startsWith("mst -f")){ // file input mode, f-heap scheme
      Graph graph = createGraphFromFile(cmd);
      List<Edge> generatedMST = MSTGenerator.generateMST(new FHeapSchemeMSTGenerator(), graph);
      printTimeAndCost(MSTGenerator.getRuntime(), generatedMST);
      printEdges(generatedMST);
    } else if("runexperiment".equals(cmd)){
      runRandomizedExperiment();
    } else{
      System.out.println("Unrecognized command; please try again.\n");
    }
  }

  private static void processRandomMode(int numVertices, double density){
    Graph graph = GraphGenerator.generateGraph(numVertices, density); 
    System.out.println("Graph size = "+numVertices+", density = "+density);
    System.out.println();
    
    System.out.println("Running simple scheme");
    System.out.println("---------------------");
    List<Edge> generatedMST = MSTGenerator.generateMST(new SimpleSchemeMSTGenerator(), graph);
    printTimeAndCost(MSTGenerator.getRuntime(), generatedMST);
    
    System.out.println("Running f-heap scheme");
    System.out.println("---------------------");
    generatedMST = MSTGenerator.generateMST(new FHeapSchemeMSTGenerator(), graph);
    printTimeAndCost(MSTGenerator.getRuntime(), generatedMST);
  }

  private static void printTimeAndCost(long runtime, List<Edge> generatedMST){
    System.out.format("Time taken:\t\t%d min, %d sec (%d millisec)\n", 
        TimeUnit.MILLISECONDS.toMinutes(runtime),
        TimeUnit.MILLISECONDS.toSeconds(runtime) - 
        TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(runtime)),
        runtime
    );
    printTotalCostOfMST(generatedMST);
    System.out.println();
  }

  private static void printTotalCostOfMST(List<Edge> generatedMST){
    int totalCost = 0;
    for(Edge MSTEdge: generatedMST){
      totalCost += MSTEdge.getCost();
    }
    System.out.println("Total cost of MST:\t"+totalCost);
  }

  private static void printEdges(List<Edge> generatedMST){
    System.out.println("Edges in MST:");
    for(Edge MSTEdge: generatedMST){
      System.out.println(MSTEdge.getStart()+"\t"+MSTEdge.getFinish()+"\t"+MSTEdge.getCost());
    }
    System.out.println();
  }

  private static Graph createGraphFromFile(String cmd){
    String[] params = cmd.split("\\s+");
    String fileName = params[2];
    BufferedReader br;
    Graph graph = null;
    
    try{
      br = new BufferedReader(new FileReader(fileName));
      String line = br.readLine();
      String[] values = line.split("\\s+");
      int numVertices = Integer.parseInt(values[0]);
      int numEdges = Integer.parseInt(values[1]);
      System.out.println("Graph size = "+numVertices+", number of edges = "+numEdges);
      graph = new Graph(numVertices, 0); //density is ignored since we're going to manually add the edges
      while ((line = br.readLine()) != null) {
        values = line.split("\\s+");
        int startNode = Integer.parseInt(values[0]);
        int endNode = Integer.parseInt(values[1]);
        int cost = Integer.parseInt(values[2]);
        graph.getAdjList().get(startNode).add(new AdjListNode(endNode, new Edge(startNode, endNode, cost, false)));
        graph.getAdjList().get(endNode).add(new AdjListNode(startNode, new Edge(endNode, startNode, cost, false)));
      }
      br.close();
    } catch(FileNotFoundException e){
      System.out.format("ERROR: File %s not found.\n", fileName);
    } catch(IOException e){
      System.out.format("ERROR: IO error reading from file %s.\n", fileName);
    }
    
    return graph;
  }
  
  public static void runRandomizedExperiment(){
    final int NUMRUNS  = 5;
    final int[] numVerticesArray = {1000, 3000, 5000};
    final int[] densityArray = {10, 20, 30, 40, 50, 60, 70, 80, 90, 100};
    for(int numVertices: numVerticesArray){
      for(int density: densityArray){
        long simpleSchemeRuntime = 0;
        long fHeapSchemeRuntime = 0;
        for(int run = 0; run < NUMRUNS; run++){
          Graph graph = GraphGenerator.generateGraph(numVertices, density/100.0); 
          
          MSTGenerator.generateMST(new SimpleSchemeMSTGenerator(), graph);
          simpleSchemeRuntime += MSTGenerator.getRuntime();
          
          MSTGenerator.generateMST(new FHeapSchemeMSTGenerator(), graph);
          fHeapSchemeRuntime += MSTGenerator.getRuntime();
        }
        simpleSchemeRuntime = simpleSchemeRuntime / NUMRUNS;
        fHeapSchemeRuntime = fHeapSchemeRuntime / NUMRUNS;
        System.out.format("%d, %d, %d, %d\n",
                          numVertices,
                          density,
                          simpleSchemeRuntime,
                          fHeapSchemeRuntime);
      }
    }
  }

  private static void exit(){
    System.exit(0);
  }

  private static void printHelp(){
    System.out.println("Available commands:");
    System.out.println("'help'           \t: Prints this message");
    System.out.println("'mst -r n d'     \t: Runs Prim's algorithm using both simple and f-heap schemes on a graph of size n and density d specified in %");
    System.out.println("                 \t  Prints time taken by both schemes");
    System.out.println("'mst -s filename'\t: Runs Prim's algorithm using the simple scheme on a graph initialized from file 'filename'");
    System.out.println("'mst -f filename'\t: Runs Prim's algorithm using the f-heap scheme on a graph initialized from file 'filename'");
    System.out.println("'runexperiment'  \t: Runs the randomized mode experiment outlined in the project description");
    System.out.println("'exit'           \t: Quits this program");
    System.out.println();
  }
}
