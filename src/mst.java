import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import ads.neeraj2608.types.common.Graph;

/*
 * Facade for the actual code. Processes the command being given on the input line.
 */
public class mst{
  public static void main(String[] args){
    System.out.println("hello world");
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

  public static void exit(){
    System.exit(0);
  }

  public static void printHelp(){
    System.out.println("Available commands:");
    System.out.println("'help'           \t:\tPrints this message");
    System.out.println("'mst -r n d'     \t:\tRuns Prim's algorithm using both simple and f-heap schemes on a graph of size n and density d specified in %");
    System.out.println("                 \t \tPrints time taken by both schemes");
    System.out.println("'mst -s filename'\t:\tRuns Prim's algorithm using the simple scheme on a graph initialized from file 'filename'");
    System.out.println("'mst -f filename'\t:\tRuns Prim's algorithm using the f-heap scheme on a graph initialized from file 'filename'");
    System.out.println();
  }

  private static void processCommandFurther(String cmd){
    if(cmd.startsWith("mst -r")){ // random mode
      String[] params = cmd.split("\\s+");
      if(params.length < 4){
        System.out.println("ERROR: Not enough params given. Syntax is 'mst -r n d'. Please try again.\n");
      } else{
        int numVertices = Integer.parseInt(params[2]);
        int density = Integer.parseInt(params[3]);
        
        System.out.println("Graph size = "+numVertices+", density = "+density);
        System.out.println("Running simple scheme");
        System.out.println("Running f-heap scheme");
        System.out.println();
        //measure runtime with simple scheme
        //measure runtime with f-heap scheme
      }
    } else if(cmd.startsWith("mst -s")){ // file input mode, simple heap scheme
      Graph graph = createGraphFromFile(cmd);
    } else if(cmd.startsWith("mst -f")){ // file input mode, f-heap scheme
      Graph graph = createGraphFromFile(cmd);
    }
    else{
      System.out.println("Unrecognized command; please try again.\n");
    }
  }

  private static Graph createGraphFromFile(String cmd){
    String[] params = cmd.split("\\s+");
    String fileName = params[2];
    //TODO: add code to init a graph from this file
    return null;
  }
}
