package edu.iastate.cs228.hw5;
/*
 *  @author Brandon Ladd
 *
 */

import java.util.HashMap;
import java.util.Iterator;

public class DFS
{
    // This method creates a color map and a pred map (see lecture code file DiGraph.java)
    // and an empty stack of type LinkedStack<V>. It colors each vertex green
    // and sets the value of each vertex to null in the pred map (see lecture code).
    // Then as long as there is a green vertex w left, the method calls visitDFS()
    // on the vertex w along with other parameters.
    // If visitDFS() returns false, then this method returns null.
    // Otherwise, it returns the stack containing the list of all vertices in
    // a topological order, which is produced by the visitDFS() method.
    // If aGraph is null, then it throws IllegalArgumentException.
	public static <V> LinkedStack<V> depthFirstSearch(DiGraph<V> aGraph) {
		if (aGraph == null) {
			throw new IllegalArgumentException();
		}
		HashMap<V, String> color = new HashMap<V, String>();
		HashMap<V, V> pred = new HashMap<V, V>();
		LinkedStack<V> topoOrder = new LinkedStack<V>();
		for (V vert : aGraph.vertices()) {
			pred.put(vert, null);
			color.put(vert, "green");
		}

		for (V vert : aGraph.vertices()) {
			if (color.get(vert).equals("green")) {
				if (!visitDFS(aGraph, vert, color, pred, topoOrder)) {
					return null;
				}
			}

		}

		return topoOrder;// TODO

	}

	// This method implements an iterative depth-first search algorithm for
	// checking if the given graph is acyclic (has no cycles) and if so,
	// generates a stack (named topoOrder) of all vertices in a topological
	// order and returns true. Otherwise, it returns false. An iterative depth-first
	// search algorithm is given in lecuture for an undirected graph. Here, you
	// need to modify the algorithm to make it work for a directed graph. Note that
	// the edge iterator citer should be changed from type Iterator<V> to
	// type Iterator<Edge<V, Integer>>, and that citer.next().getVertex(),
	// instead of citer.next(), gives the vertex w. If the vertex w is red,
	// then the graph has a cycle. So the method returns false.
	// Whenever a vertex is colored black, the vertex is pushed onto the stack
	// topoOrder. If the graph has no cycles (the execution reaches the end of
	// the method), then the method returns true.
	protected static <V> boolean visitDFS(DiGraph<V> aGraph, V s, HashMap<V, String> color, HashMap<V, V> pred,
			LinkedStack<V> topoOrder) {
		
		color.put(s, "red");
		
		LinkedStack<V> nodestack = new LinkedStack<V>();
		LinkedStack<Iterator<Edge<V, Integer>>> edgestack = new LinkedStack<Iterator<Edge<V, Integer>>>();
		Iterator<Edge<V, Integer>> siter = aGraph.adjacentTo(s).iterator();
		
		nodestack.push(s);
		edgestack.push(siter);
		
		while (!nodestack.isEmpty()) {
			V c = nodestack.peek();
			Iterator<Edge<V, Integer>> citer = edgestack.peek();
			
			if (citer.hasNext()) {
				
				V w = citer.next().getVertex();
				
				if (color.get(w).equals("green")) {
					color.put(w, "red");
					pred.put(w, c);
					Iterator<Edge<V, Integer>> witer = aGraph.adjacentTo(w).iterator();
					nodestack.push(w);
					edgestack.push(witer);
				}
				else if(color.get(w).equals("red")){
					return false;
				}
			}
				else {
				color.put(c, "black"); // processed
				topoOrder.push(nodestack.peek());
				nodestack.pop(); // vertex c is removed
				edgestack.pop(); // its edge iterator is removed
			}
		}
		return true;
	}
}

