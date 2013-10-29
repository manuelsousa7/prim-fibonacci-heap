Comparing performance of Fibonacci heap-based and array-based implementations of Prim's minimum spanning tree algorithm
---
This project compares the time complexity of two implementations of Prim's shortest path algorithm to construct a minimum spanning tree

* a Fibonacci-heap based implementation (**O(|V|log|V| + E)**)
* an array-based implementation (**O(n^2)**)

The code provides an interactive command-line console where the following commands can be entered:

command| meaning
:----|:-----|:----
mst -r n d | Compare the runtime for constructing a minimum spanning tree for a random, undirected, connected graph with n nodes and density d generated using an F-heap based implementation versus that generated with an array-based implementation. Prints the time taken by both the implementations.
mst -s filename | Construct the minimum spanning tree for a graph constructed from file `filename` using an array-based implementation. Prints the edges of the MST so constructed.
mst -f filename | Construct the minimum spanning tree for a graph constructed from file `filename` using an F-heap-based implementation. Prints the edges of the MST so constructed.

A sample file is given below:
<pre>
3 2
0 1 10
1 2 5
</pre>
This file specifies a graph with 3 nodes and 2 edges (first line). The first edge links node 0 and 1 and has a cost of 10 (second line). 
The second edge links nodes 1 and 2 and has a cost of 5 (third line).
